package function

object FunctionHigh {

  /**
   * scala语言 万物皆对象 所以函数也是对象 也可以给其他人用
   *
   *
   * */
  def main(args: Array[String]): Unit = {

    def func1(): Unit ={
      println("func1")
    }
    //将函数作为对象赋值给变量 这个变量其实就是函数
    //函数有类型 这个类型其实就是Function （Function0 - Function22 这就是之前说参数个数不要超过22的原因 不然没有类型接）
    val f1 :Function0[Unit]= func1
    f1()
    //第二种方式()无参 Unit 返回值
    val f11 :()=>Unit = func1
    f11()

    val f111 = func1 _

    f111()


    def func2(i: Int): Unit ={
      println(i)
    }
    val f2:(Int)=>Unit = func2
    f2(1)

    //将函数作为一个整体给一个变量
    val f22 = func2 _
    f22(2)

    def func3(): Unit ={
      println("func3")
    }

    //val f3 = func3 //这样会有歧义 会认为是拿返回值 因为无参函数调用够可以省略括号

    //如果想要让函数作为对象来使用 但是又不想声明函数类型 可以采用特殊符号 _ 记住前面有个空格
    val f3 = func3 _
    f3()





  }

}
