package sparkStreaming.Req

import java.sql.Connection
import java.text.SimpleDateFormat
import java.util.Date

import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import util.JdbcUtil

object Req2{

  /**
   * 需求二：广告点击量实时统计
   * */

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setAppName("KafkaConsumerAdCal").setMaster("local[8]")
    val ssc: StreamingContext = new StreamingContext(sparkConf,Seconds(5))

    val kafkaPara: Map[String, Object] = Map[String, Object](
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> "gxc188:9092,gxc189:9092,gxc190:9092",
      ConsumerConfig.GROUP_ID_CONFIG -> "gxc",//消费者组
      "key.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
      "value.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer")


    val kafkaDStream: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream[String, String](ssc,
      LocationStrategies.PreferConsistent,//位置的策略
      ConsumerStrategies.Subscribe[String, String](Set("ad"), kafkaPara))

    val datas: DStream[String] = kafkaDStream.map(record => record.value())
    val reduceDs: DStream[((String, String, String, String), Int)] = datas.map {
      line => {
        val datas: Array[String] = line.split(" ")
        val simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")
        val day: String = simpleDateFormat.format(new Date(datas(0).toLong))
        val area = datas(1)
        val city = datas(2)
        val ad = datas(4)
        ((day, area, city, ad), 1)
      }
    }.reduceByKey(_ + _)

    reduceDs.foreachRDD(rdd=>{
      rdd.foreachPartition(t=>{
        val connection: Connection = JdbcUtil.getConnection
        t.foreach{
          case ((day, area, city, ad), sum)=>{
            val sql =
              """
                |insert into area_city_ad_count (dt, area, city, adid, count) values(?,?,?,?,?)
                |on duplicate key
                |update count = count + ?
                |""".stripMargin
            JdbcUtil.executeUpdate(connection,sql,Array(day,area,city,ad,sum,sum))
          }
        }
        connection.close()

      })
    })

    //7.开启任务
    ssc.start()
    ssc.awaitTermination()

  }
}
