import scala.util.control.Breaks. _;

object Opera {

  def main(args: Array[String]): Unit = {

    //for 循环
    //Todo 1 to  5 包含5
    for (i : Int <- 1 to 5){
      //println(i)
    }

    //Todo 1 until 5 不包含5
    for (i : Int <- 1 until 5){
      //println(i)
    }

    //Todo Range(1,5))不包含5 Range(1,5,2) 第三个参数是步长
    for (i : Int <- Range(1,5)){
      //println(i)
    }

    for (i : Int <- 1 to 5 by 2){ //步长是2
      //println(i) //1 3 5
    }

    for (i : Int <- 10 to 1 by -1){ //10 - 1
     // println(i)
    }

    //Todo 循环守卫 循环和if条件放在一起
    for (i : Int <- 1 to 5 if (i%2 == 0)){
      //println(i) //2 4
    }

    //Todo 嵌套循环 但是这种 代码只在内存循环的时候 才能用
    for (i : Int <- 1 to 5;j:Int <- 2 to 6){
      //println(i + j)
    }

    //Todo 引入变量 可以换成下面的 写在一起
    for (i <- 1 to 3){
      val j = i-1 //里面的变量用到了循环中变量
     // println(j)
    }

    for (i <- 1 to 3 ;j= i-1){
      //println(j)
    }

    //Todo 循环的返回值
    val result = for (i <- 1 to 3){//不知道给哪个结果 就什么也不给
      i
    }
    //println(result) //() unit

    val result1 = for (i <- 1 to 3) yield{//加上关键字yield 将每次循环的执行结果都给出来
      i
    }
    //println(result1)// Vector(1, 2, 3)

    //线程中yield方法 想要调用  带上飘号
    //Thread.`yield`()


    //Todo 循环中断
    //Scala 中没有continue关键字break关键字也没有
    //Scala中采用面向对象的方法实现break操作


    //Todo 这是个函数 但是用花括号 小括号也行 一般传进来的是一段逻辑的时候 用花括号看起来更有层次 Breaks.break()采用抛出异常的方式来改变循环操作 Breaks.breakable() 采用的是捕捉异常的方式来处理break方法带来的异常
    breakable{//把可能中断的程序放在这 就不会抛异常了
      for (i <- 1 to 3){
        if(i == 3){
          //Breaks.break()这里会有一个异常产生 后面的逻辑就执行不了了  现在很多时候 异常是可以用来改变逻辑的 catch不同的异常来走不同的逻辑 是很强大的
            break //上面进行了引入之后 前面的Breakers就可以去掉了 括号也可以去掉 因为是无参的函数调用
        }
        println(i)
      }
    }
    println("aaaa")

    //控制抽象 将代码作为参数进行传递 一般应用于框架 实现特殊语法

//    def oper(f:(Int) => Unit): Unit ={
//      f
//    }
    def oper(f: => Unit): Unit ={
      f
    }

    oper{
      println("opera") //把代码逻辑传进去
    }


    val i= 10 //10min
    val j= 10 //20min
    def test(i:Int,j:Int): Unit ={
      for (k <- 1 to i){
        println(i)//10min
      }
      for (m<- 1 to j){
        println(j)//20min
      }
    }

    //假设上面每一步分别的执行时间 则总共需要一个小时才能执行完 但是其实i和j的逻辑是完全没有关系的 可以分开的

    //Todo 函数的柯里化 将复杂的参数简单化 声明多个参数列表
    def test1(i:Int)(j:Int): Unit ={

    }

    //Todo 递归 函数内部调用自身
    //    存在跳出逻辑 传递参数时应该有规律 递归函数必须声明返回类型 不能省略

    def circle(num:Int):Int ={
      if (num <= 1){
        return 1
      }else{
       return num * circle(num -1)
      }
    }

    //注意这里 即使有跳出逻辑 你传个 10000000000 一样会栈溢出
    //println(circle(10))


    //Todo 尾(伪)递归 scala编译器在编译时会将尾递归编译为while死循环 java中没有优化 还是会报错
    def printCircle(): Unit ={
      println("circle")
      printCircle()
    }

    //Todo 惰性函数
    def fun(): String ={
      println("function")
      "aaa"
    }

    val a= fun()
    println("-----")
    println(a)

    //function
    //-----
    //aaa

    //Todo 惰性函数 延迟加载(分页查询)
    //Todo 如果 println("-----")花了十分钟 fun()f返回十万个对象 这时候十万个对象就会浪费内存十分钟
    //     这个时候就用关键字lazy

    lazy val b= fun()
    println("-----")
    println(b)

    //-----
    //function
    //aaa


    //分布式计算领域 数据拆开 逻辑不变



  }

}
