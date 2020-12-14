package test

import scala.io.{BufferedSource, Source}

object WordCount {
  def main(args: Array[String]): Unit = {
    val source: BufferedSource = Source.fromFile("input/word.txt")
    val list: List[String] = source.getLines().toList
    source.close()
    //打散
    val strings: List[String] = list.flatMap(_.split(" "))
    //分组
    val map: Map[String, List[String]] = strings.groupBy(w=>w)
    //map进行转换 相同key的进行合并
    val stringToInt: Map[String, Int] = map.map(t => {
      (t._1, t._2.size)
    })
    //转为有序 排序 降序
    val list1: List[(String, Int)] = stringToInt.toList
    val sortList: List[(String, Int)] = list1.sortBy(_._2)(Ordering.Int.reverse)

    //取前三
    val result: List[(String, Int)] = sortList.take(3)
    println(result)
  }
}
