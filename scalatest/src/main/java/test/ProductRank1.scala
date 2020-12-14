package test

object ProductRank1 {
/**
 * 在实际开发中原则上都是先wordcount再进行分组
 * */
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
    val productList: List[String] = list.map(t => {
      (t._2 + " " + t._3)
    })
    val groupMap: Map[String, List[String]] = productList.groupBy(s=>s)

    val groupMapWithCount: Map[String, Int] = groupMap.mapValues(list=>list.size)

    println(groupMapWithCount)
    //这里不能直接对map操作 要记得 map不允许key重复 所以先转成list在进行操作
    val groupProductMap= groupMapWithCount.toList.map(t => {
      val strings: Array[String] = t._1.split(" ")
      val province = strings(0)
      val product = strings(1)
      (province, (product, t._2))

    })
    val resultBefore: Map[String, List[(String, (String, Int))]] = groupProductMap.groupBy(_._1)
                                                                               //这里遍历的是多个list 所以要对list进行转换 不是list里面的tuple
    val resultValue: Map[String, List[(String, Int)]] = resultBefore.mapValues(list=>list.map(_._2))//注意一下这里 里面究竟处理的是什么 好好想清楚
    val result: Map[String, List[(String, Int)]] = resultValue.mapValues(list=>list.sortBy(_._2)(Ordering.Int.reverse).take(3))
    println(result)
  }
}
