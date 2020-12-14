package sparkcore

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object WordCountTest {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local").setAppName("WordCountTest")
    val sc = new SparkContext(sparkConf)
    val rdd: RDD[String] = sc.makeRDD(List("Hello","Scala","Hello","Spark","Spark"))
    //Todo 1 group by
     //Todo 2 reduceByKey
    rdd.map(word=>(word,1)).reduceByKey(_+_).collect().foreach(println)
    //Todo 3  groupByKey
    rdd.map(word=>(word,1)).groupByKey().mapValues(list=>list.size).collect().foreach(println)
    //Todo 4 aggregateByKey
    rdd.map(word=>(word,1)).aggregateByKey(0)(_+_,_+_).collect().foreach(println)
    //Todo 5 foldByKey
    rdd.map(word=>(word,1)).foldByKey(0)(_+_).collect().foreach(println)
    //Todo 6 combineByKey
    rdd.map(word=>(word,1)).combineByKey(i=>i,(x:Int,y:Int)=>(x+y),(x:Int,y:Int)=>(x+y)).collect().foreach(println)
    //Todo 7 countByValue
    val countByValue: collection.Map[String, Long] = rdd.countByValue()
    println(countByValue)
    //Todo 8 countByKey
    val countByKey: collection.Map[String, Long] = rdd.map(word=>(word,1)).countByKey()
    println(countByKey)
    //Todo 9 reduce
   // val reduceMap = rdd.map(word => mutable.Map[String,Int](word, 1))
   // reduceMap.reduce(_)
    //Todo fold

    //Todo aggregate

    //Todo cogroup
  }

}
