package function

object FunctionMedium {
/**
 * 为了开发人员提高开发效率 从语法的角度 马丁能识别的语法就不需要写
 * 至简原则 能省则省
 * */


  def main(args: Array[String]): Unit = {
    val str: String = fun1()
    println(str)
  }

  //Todo 1 函数可以自动将最后一行代码的执行结果作为返回值 所以return可以省略
  //     2 如果返回值确定 那么返回类型其实也能确定 那么返回值类型也就可以省略
  def fun1(): String ={
    //return "aaa" return省略
    "fun1"
  }
  def fun2()={//：和类型都省略了 idea能自动推断出来
    //return "aaa" return省略
    "func2"
  }
  def func3() ={//这里的Any 就是因为返回值既可能是int又可能是string 所以能包含这两种都就是Any
    val age = 10
    if (age > 10){
      1
    }else{
     "func3"
    }
  }

  //Todo 如果函数逻辑代码只有一行 那么大括号可以省略
  def fun4()= "func4"


  //Todo 如果参数列表没有参数 那么小括号可以省略
  def fun5= "func5"

  //如果函数声明时省略了参数列表 那么调用时也不能加参数列表
  //fun5()//会报错
  val fun: String = fun5
  println(fun)

  //Todo 如果函数体明确了没有返回值 那么函数体中的return关键字不起作用
  //     如果函数体有return 关键字 那么返回值类型不能省略
  //     不想声明返回值类型 但是又不想return关键字起作用 此时可以等号返回值类型一起省略 称之为过程函数 没有结果 只有输入没有输出
  //我的理解 上面说了 有return 关键字不能省略返回值类型 但是还是省略了 只要一种情况 就是没有返回值 就把类型和等号一起省略了 所以 看到return还没类型 就是没有返回值
  def fun6(): Unit ={
    return "func6"
  }
  val v6: Unit = fun6()
  println(v6)//结果是()

  def fun66(){
    return "func66"
  }

  val v66= fun66()
  println(v66) //结果还是()

  def fun666(){ //这里注意哦 没有等号 返回值就是Unit 有等号才会自动识别返回值是String
    "fun666"
  }
  private val v666 = fun666()
  println(v666)//结果还是()

  //Todo 如果不关心函数的名称 只关心逻辑的时候 函数名和def也可以省略
  def fun7(): Unit ={
    println("func7")
  }

  def fun77() ={
    println("func77")
  }

  //() ={//但是这是会报错的 因为()是Unit的一个对象 这里会识别不了 所以要告诉scala这不是对象 是参数列表 需要在等号后面加上> ()=>{}
  //  println("func77")
  //}

  val f = () =>{//匿名函数 但是怎么调用？需要赋值给其他的变量来调用 所以前面加上 val f =
    println("func77")
  }
  //调用 括号不能省
  f()
  //Todo 但是为什么这么用 给了一个变量 是为了扩大作用域 给了一个变量 你想让他返回 传递都可以了就 不然这个函数只是作用在当前范围内

//
  // val list = List[String](': FunctionMedium = new FunctionMedium()')


}
