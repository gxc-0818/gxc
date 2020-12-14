package sparkStreaming

import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object StreamingKafkaConsumer {

  def main(args: Array[String]): Unit = {

    //从检查点恢复数据
    val context: StreamingContext = StreamingContext.getActiveOrCreate("cp", () => {

      val sparkConf: SparkConf = new SparkConf().setAppName("StreamingKafkaConsumer").setMaster("local[8]")
      val ssc: StreamingContext = new StreamingContext(sparkConf, Seconds(3))


      val kafkaPara: Map[String, Object] = Map[String, Object](
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> "gxc188:9092,gxc189:9092,gxc190:9092",
        ConsumerConfig.GROUP_ID_CONFIG -> "gxc", //消费者组
        "key.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
        "value.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer")
      //Todo 从kafaka中消费数据 用于数据分析
      //由于在实际工作中 实时数据处理都是由kafka完成的 所以为了开发方便 kafka提供了工具类完成基本的操作
      //kafka中的数据以K-V对进行传递
      val kafkaDStream: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream[String, String](ssc,
        LocationStrategies.PreferConsistent, //位置的策略
        ConsumerStrategies.Subscribe[String, String](Set("gxc"), kafkaPara)) //消费策略 set 主题

      //5.将每条消息的KV取出
      val valueDStream: DStream[String] = kafkaDStream.map(record => record.value())

      valueDStream.print()


      ssc
    })
    context

    //7.开启任务
    context.start()
    context.awaitTermination()


  }
}
