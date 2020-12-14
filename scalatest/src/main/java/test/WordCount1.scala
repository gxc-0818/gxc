package test

import scala.collection.mutable.ArrayOps

object WordCount1 {

  def main(args: Array[String]): Unit = {
    var dataList = List(("Hello Scala", 4), ("Hello Spark", 2))

    val tuples: List[(String, Int)] = dataList.flatMap(t => {
      val key = t._1
      val value1 = t._2
      val strings: ArrayOps.ofRef[String] = t._1.split(" ")
      strings.map(s => (s, value1))
    })
   // println(tuples)

    val groupMap: Map[String, List[(String, Int)]] = tuples.groupBy(_._1)
    println(groupMap)
    val map: Map[String, Int] = groupMap.map(t => {
      val key = t._1
      val value = t._2
      val ints: List[Int] = value.map(t1 => t1._2)
      (key, ints.sum)
    })
    val list: List[(String, Int)] = map.toList
    val list1: List[(String, Int)] = list.sortBy(_._2)(Ordering.Int.reverse)
    //println(list1)



  }
}
