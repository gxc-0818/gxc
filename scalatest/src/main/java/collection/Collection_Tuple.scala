package collection

object Collection_Tuple {
  def main(args: Array[String]): Unit = {

    //Todo 集合 元组
    //scala将无关的数据作为一个整体来使用 形成了一个元素的数据集合 简称为元组Tuple

    //元祖的数据最多只能容纳22个 只是限制了数据容纳的个数 没有限制数据容纳的类型可以里面套集合和元组
    val t :(Int,String,Int)= (1,"aaa",100)
    println(t)

    //元组数据的访问 根据有效的顺序号访问
    println(t._1)
    println(t._2)
    println(t._3)

    t.productIterator

    //如果元组中只有两个元素 那么这样的元组称之为对偶元组 也称之为键值对

    val kv=("a",1)

    //map集合中的键值对 就是元组
    "a"->1 //就是上面
    val map: Map[String, Int] = Map(("a",1),("b",2))
    for (kv <- map){
      println(kv._1 +" "+kv._2)
    }





  }
}
