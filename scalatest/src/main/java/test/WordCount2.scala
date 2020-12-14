package test

object WordCount2 {
  def main(args: Array[String]): Unit = {
    var dataList = List(("Hello Scala", 4), ("Hello Spark", 2))
    val tuples: List[(String, Int)] = dataList.flatMap { case (wordString, count) => {
      val strings = wordString.split(" ").toList
      strings.map(s => (s, count))
    }}
    val groupMap: Map[String, List[(String, Int)]] = tuples.groupBy(_._1)
    val groupToSum: Map[String, Int] = groupMap.mapValues(list => {
      val ints: List[Int] = list.map(_._2)
      ints.sum
    })
    val result: List[(String, Int)] = groupToSum.toList.sortBy(_._2)(Ordering.Int.reverse)
    println(result)

  }

}
