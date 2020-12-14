package collection

import scala.collection.mutable.ListBuffer

object Collection_Seq {

  def main(args: Array[String]): Unit = {
    //todo 集合 - 序列 seq 最常用的是list
    //list 有序 数据可重复

    //val seq = Seq(1,2,3,4)
    //在使用序列的集合时 使用最多的是List

    //构建不可变的序列集合
//    val list = List(1,2,3,4)
//    val list1: List[Int] = list:+5 //产生了一个新的集合
//    println(list1)//List(1, 2, 3, 4, 5)
//    val list2: List[Int] = 6+:list
//    println(list2)//List(6, 1, 2, 3, 4)

    //创建list集合
    //空集合
    //val list = Nil
    //添加数据
    val list1: List[Int] = 1::2::3::4::5::Nil // =>3::List(4)=>2::List(3,4)=>1::List(2,3,4)=>List(1,2,3,4)

    println(list1)//List(1, 2, 3, 4, 5)

    val list2 = List(5,6,7,8)
    val list3: List[Any] = 1::2::3::list2::Nil
    println(list3)//List(1, 2, 3, List(5, 6, 7, 8))

    //将整体拆分为一个一个的个体来使用 称之为扁平化 用三个冒号来实现
    val list4 = 1::2::3::list2:::Nil
    println(list4) //List(1, 2, 3, 5, 6, 7, 8)

    //可变list集合
    val buffer = ListBuffer(1,2,3,4)
    buffer.append(1)
    buffer.update(2,5)
    buffer.remove(2)
    buffer.remove(2,3)
    buffer.mkString(",")
    buffer.foreach(println(_))


  }
}
