object ExceptionTest {

  def main(args: Array[String]): Unit = {

    //scala异常不分类
    //scala中异常的处理类似于模式匹配
    //如果异常没有匹配成功 也不会发生MatchError 只是将异常直接抛给jvm

    //一个方法中的return操作 返回的都是同一个临时变量




  }

  //java调用scala代码 是没法明确异常的 因为scala不区分运行时异常 和编译时异常
  //要想明确 需要加上注解
  @throws(classOf[Exception])
  def test(): Unit ={
    throw new Exception
  }

}

