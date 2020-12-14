package function

object FunctionEasy {
/**
 * 函数：封装的功能
 * 可以简单地理解为方法的封装
 * 对于函数式编程来讲 IO最重要 即 函数的输入和输出
 * */

  def main(args: Array[String]): Unit = {

    test()
    //Todo 在类里声明的是方法 可以重写和重载 其余的都是函数 名字一样 就会报错
    def test(): Unit ={
      println("test")
    }

    func1()
    //func1 //参数列表没参数的时候 调用时可以省略括号
    //无参 无返回值
    def func1():Unit={
      println("func1")
    }
    //无参 有返回值
    def func2(): String ={
      return "aaa"
    }
    val a = func2()
    println(a)

    //有参 无返回值
    def func3(a:String):Unit={
      println("func3:"+a)
    }
    func3("aa")

    //有参 有返回值
    def func4(name :String): String ={
      return "name = "+name
    }
    val v4= func4("jcy")
    println(v4)

    //多参 无返回值 可变参数 类型后面加一个*号 :0个或者多个值 但是一定需要放在参数列表的最后 放在前面 后面的参数不知道怎么取值
    //前一个参数也不能给默认值 因为有默认值 可以不传 后面的可变参数列表就蒙圈了

    def func5(name: String,args:String*): Unit ={
      println(s"name =${name} "+args) //如果多个参数传值了 是一个数组 不传值是一个空集合
    }
    func5("jcy","hehe","jajaja")//传了多个值
    func5("jcy empty")//后面一个都没传

    def funcMul(name:String*): Unit ={
      println(name)
    }
    funcMul()//打印结果 List()

    //多参 有返回值   1可以给参数一个默认值 传值的时候默认值不生效 2想使用默认值导致顺序问题 参数对不上的时候 可以指明参数
    def func6(password:String = "12345",name :String):String ={
      return s"name=${name} password= ${password}"
    }
    val v6 = func6(name = "aaa");//带名参数
    val v61 = func6("111","bbb")
    println(v6)
    println(v61)
  }

  def test(): Unit ={

  }
  def test(s:String): Unit ={

  }
}
