package collection

import scala.collection.mutable

object Collection_Set {
  def main(args: Array[String]): Unit = {

    //Todo 集合 集 set
    //无序 不可重复
    val set1 = Set(1,2,3,4,5,6)
    println(set1)

    //可变的set集合 没有append insert 因为它无序啊
    val set2 = mutable.Set(1,2,3,4,5)

    //添加数据
    set2.add(1)
    //第一个元素 第二个 包含 修改set集合本身
    set2.update(8,true)
    set2.update(3,false)

    set2.remove(2)
    set2.mkString(",")
    set2.foreach(println(_))



  }
}
