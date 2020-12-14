package sparkcore

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object KeyValue {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local").setAppName("local")
    val sc = new SparkContext(sparkConf)
    val rdd: RDD[Int] = sc.makeRDD(List(1,3,4,2),2)

    //collect 用于采集数据 会将Executor执行的结果汇集到Driver的内存中 内存可能会溢出 所以需要谨慎使用
    // 可以触发作业的执行 因为在运行中 会动态创建Job对象 算子的每一次调用 都会创建一个job
    rdd.collect()

    //todo count
    println(rdd.count())

    //todo first
    println(rdd.first())

    //todo take
    println(rdd.take(2))

    //todo 先排序再取值
    println(rdd.takeOrdered(2))

    //todo aggregate
    //aggregateByKey 算子的初始用于分区内计算
    //aggregate的初始值 在分区间也会使用
    val i: Int = rdd.aggregate(10)(_+_,_+_)
    println(i)//40

    //todo fold aggregate的简化操作 分区内和分区间的规则相同
    val i1: Int = rdd.fold(10)(_+_)
    println(i1)

    //todo countByValue 可以实现wordCount 7/10
    val intToLong: collection.Map[Int, Long] = rdd.countByValue()
    println(intToLong)

    //todo countByKey 指的是key出现多少次 和value是无关的
    println(rdd.map(num => {
      (num.toString, num)
    }).countByKey())

    //todo countByKey实现wordcount
    val rdd1: RDD[(String, Int)] = sc.makeRDD(List(("hello",4),("hello",2)))
    val value: RDD[(String, Int)] = rdd1.flatMap {
      case (word, count) => {
//        val str: String = (word + " ") * count
//        str.split(" ").map((_, 1))
        Array.fill(count)(word).map((_, 1))
      }
    }
    val stringToLong: collection.Map[String, Long] = value.countByKey()
    println(stringToLong)
    //todo save 将数据保存到分区文件中
    //rdd.saveAsTextFile("output")
    //rdd.saveAsObjectFile("output1")//对象文件
    //rdd.map((_,1)).saveAsSequenceFile("output2")//序列化文件



    //todo collect是按照分区的顺序采集数据的方法
    // foreach方法其实是scala的集合方法 是单点操作 所以是按照顺序循环
    rdd.collect().foreach(println)

    //rdd 的所有算子其实都是分布式操作
    //所以这里的foreach操作其实它是分布式循环打印 因为还没采集到driver上 就在executor上打印 也不知道谁先打印出来 都有可能 你一个我一个的
    //算子外部的逻辑操作全部都是在Driver端执行
    rdd.foreach(println)


    //分布式执行时 需要考虑传输数据的序列化问题
    //分布式执行计算前 需要进行闭包检测操作 用于判断闭包操作中的数据是否能序列 如果数据不能序列化 在计算前就会报错

    //类的构造参数用于赋值给对象的属性 所以外部无法访问 需要访问对象的属性才可以
    //样例类在编译时自动混入可序列化特质

    //spark中两个rdd之间的关系称之为依赖关系
    //连续的依赖关系称之为
    // 血缘关系

    //spark中所谓的依赖的关系 其实就是分区数据的依赖关系 下游的分区数据 p0 来自上游的哪些分区 上游的一个分区 只给了下游的一个分区 数据没有被打散 这就是OneToOne


    println(rdd.toDebugString)//血缘
    println(rdd.dependencies)//依赖

    //任务数量的计算以阶段为单位

    //rdd.cache()
    //rdd.persist(StorageLevel.DISK_ONLY)

    //检查点 checkpoint 会切断rdd的血缘关系
             //cache是在血缘中添加了一个缓存依赖
    //checkpoint使用的其实不多 会产生很多小文件 导致性能下降
    //rdd.checkpoint()











    sc.stop()
  }

}
