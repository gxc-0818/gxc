package sparkStreaming.Req

import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.Date

import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Minutes, Seconds, StreamingContext}

import scala.collection.mutable.ListBuffer

object Req3{

  /**
   * 需求三：最近一小时广告点击量趋势统计
   * 每十分钟算一个时间点
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
      ConsumerStrategies.Subscribe[String, String](Set("gxc"), kafkaPara))

    val datas: DStream[String] = kafkaDStream.map(record => record.value())

    val reduceDs: DStream[(Long, Int)] = datas.map {
      line => {
        val datas: Array[String] = line.split(" ")
        val time: Long = datas(0).toLong / 10000 * 10000
        (time, 1)
      }
    }

    val windowDs: DStream[(Long, Int)] = reduceDs.reduceByKeyAndWindow(
      (x:Int,y:Int)=>{x+y},
      Minutes(1),
      Seconds(10))
    windowDs.foreachRDD(rdd=>{

      //前面有shuffle 所以可能产生乱序 所以这里再排序一下
      val reduceRdd = rdd.sortBy(_._1).collect()
      val listBuffer: ListBuffer[String] = ListBuffer[String]()
      reduceRdd.foreach{
        case(time,count)=>{
          val simpleDateFormat = new SimpleDateFormat("HH:mm:ss")
          val timeString: String = simpleDateFormat.format(new Date(time))
          listBuffer.append(
            s"""
              |{"xtime":"${timeString}","yval":"${count}"}
              |""".stripMargin)

        }
      }

      // 保存文件
      val out = new PrintWriter("C:\\0820\\1资料\\scala\\program\\input\\ad\\adclick.json")
      out.println("["+listBuffer.mkString(",")+"]")
      out.flush()
      out.close()

    })


    ssc.start()
    ssc.awaitTermination()

  }
}
