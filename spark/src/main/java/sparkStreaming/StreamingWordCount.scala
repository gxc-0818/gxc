package sparkStreaming

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.receiver.Receiver
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.util.Random

/**
 * 从数据处理方式的角度
 * 流数据处理：一条数据来了 处理一条
 * 批量数据处理 ：一批数据一起处理
 *
 * 从数据处理延迟的角度
 *
 * 实时数据处理：延迟的时间以毫秒为单位
 * 准实时数据处理 延迟的时间以秒 分钟为单位
 * 离线数据处理：延迟的时间以小时 天 为单位
 *
 * SparkStreaming是基于sparkcore 进行流式数据处理的模块
 * 但是SparkCore 中没有办法进行真正的流式数据处理  就应该是批量数据处理
 * SparkStreaming 不是为了离线数据分析 而是希望进行实时数据分析
 * SparkStreaming 批量处理的数据不能太多 称之为微批次处理
 * SparkStreaming 无法真正实现实时数据处理 因为延迟比较高 但是因为一批次数据量较小 又达不到离线的程度 所以提出了准实时的概念
 * SparkStreaming 准实时的数据处理框架
 *
 * SparkStreaming 流式处理 采用离散化流
 * 离散化流 DStream 不连续的流 随时间推移而收到的数据的序列 简单来讲 就是对RDD在实时数据处理场景的一种封装
 * 背压机制 backpressure 根据JobSchedule反馈作业的执行信息来动态调整Receiver数据接收率
 *
 *
 *
 *
 * */
object StreamingWordCount {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("StreamingWordCount")
    val streamingContext = new StreamingContext(sparkConf,Seconds(5))
//    val lines: ReceiverInputDStream[String] = streamingContext.socketTextStream("127.0.0.1",9999)
//    val word: DStream[String] = lines.flatMap(line=>line.split(" "))
//    val wordAndOne: DStream[(String, Int)] = word.map((_,1))
//    val wordSum: DStream[(String, Int)] = wordAndOne.reduceByKey(_+_)
//    wordSum.print()

//    //启动采集器
//    streamingContext.start()
//    //阻塞当前运行的线程 Driver不能执行完毕 需要等待采集器的结束
//    streamingContext.awaitTermination()

    //创建自定义采集器
    val myReciever = new MyReciever
    //应用自定义采集器
    val inputStream : ReceiverInputDStream[String] = streamingContext.receiverStream(myReciever)
    val mapStream: DStream[(String, Int)] = inputStream.map((_,1))
    val result: DStream[(String, Int)] = mapStream.reduceByKey(_+_)
    result.print()

    streamingContext.start()

    streamingContext.awaitTermination()

  }
  //自定义数据采集对象
  //1 继承receiver对象 定义泛型
  //2 给父类传递初始化参数 用于设定数据的存储级别
  //3 重写方法

  class MyReciever extends Receiver[String](StorageLevel.MEMORY_ONLY){
    var flowflag= true
    override def onStart(): Unit = {
      while (flowflag){
        Thread.sleep(100)
        //生成数据
        val data: String = new Random().nextInt(10).toString
        //存储数据
        store(data)
      }
    }

    override def onStop(): Unit = {
      flowflag = false
    }
  }
}
