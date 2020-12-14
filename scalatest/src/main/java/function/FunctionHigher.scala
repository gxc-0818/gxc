package function

object FunctionHigher {
  def main(args: Array[String]): Unit = {


    //Todo 将函数作为参数来使用 一般情况下使用匿名函数
    def fun1( f:()=>Unit ): Unit ={
      f()
    }

    fun1( ()=>{println("test")} )

    def fun2( f:(Int,Int)=>Int ):Int ={
      f(1,2)
    }

    val result:Int = fun2( (i:Int,j:Int)=>{i+ j} )
    println(result)

    //参数类型可以推断 参数类型可以省略 逻辑代码只有一行 花括号可以省略
    val result1:Int = fun2( (i,j)=>i * j )
    println(result1)

    val result2:Int = fun2(_+100+_)
    println(result2)


    def func3(  f:(String)=>Unit): Unit ={
      f("func3")
    }

    def test(name :String):Unit={
      println(name)
    }

    func3(test)
    func3((name:String)=>{println(name)})
    //逻辑代码只有一行 花括号可以省略
    func3((name:String)=>println(name))
    //参数类型可以推断 参数类型可以省略
    func3((name)=>println(name))
    //参数列表中的参数只有一个 小括号可以省略
    func3(name=>println(name))
    //在函数中参数只按照顺序使用了一次 那么参数可以省略
    //使用下划线打印这个参数
    func3(println(_))

  }

}
