package sparkcore

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Hadoop 1.x
 * 1 namenpode是单点的 容易出现单点故障 制约了集群的发展
 * 2 namenode是单点的 收到了硬件的制约 制约了集群的发展
 * 3 计算引擎是MR 计算和资源耦合在一起 比较慢
 *   MR是面向一次性数据计算 从存储介质中加载数据 然后进行数据计算 将结果在保存回到存储介质中
 * spark
 * 1 基于Mr计算引擎 优化了其中的计算过程 提高了效率
 * 2 spark的计算模型十分丰富 基于scala函数式编程语言实现
 * 3 spark为了更适合迭代式数据开发以及数据发掘计算 提供了很多的优化方案
 *   提供了更加适合并行计算的数据模型(RDD)
 *
 * Hadoop 2.x
 * 1 namenode是高可用
 * 2采用新的资源调度框架(yarn) 资源和调度框架和计算框架解耦合
 *
 *
 *
 */




object SparkWordCount {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local").setAppName("wordCount")
    val sc = new SparkContext(sparkConf)
       sc.textFile("input/word.txt")
      .flatMap(_.split(" "))
      .groupBy(word => word)
      .mapValues(list => list.size)
      .collect().foreach(println(_))

    //创建RDD

    //从内存中读取
    //makeRdd的第二个参数表示分区数量 如果设定 那么spark会按照这个数值进行分区
    // 如果没有设定会找一个配置 spark.default.parallelism  找不到 用并行度
    val rdd: RDD[Int] = sc.makeRDD(List(1,2,3,4),3)

    //将数据保存到分区文件
    rdd.saveAsTextFile("output")

    //从文件中读取 这个路径可以是相对路径 也可以是绝对路径
    //返回的RDD[String] 表示后续操作的类型为字符串 这里的字符串其实就是文件中每一行数据
    //spark读文件采用的是hadoop读文件的原理
    //文件路径可以读取本地文件 也可以读取HDFS中存储的文件
    //到底读取本地还是读取HDFS文件 可以根据运行环境来确定
    //文件路径可以是具体的文件路径 也可以是文件目录 input 目录下所有的文件都会被读到
    //第二个参数表示最小分区数 有默认值 调用时 可以不用传

    //Todo 文件作为数据源 分区计算方式
    //1.计算文件 所有的文件总的字节数
    //2.用总的字节数除以指定的分区数量获取每个分区应该存储的字节数
    //3.因为不一定能整除 所以需要计算需要多少个分区才能容纳所有的数据
    //总的字节数/每个分区的存储的字节数......余数剩余的字节数是否超过每个分区存储的字节数的10% =>1.1 超过了 再加一个分区
    //举例 总字节数 7 最小分区数给了2
    // 7/2 = 3 每个分区应该放三个字节
    // 7/3 = 2.....1
    // 1/3= 0.333 超过了百分之一
    // 所以最后分区数为2+1=3

    //Todo 文件作为数据源 分区如何存储
    //1 底层采用的是hadoop操作 一行一行的读取
    //2 读取数据采用偏移量
    //3 相同偏移量的数据不会重复读取

    //spark文件分区 以总文件数进行分区 但是具体怎么分区 和文件数也有关系 每个文件单独算


    val rdd1: RDD[String] = sc.textFile("input/word.txt",4)
    rdd1.saveAsTextFile("output1")

    //wholeTextFiles 方法可以返回元组数据
    //元组的第一个数据表示文件路径 第二个数据表示数据的完整内容
    val rdd2: RDD[(String, String)] = sc.wholeTextFiles("input")


  }

}
