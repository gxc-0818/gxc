package sparkStreaming

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}

import scala.collection.mutable.ListBuffer
import scala.util.Random

object KafkaProducer {

  def main(args: Array[String]): Unit = {

    val topic = "gxc"
    // 创建配置对象
    val prop = new Properties()
    // 添加配置
    prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "gxc188:9092")
    prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")

    val random = new Random()

    val producer = new KafkaProducer[String, String](prop)
    while(true){
      val records: ListBuffer[String] = getRandomDataList()
      for (data <- records){
        val record = new ProducerRecord[String,String](topic,data)
        producer.send(record)
      }

      Thread.sleep(100)
    }


    def getRandomDataList()={
      val strings = new ListBuffer[String]
      val areas = List("东北","华北","华东")
      val cities = List("沈阳","北京","上海")
      for (i <- 1 to random.nextInt(20)){
        //时间戳 地区 城市 userId adId
        val str: String = System.currentTimeMillis()+" "+areas(random.nextInt(3))+" "+cities(random.nextInt(3))+" "+random.nextInt(10)+" "+random.nextInt(10)
        strings.append(str)
        println(str)
      }
      strings
    }

  }



}
