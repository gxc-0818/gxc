package sparkcore

import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

/**
 * 所谓的算子就是方法 为了和scala区分开 分为转换算子和行动算子
 */

object Value {
  def main(args: Array[String]): Unit = {


    val sparkConf: SparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
    val sc = new SparkContext(sparkConf)
    val rddd: RDD[Int] = sc.makeRDD(List(1,2,3,4),2)

      //Todo 转换算子 分为value类型 双value类型 和key-value类型
      //转换算子默认情况下  创建新的RDD时 分区数量不变 分区数据处理后所在的分区也不会发生变化
      //多个RDD所形成的依赖关系中 每一条数据必须全部的执行完毕后 才能继续执行后续操作
      //map
      //Todo mapPartitions算子用于将一个分区的数据同时进行处理 将一个分区可以加载到内存中进行处理 性能比较高 类似于批处理
                     //依赖于内存的大小 如果内存小的情况下 不推荐使用 内存大也要考虑 分区内的数据不是独立的 一个数据处理完不能被回收 只有整个分区都被处理完才能回收 会占用内存 内存有可能溢出

      //mapPartitionsWithIndex
      //flatMap
      //glom 将同一个分区的数据直接转换为相同类型的内存数组进行处理，分区不变
      val glomRdd: RDD[Array[Int]] = rddd.glom()
      val maxGlom: RDD[Int] = glomRdd.map(array=>{array.max})
      maxGlom.collect().foreach(println)
      //Todo groupBy  1/10 将数据根据指定的规则进行分组, 分区默认不变，但是数据会被打乱重新组合，我们将这样的操作称之为shuffle。极限情况下，数据可能被分在同一个分区中 一个组的数据在一个分区中，但是并不是说一个分区中只有一个组
                //必须将所有分区的数据全部处理(分组)完毕后 才能继续执行后续的操作 那么分组的数据需要等待 但是你的内存是有限的 不能无限期等待 所以shuffle操作必须要落盘
      //shuffle一般分为两部操作 一个写磁盘 一个读磁盘 所以shuffle一般分为两个阶段 一个称之为shuffle write阶段 一个称之为shuffle read阶段
      //shuffle操作是可以改变分区的
      //Todo sample 需要传递三个参数
      //第一个参数表示抽取数据后是否放回数据集中
      //第二个参数基于第一个参数来判断 如果抽取放回的场合 表示期望抽取的次数 如果抽取不放回 表示抽取数据的概率(每条数据的抽取概率)
      //第三个参数表示抽取数据的种子(随机数) 如果不传递就是当前时间
      //随机数不随机 种子相同 结果也是相同的  种子就是随机算法的初始值 因为一般是当前时间 所以是不会相同的


      var rdd: RDD[Int] = sc.makeRDD(List(1,2,3,4,5,6),3)
      //Todo coalesce 缩减分区 用于将多分区减少为少分区 默认情况下不会将数据打乱重新组合
      //默认的合并规则采用的是计算位置的关系 第二个参数是是否shuffle 默认值是false
      var coaRdd: RDD[Int] = rdd.coalesce(2)

      //Todo 扩大分区 repartition的底层调用的就是coalesce 方法 只不过一定会会有shuffle过程
      val reRdd: RDD[Int] = rdd.repartition(6)

      //默认升序 存在shuffle
      val sordRdd: RDD[Int] = rdd.sortBy(num=>num,true,4)

      //Todo  交集 并集 差集 操作时 需要两个数据集的类型是一样的
      val rdd1: RDD[Int] = sc.makeRDD(List(1,2,3,4))
      val rdd2: RDD[Int] = sc.makeRDD(List(3,4,5,6))

      //交集
      println(rdd1.intersection(rdd2).collect().mkString(","))
      //并集
      println(rdd1.union(rdd2).collect().mkString(","))
      //差集
      println(rdd1.subtract(rdd2).collect().mkString(","))

      //zip 分区数需要一致 分区内元素的数量需要一致 不然会报错 数据的类型可以不一致
      val rdd3: RDD[Int] = sc.makeRDD(List(1,2,3,4),2)
      val rdd4: RDD[Int] = sc.makeRDD(List(3,4,5,6),2)
      println(rdd3.zip(rdd4).collect().mkString(",")) //(1,3),(2,4),(3,5),(4,6)

      //Spark的RDD处理的数据类型 如果为KV类型 需要进行特殊的转换才能调用特殊的功能
      val kvRDD: RDD[(Int, Int)] = rdd.map((_,1))
      //所有的kv操作方法 都来自于 PairRDDFunctions 使用的是scala的隐式转换语法

      //Todo partitionBy方法根据指定的规则对数据进行重分区
      //repartition是为了改变分区的数量 partitionBy是为了改变数据所存放的分区
      //partitionby 需要传递一个分区器对象 改变数据所在的分区
      //Partitioner分区器对象有两个具体的分区器 HashPartitioner 和 RangePartitioner
      //scala中很多Rdd操作默认的分区器都是HashPartitioner
      kvRDD.partitionBy(new HashPartitioner(2))

      //Todo reduceBykey 将相同的key的数据汇总在一起后 对V进行聚合操作 在分区内预聚合和分区间聚合的逻辑是相同的
             //也能实现wordCount 2/10
      //todo groupByKey 3/10
             //groupBy可以根据数据来计算分组的key 分组后每一个数据都会放置在一个组中
             //groupByKey采用固定采用key作为分组key 分组后 每一个kv数据的v会放置在一个组中
     //todo aggregateByKey 4/10
     //第一个参数列表有一个参数 表示计算的初始值 用于分区内相同key第一个value的计算
     //第二个参数列表有两个参数
            //第一个参数是分区内计算功能
            //第二个参数表示分区间计算功能

    //todo foldByKey  5/10 aggregateByKey的分区内和分区间规则相同的时候可以简化为foldByKey

    //todo combineByKey
      //第一个参数 转换相同key的第一个值
      //第二个参数是分区内计算功能
      //第三个参数表示分区间计算功能

    //todo sortByKey 跟v没关系 只排Key sortBy是按指定的规则进行排序 K如果是自定义的类一定要实现Ordered

    //todo join 用于连接两个数据源 连接条件为相同的key join也会产生笛卡尔积
    val rdd11= sc.makeRDD(List(("a",1),("b",2)))
    val rdd22: RDD[(String, Int)] = sc.makeRDD(List(("b",5),("a",3),("b",4)))

    //如果两个数据源中相同key 会将两个V连接在一起 但是如果一个数据源中有key 另外一个没有 那么无法连接                                             //笛卡尔积
    rdd11.join(rdd22).collect().foreach(println) //(a,(1,3))
                                                //(b,(2,5))
                                                //(b,(2,4))

    val rdd33= sc.makeRDD(List(("a",1),("b",2),("c",5)))
    val rdd44: RDD[(String, Int)] = sc.makeRDD(List(("b",5),("a",3),("d",5),("a",6)))
    //主从关系
    rdd33.leftOuterJoin(rdd44)
    rdd33.rightOuterJoin(rdd44)
    //每个数据源进行groupByKey操作 多个数据源进行connect connect+group的缩写 可以传多个rdd
    rdd33.cogroup(rdd44).collect().foreach(println) //(d,(CompactBuffer(),CompactBuffer(5)))
                                                    //(a,(CompactBuffer(1),CompactBuffer(3, 6)))
                                                    //(b,(CompactBuffer(2),CompactBuffer(5)))
                                                    //(c,(CompactBuffer(5),CompactBuffer()))














  }

}
