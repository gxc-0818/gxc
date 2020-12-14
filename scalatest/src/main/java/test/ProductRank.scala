package test

import scala.collection.mutable.ListBuffer
import scala.io.{BufferedSource, Source}

object ProductRank {
  def main(args: Array[String]): Unit = {

    val source: BufferedSource = Source.fromFile("input/data.txt")
    val list = ListBuffer[String]()
    val iter: Iterator[String] = source.getLines()
    while (iter.hasNext){
      val s = iter.next().trim
      if(s.startsWith("(") && s.endsWith(",")){
         list.append(s.substring(1,s.length-2))
      }
    }
    val tuples: ListBuffer[(String, String, String)] = list.map(s => {
      val ss = s.split(",")
      val province = ss(1)
      val product = ss(2)
      val person = ss(0)
      (province, product, person)
    })
    val list1: Map[String, ListBuffer[(String, String, String)]] = tuples.groupBy(_._1)
    val results= list1.map(t => {
      val pro = t._1
      val value: ListBuffer[(String, String, String)] = t._2
      val buffer: ListBuffer[(String, String)] = value.map(t => {
        (t._2, t._3)
      })
      val tuples1: Map[String, ListBuffer[(String, String)]] = buffer.groupBy(_._1)
      val counts: Map[String, Int] = tuples1.map(t => (t._1, t._2.size))
      val tuples2: List[(String, Int)] = counts.toList.sortBy(_._2)(Ordering.Int.reverse)
      (pro, tuples2)
    })

    println(results)
    source.close()









  }

}
