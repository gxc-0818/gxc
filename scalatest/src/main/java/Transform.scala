/**
 * 隐式转换
 * 注意是按照类型进行转换
 *
 * */
object Transform {

  //程序已经编译出现错误 编译器会尝试在指定范围内查找指定的转换规则 加implicit 关键字
  //二次编译 这个转换是由编译器自动完成的 所以称之为隐式转换

  def main(args: Array[String]): Unit = {

    val s = "hello"
    println(s(0))

    val i = 1
    println(i.toChar) //int转为字符串了

    //隐式转换函数 可以被编译器识别 注意啊 我声明了隐式类UserExt1以后 就得注掉这个转换了 不然两个转换规则 编译器报错
//    implicit def transform(user:User): UserExt ={
//      new UserExt
//    }

//    val user = new User() with Parent
//    user.insertUser()
//    user.updateUser()

     val user = new User()
     user.insertUser()
     user.updateUser()//编译出现错误 =》去找隐式转换规则
     //所谓的隐式转换其实就是通过类型的转换实现编译操作
     //如果在同一个作用域范围内 有相同的多个转换规则 会发生错误 跟名称没关系

    //隐式参数 告诉编译器这个参数有可能会改
    def register(implicit password:String ="000000",name:String = "aaa"): Unit ={
      println(s"password = ${password},name = ${name} ")
    }
    //隐式变量
    implicit val pass = "123123" //参数列表中所有的字符串参数都会被替换 是按类型替换的 跟名字无关
    register //隐式参数式可以不需要传递并调用参数列表
    register()//如果使用小括号那么隐式变量不起作用
    register("aaaaaa")

    //Todo 隐式机制
    //当前作用域中可以使用隐式转换
    //父类和父类的伴生对象的作用域范围
    //混入的特质以及特质的伴生对象
    //包对象的作用域
    //用的时候直接导入(没关系的直接导)
    //import默认会直接导三个不同类型的东西
    //a java.lang._  b. scala._ c. scala.Predef._

  }
  class UserExt{
    def updateUser(): Unit ={
      println("updateUser")
    }
  }

  //隐式类 在类的面前增加implicit关键字
  //必须提供一个有参的构造方法 你得告诉转换谁
  implicit class UserExt1(user:User){
    def updateUser(): Unit ={
      println("updateUser")
    }
  }

  trait Parent{
    def updateUser(): Unit ={
      println("updateUser")
    }
  }
  implicit def test(i:Int): Unit ={
    i.toString
  }
  class User{
    def insertUser(): Unit ={
      println("insert user")
    }
  }

}
