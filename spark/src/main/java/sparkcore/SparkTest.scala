package sparkcore

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object SparkTest {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local").setAppName("SparkTest")
    val sc = new SparkContext(sparkConf)
    //Todo 从服务器日志数据apache.log中获取用户请求URL资源路径
    val logRdd: RDD[String] = sc.textFile("input/apache.log")
    val mapRdd: RDD[String] = logRdd.map(
      list => {
        val strings: Array[String] = list.split(" ")
        strings(6)
      }
    )
    //mapRdd.collect().foreach(println)

    //Todo 获取每个数据分区的最大值
    val numRdd: RDD[Int] = sc.makeRDD(List(1,2,3,4,5,6),3)

    //需要返回一个迭代器 即使是一个数也是可迭代的
    val maxRdd: RDD[Int] = numRdd.mapPartitions(
      list => {
        List(list.max).iterator
      }
    )
    //maxRdd.collect().foreach(println)

    //Todo 获取第二个数据分区的数据
    val indexPartitionRdd= numRdd.mapPartitionsWithIndex(
      (index, datas) => {
        if (index == 1) {
          datas
        } else {
          Nil.iterator
        }
      }
    )
    //indexPartitionRdd.collect().foreach(println)


    //Todo 将List(List(1,2),3,List(4,5))进行扁平化操作
    val listRdd: RDD[Any] = sc.makeRDD(List(List(1,2), 3, List(4,5)))
    val flatMapRdd: RDD[Int] = listRdd.flatMap {
      case i: Int => List(i)
      case list: List[Int]=>list
    }
    //flatMapRdd.collect().foreach(println)

    //Todo 计算所有分区最大值求和（分区内取最大值，分区间最大值求和）
    val glomRdd: RDD[Array[Int]] = numRdd.glom()
    val maxGlomRdd: RDD[Int] = glomRdd.map(list => {
      list.max
    })
    //maxGlomRdd.collect().foreach(println)
    //println("分区最大值求和"+maxGlomRdd.sum())

    //Todo 小功能：将List("Hello", "hive", "hbase", "Hadoop")根据单词首写字母进行分组。

    val stringRdd: RDD[String] = sc.makeRDD(List("Hello", "hive", "hbase", "Hadoop"))
    val groupRdd: RDD[(String, Iterable[String])] = stringRdd.groupBy(_.substring(0,1))
    //groupRdd.collect().foreach(println)

    //Todo 小功能：从服务器日志数据apache.log中获取每个时间段访问量
    val hourRdd: RDD[(String, Int)] = logRdd.map(
      line => {
        val datas: Array[String] = line.split(" ")
        (datas(3).split(":")(1), 1)
      }
    )
    val hourResult: RDD[(String, Int)] = hourRdd.reduceByKey(_+_)
    //hourResult.collect().foreach(println)

    //Todo 从服务器日志数据apache.log中获取2015年5月17日的请求路径 一般都是先过滤
    val filterRdd: RDD[String] = logRdd.filter(line => {
      val datas: Array[String] = line.split(" ")
      datas(3).startsWith("17/05/2015")
    })
    val urlRdd: RDD[String] = filterRdd.map(line => {
      line.split(" ")(6)
    })
    //urlRdd.collect().foreach(println)

    //agent.log：时间戳，省份，城市，用户，广告，中间字段使用空格分隔 统计出每一个省份每个广告被点击数量排行的Top3
    val agentRdd= sc.textFile("input/agent.log")
    val proAndAdRdd: RDD[((String, String), Int)] = agentRdd.map(line => {
      val datas: Array[String] = line.split(" ")
      ((datas(1), datas(4)), 1)
    })
    val proAndAdSum: RDD[((String, String), Int)] = proAndAdRdd.reduceByKey(_+_)
    val proAndAd: RDD[(String, (String, Int))] = proAndAdSum.map {
      case ((pro, ad), sum) => (pro, (ad, sum))
    }
    val groupAdRdd: RDD[(String, Iterable[(String, Int)])] = proAndAd.groupByKey()
    val result: RDD[(String, List[(String, Int)])] = groupAdRdd.mapValues(map => {
      map.toList.sortBy(_._2)(Ordering.Int.reverse).take(3)
    })
    result.collect().foreach(println)









  }
}
