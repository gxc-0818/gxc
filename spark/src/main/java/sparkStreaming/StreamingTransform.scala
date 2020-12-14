package sparkStreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object StreamingTransform {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("streamingTransform")
    val ssc = new StreamingContext(sparkConf,Seconds(3))
    val ds: ReceiverInputDStream[String] = ssc.socketTextStream("127.0.0.0",9999)

    //Dstream底层封装了RDD 但是提供的方法并不完整 所以如果想要实现特定功能 如排序 那么需要将DStream转换成RDD进行操作
    val transForm: DStream[String] = ds.transform(rdd=>rdd.sortBy(s=>s))

    //Todo 原语 算子 方法

    //coding =>driver端 执行一次
    ds.map(
      s=>{
        //coding =>executor端 跟任务数量有关
        s*2
      }
    )

    //coding =>driver端 执行一次
    ds.transform(
      rdd=> {
        //Todo coding =>driver端 这里的代码周期性执行
        rdd.map(
          s => {
            //coding =>executor端 跟任务数量有关
            s * 2
          }
        )
      }
    )

    //Todo 综上为什么两个相同的功能 要使用transform 可以周期性执行


    //Dstream 的输出操作不是很多 所以想要进行特殊的输出操作 必须获取底层的RDD进行处理
    //transform 需要返回 foreachRDD不需要返回
    //最通用的输出操作
    ds.foreachRDD{
      rdd=>{
        //coding 周期性执行
        //JDBC
        //Jedis
        //Hbase
      }
    }




    ssc.start()
    ssc.awaitTermination()

    /**
     * 优雅关闭
     * 强制关闭
     * 二阶段提交  预提交
     * 补偿式事务
     * */

    //在采集业务发生变化或技术升级时 需要将采集功能进行关闭
    //关闭时需要调用对象的stop方法
    //stop方法是不能在主线程中进行调用的
    //1关闭需要在新的线程中执行
    //2 方法的调用时机(分布式事务)
    //实现方式 第三方的一些状态
    //JDBC =>Table closeFlag 0->1
    //Zk -> 节点 -> stopSpark
    //HDFS -> path -> stopSpark
    //Redis -> data -> stopSpark


    new Thread(new Runnable {
      override def run(): Unit = {

        //第二个参数 是否优雅的关闭
        ssc.stop(true,true)
      }
    })



    //ssc.stop()

  }

}
