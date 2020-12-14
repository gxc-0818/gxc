package sparkStreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}

object StreamingState {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("streamingState")
    val ssc = new StreamingContext(sparkConf,Seconds(3))

        .7
    ssc.checkpoint("cp")

    val ds: ReceiverInputDStream[String] = ssc.socketTextStream("127.0.0.1",9999)

    val wordDs: DStream[String] = ds.flatMap(_.split(" "))
    val wordToOneDs: DStream[(String, Int)] = wordDs.map((_,1))

    //所谓的无状态操作就是计算时不考虑缓冲
    //wordToOneDs.reduceByKey(_+_) 无状态操作
    val stateDs: DStream[(String, Int)] = wordToOneDs.updateStateByKey {

      //第一个参数 相同的key的value的集合(seq)
      //第二个参数 缓冲区数据对象
      //有状态计算就是使用缓冲区进行计算 这个缓冲区其实就是采用的是检查点操作
      (seq: Seq[Int], buffer: Option[Int]) => {
        val newValue: Int = buffer.getOrElse(0) + seq.sum
        //新的值更新回去
        Option(newValue)

      }
    }
    stateDs.print()

    ssc.start()
    ssc.awaitTermination()
  }


}
