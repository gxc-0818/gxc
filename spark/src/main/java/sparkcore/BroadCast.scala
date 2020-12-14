package sparkcore

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

object BroadCast {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local").setAppName("BroadCast")
    val sc = new SparkContext(sparkConf)
    val rdd1: RDD[(String, Int)] = sc.makeRDD(List(("a",3),("b",2)))
    val rdd2: RDD[(String, Int)] = sc.makeRDD(List(("a",2),("b",1)))
    //join算子不推荐使用 笛卡尔乘积 数量几何性增长 没有shuffle还好
    //join 如果分区器相同 是可能一对一依赖的 否则就是shuffle

    //val rdd3: RDD[(String, (Int, Int))] = rdd1.join(rdd2)
    //println(rdd3.collect().mkString(","))

    var map = mutable.Map(("a",2),("b",1))

    //Todo 创建广播变量 分布式共享只读变量
    val bc: Broadcast[mutable.Map[String, Int]] = sc.broadcast(map)

    val mapRdd: RDD[(String, (Int, Int))] = rdd1.map {
      case ((k, v)) => {
        (k, (bc.value.getOrElse(k, 0), v))
      }
    }

    println(mapRdd.collect().mkString(","))

  }

}
