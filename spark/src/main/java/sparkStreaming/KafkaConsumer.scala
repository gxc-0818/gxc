package sparkStreaming

import java.sql.{Connection, Date, PreparedStatement, ResultSet}
import java.text.SimpleDateFormat

import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord}
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import util.JdbcUtil

import scala.collection.mutable.ListBuffer

object KafkaConsumer {

  /**
   * 用rdd.foreachPartition 代替 rdd.foreach
   * */

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setAppName("KafkaConsumer").setMaster("local[8]")
    val ssc: StreamingContext = new StreamingContext(sparkConf,Seconds(5))

    val BlackLimit = 20
    val kafkaPara: Map[String, Object] = Map[String, Object](
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> "gxc188:9092,gxc189:9092,gxc190:9092",
      ConsumerConfig.GROUP_ID_CONFIG -> "gxc",//消费者组
      "key.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
      "value.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer")


    val kafkaDStream: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream[String, String](ssc,
      LocationStrategies.PreferConsistent,//位置的策略
      ConsumerStrategies.Subscribe[String, String](Set("gxc"), kafkaPara))

    val datas: DStream[String] = kafkaDStream.map(record => record.value())

    datas.foreachRDD{
      rdd => {
        //加载黑名单
        val blackList: ListBuffer[String] = ListBuffer[String]()
        val connection: Connection = JdbcUtil.getConnection
        val statement: PreparedStatement = connection.prepareStatement(
          """
            |select userid from black_list
            |""".stripMargin)
        val resultSet: ResultSet = statement.executeQuery()
        while (resultSet.next()) {
          blackList.append(resultSet.getString(1))
        }
        statement.close()
        connection.close()

        //过滤黑名单
        rdd.filter(line => {
          val datas: Array[String] = line.split(" ")
          datas.length == 5 && !blackList.contains(datas(3))
        })

        //不在黑名单 wc 统计((天 userid adId) sum )
        val sdf = new SimpleDateFormat("yyyy-MM-dd")
        val reduceRdd: RDD[((String, String, String), Int)] = rdd.map(line => {
          val datas: Array[String] = line.split(" ")
          val day: String = sdf.format(new Date(datas(0).toLong))
          ((day, datas(3), datas(4)), 1)
        }).reduceByKey(_ + _)

        //不应该和数据库建立大量的连接对象 虚拟机是个进程 mysql也是个进程 两个进程交互 非常慢 所以用到了连接池 JdbcUtil 但是不能完全解决问题 所以最好是减少connection的个数
        //所有的连接对象都没有办法序列化
        reduceRdd.foreach{
          case ((day,userId,adId),sum) =>{
            // 大于阈值 拉黑
            val connection: Connection = JdbcUtil.getConnection
            if (sum > BlackLimit){
//              val statement: PreparedState
//              ment = connection.prepareStatement(
//                """
//                  |insert into black_list values(?)
//                  | ON DUPLICATE KEY
//                  | UPDATE userid=?
//                  |""".stripMargin)
//              statement.setString(1,userId)
//              statement.setString(2,userId)
//              statement.executeUpdate()
              val sql: String =
                """
                  |insert into black_list values(?)
                  | ON DUPLICATE KEY
                  | UPDATE userid=?
                  |""".stripMargin
              JdbcUtil.executeUpdate(connection,sql,Array(userId,userId)) //封装一下执行sql
            }else{
              //不大于阈值 获取数据
              val statement: PreparedStatement = connection.prepareStatement(
                """
                  |select count from user_ad_count where dt = ? and userid = ? and adid = ?
                  |""".stripMargin)
              statement.setString(1,day)
              statement.setString(2,userId)
              statement.setString(3,adId)
              val resultSet1: ResultSet = statement.executeQuery()
              if (!resultSet1.next()){
                //没有数据 插入数据
                val sql =
                  """
                    |insert into user_ad_count values(?,?,?,?)
                    |""".stripMargin
                JdbcUtil.executeUpdate(connection,sql,Array(day,userId,adId,sum))

              }else {
                //有数据 聚合 与阈值比较
                val hasCount: Long = resultSet1.getLong(1)
                val nowCount = hasCount + sum
                if (nowCount > BlackLimit) {
                  //大于阈值 拉黑
                  val sql="""
                      |insert into black_list values(?)
                      | ON DUPLICATE KEY
                      | UPDATE userid=?
                      |""".stripMargin
                  JdbcUtil.executeUpdate(connection,sql,Array(userId,userId))

                } else {
                  //小于 求和 更新数据
                  val sql="""
                      |update user_ad_count set count = ? where dt = ? and userid = ? and adid = ?
                      |""".stripMargin
                  JdbcUtil.executeUpdate(connection,sql,Array(nowCount,day,userId,adId))
                }
              }

            }
            connection.close()
          }
        }
        reduceRdd

      }
    }

    //7.开启任务
    ssc.start()
    ssc.awaitTermination()

  }
}
