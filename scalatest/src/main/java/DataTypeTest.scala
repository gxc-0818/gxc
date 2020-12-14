//数据类型
object DataTypeTest {
  def main(args: Array[String]): Unit = {
    val b :Byte = 10
    val s :Short = 10
    val i :Int = 10
    val l :Long = 10
    val c :Char = 'A'
    val f :Float = 5.0f
    val d :Double=5.0
    val flag :Boolean = true


    //20.var 试一下 就知道
    // .scala中默认的整数数值类型是Int 默认的浮点型类型是double
    val i1: Int = 20

    //Unit是一个类型 表示没有返回值的意思 它的对象是()
    val u = test()
    //println(u)//打印结果是 ()

    //Null在scala中也是一个类型 null是这个类型的对象
    // Null是所有AnyRef子类（字符串 集合 所有java类 其他Scala类）的子类 所以null可以给一个字符串类型的变量
    val ss:Null = null
    val sn:String = null

    //Any是通用类型 它在最上面 所以给int或者字符串都可以
    val a1:Any = 1
    val a2:Any = "aaa"

    val cc:Char = 'A'+1//这里虽然标红但是是可以执行的 结果是B 因为编译时可以计算常量
    println(cc)

    //val ccc:Char = c+1;//这里就不能运行 这是变量

    //原因 编译器可以在编译的时候进行常量计算 但是变量的值只有在运行是才能知道 所以一个能执行一个不行







  }



  def test(): Unit ={

  }


  //还有一个Nothing 一种特殊类型 表示没有数据操作返回 或者可以理解为没有正常值
  def test1()={
    throw new Exception //特殊情况没有返回的时候 就是nothing 可以代替任何类型
    //return ""
  }
}
