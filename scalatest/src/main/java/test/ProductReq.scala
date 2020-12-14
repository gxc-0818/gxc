package test

object ProductReq {
  def main(args: Array[String]): Unit = {
    val list = List(
      ("zhangsan", "河北", "鞋"),
      ("lisi", "河北", "衣服"),
      ("wangwu", "河北", "鞋"),
      ("zhangsan", "河南", "鞋"),
      ("lisi", "河南", "衣服"),
      ("wangwu", "河南", "鞋"),
      ("zhangsan", "河南", "鞋"),
      ("lisi", "河北", "衣服"),
      ("wangwu", "河北", "鞋"),
      ("zhangsan", "河北", "鞋"),
      ("lisi", "河北", "衣服"),
      ("wangwu", "河北", "帽子"),
      ("zhangsan", "河南", "鞋"),
      ("lisi", "河南", "衣服"),
      ("wangwu", "河南", "帽子"),
      ("zhangsan", "河南", "鞋"),
      ("lisi", "河北", "衣服"),
      ("wangwu", "河北", "帽子"),
      ("lisi", "河北", "衣服"),
      ("wangwu", "河北", "电脑"),
      ("zhangsan", "河南", "鞋"),
      ("lisi", "河南", "衣服"),
      ("wangwu", "河南", "电脑"),
      ("zhangsan", "河南", "电脑"),
      ("lisi", "河北", "衣服"),
      ("wangwu", "河北", "帽子")
    )

    //先wc再分组
    val productList: List[String] = list.map{case(person,province,product) =>province+" "+product}

    val groupMap: Map[String, List[String]] = productList.groupBy(s=>s)
    val groupToSum: Map[String, Int] = groupMap.mapValues(list=>list.size)
    val tuples= groupToSum.toList.map { case (stringCombine, count) =>
      val strings = stringCombine.split(" ")
      (strings(0), (strings(1), count))
    }
    val provinceMap: Map[String, List[(String, (String, Int))]] = tuples.groupBy(_._1)
    val result: Map[String, List[(String, Int)]] = provinceMap.mapValues(list=>{
        list.map(_._2).sortBy(_._2)(Ordering.Int.reverse).take(3)
    })
    println(result)






  }

}
