package collection

import scala.collection.mutable

object Collection_Map {
  def main(args: Array[String]): Unit = {

    //Todo 集合 映射 Map
    //map 集合中存储的是键值对 k-V
    // 无序 key不能重复 v可以
    //默认也是不可变map
    val map = Map("a"->1,"b"->2)

    //可变的map集合
    val map1 = mutable.Map("a"->1,"c"->3)

    //增删改查
    map1.put("dd",100)
    map1.remove("a")
    map1.update("a",5)
    map1.get("c")

    //map1.clear()

    map1.mkString(",")

    println(map)

    //option类型 选项类型 Some None
    //scala为了避免空指针异常
    val maybeInt: Option[Int] = map1.get("j")
    if (maybeInt.isEmpty){
      println("无值")
    }else{
      println(maybeInt.get)
    }

    //取不到给个默认值
    println(maybeInt.getOrElse(0))

    //一般这么用 取d 取不到给默认值

    println(map.getOrElse("d", 0))

    //map转换 变成了一个元组集合
    val list: List[(String, Int)] = map.toList

    val keys: Iterable[String] = map.keys

    val values: Iterable[Int] = map.values


  }


}
