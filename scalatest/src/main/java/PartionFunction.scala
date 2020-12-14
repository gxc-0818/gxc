object PartionFunction {
  /**
   * 偏函数 对集合中符合条件的数据进行处理的函数
   * map flatmap 全量函数
   * */
  def main(args: Array[String]): Unit = {

    //把数字加1 字符串去掉
    val list = List(1,2,3,4,5,"test")
    val result= list.map {
      case i: Int => i + 1
      case s => s
    }.filter(_.isInstanceOf[Int])
    println(result)

    //采用偏函数支持相同的逻辑 功能函数需要支持偏函数
    list.collect{case i:Int=>i+1}



  }

}
