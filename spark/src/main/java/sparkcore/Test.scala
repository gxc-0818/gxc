package sparkcore

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Test {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("test")
    val sc = new SparkContext(sparkConf)
    val rdd: RDD[String] = sc.textFile("input/word.txt")
    val wcRdd: RDD[(String, Int)] = rdd.flatMap(s => {
      val strings: Array[String] = s.split(" ")
      strings.map(s1 => {
        (s1, 1)
      })
    }).reduceByKey(_ + _)
    wcRdd.collect().foreach(println)
  }
}
