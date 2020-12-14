package function

object FunctionHell {
/**
 *
 * */

  def main(args: Array[String]): Unit = {


    //Todo 函数作为返回值返回 一般的场合将内部的逻辑给外部调用
    //如果将函数返回 一般省略返回值类型 应用自动推断
    def fun(): Unit ={
      println("fun")
    }
    def test() ={
      fun _
    }

    //怎么调用?
    test()()


    //(x)(y)(*)

    def outer(x: Int) = {

      def middle(y: Int) = {

        def operate(f: (Int, Int) => Int): Int = {
          f(x, y)
        }
        operate _
      }
      middle _
    }

    println(outer(10)(100)(_ * _))


    def out(x:Int)={
      def iner(y :Int): Unit ={
        println(x + y)
      }
      iner _
    }

    //Todo 一个函数使用了外部的变量 然后改变了这个变量的生命周期
    //将这样的操作称之为闭包
    //将不能够直接使用的变量包含到当前函数的内部 改变生命周期 行程闭合的环境 这个称之为闭包环境 简称闭包
    //out(10)(20)
    val iner = out(10) //按理说out执行完了 弹栈 x应该释放掉了 但是为什么iner 还能用呢？可以反编译看一下 其实inner的参数列表已经变成了X 和 Y
    val result = iner(20)

    //Todo 判断是否存在闭包
    //1 函数使用的变量有没有改变生命周期
    //2 函数有没有当成对象给别人使用
    //3 所有的匿名函数都有闭包
  }
}
