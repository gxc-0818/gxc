package sparkStreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}


object StreamingWindow {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("StreamingWindow                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ")
    val streamingContext = new StreamingContext(sparkConf,Seconds(3))
    val ds: ReceiverInputDStream[String] = streamingContext.socketTextStream("127.0.0.1",9999)

    //无状态操作
    val word: DStream[String] = ds.flatMap(line=>line.split(" "))
    val wordAndOne: DStream[(String, Int)] = word.map((_,1))

    //计算前 可以设定数据的操作范围(窗口)
    //窗口window方法用于进行数据的窗口计算
    //默认情况下 窗口随着时间推移进行滑动的 默认值以一个采集周期
    //SparkStreaming中计算周期为滑动的步长
    //窗口操作默认就是存在 只不过默认范围为一个采集周期 滑动幅度为一个采集周期
    //默认的窗口操作不需要状态保存 只需要默认处理即可
    //其中会传递两个参数
    //第一个参数表示窗口的范围 以时间为单位 一般设定为采集周期的整数倍
    //第二个参数 表示窗口的滑动幅度 步长 一般为采集周期的整数倍
    val windowDs: DStream[(String, Int)] = wordAndOne.window(Seconds(3),Seconds(6))
//    val wordSum: DStream[(String, Int)] = windowDs.reduceByKey(_+_)
//    wordSum.print()

    //reduceByKeyAndWindow 需要保存中间处理状态 一般在窗口范围大 步长小的时候用 因为存在大量的重复数据的计算 会影响性能
    streamingContext.checkpoint("cp")
    windowDs.reduceByKeyAndWindow(
      (x,y)=>{x+y},
      (a,b)=>{a-b},//反转操作 把划过去的部分剪掉 因为重复了 忘记了可以去看图
      Seconds(9),
      Seconds(3)
     )

    streamingContext.start()
    streamingContext.awaitTermination()


  }


}
