package collection

object Collection_method1 {

  //Todo 集合常用方法
  def main(args: Array[String]): Unit = {
    val list = List(1,2,3,4)
    println(list.size)
    println(list.isEmpty)
    println(list.contains(2))
    //Js里比较有用 因为js可以把字符串当成代码来使用 scala也可以 但是需要第三方类库
    println(list.mkString(","))
    list.foreach(println(_))

//  val iterator = list.iterator
//  while (iterator.hasNext){
//    iterator.next()
//  }
//  list.productIterator

    //衍生集合 scala中最常用的算法就是递归算法
    println("集合的头 " + list.head) //第一个元素 1
    println("集合的尾 " + list.tail) //除了头就是尾 List(2, 3, 4)
    println("最后一个 " + list.last) //最后一个 4
    println("除了最后一个 " + list.init) //List(1, 2, 3)

    println("取n个数据 "+list.take(3)) //List(1, 2, 3)
    println("反转 "+list.reverse) //List(4, 3, 2, 1)
    println("丢弃n条数据 "+list.drop(2)) //List(3, 4)
    println("从右边丢弃n条数据 "+list.dropRight(2)) //List(1, 2)

    println(list.sum)
    println(list.max)
    println(list.min)
    println(list.distinct)//去重
    println(list.product)//乘积

    //scala提供了自定义计算的函数 reduce就是聚合功能(简化 规约 )
    //scala中数据计算的方法用的最多的是 两两计算
    println(list.reduce(_ - _))//-8

    //reduce方法底层实现就是reduceLeft(B,Int) => Int reduce方法是一个特例
    println(list.reduceLeft(_ - _))//-8


    // reversed.reduceLeft[B]((x, y) => op(y, x))
    println(list.reduceRight(_ - _))//-2

    val list1 = List(1,2,3,4)
    val list2 = List(3,4,5,6)

    //交集
    println(list1.intersect(list2)) //List(3, 4)
    //并集
    println(list1.union(list2))//List(1, 2, 3, 4, 3, 4, 5, 6)
    //差集 这个要看以谁为基准
    println(list1.diff(list2))//List(1, 2)
    println(list2.diff(list1))//List(5, 6)

    //按照顺序来匹配数据 拉链 如果数据参差不齐 以最短的为准
    println(list1.zip(list2))//List((1,3), (2,4), (3,5), (4,6))

    //将集合的一部分数据当成一个整体 滑动窗口
    val iterator1: Iterator[List[Int]] = list1.sliding(2)
    while (iterator1.hasNext){
      println(iterator1.next())//List(1, 2) List(2, 3) List(3, 4)
    }
    list1.sliding(2,2)//可以带步长 就是不想数据重复的话







  }

}
