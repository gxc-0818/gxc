object Match1 {

  def main(args: Array[String]): Unit = {
      //Todo 模式匹配
      //java switch 多重分支结果
      //因为switch语法有歧义 所以sca啦没有这个语法 但是模拟了一种类似的语法 模式匹配
      //模式匹配主要用于规则匹配
      //模式匹配语法中没有break操作 执行完毕 直接退出
      //模式匹配中的下划线 表示除前置case语句外任意值 类似于java中的default
      //如果一个都匹配不上 会报错
      var a: Int = 10
      var b: Int = 20
      var operator: Char = '*'
      var result = operator match {
        case '+' => a + b
        case '-' => a - b
        case '*' => a * b
        case '/' => a / b
        case _ => "illegal" //case放最前面 结果就是illegal了
      }
      println(result)

    //模式匹配中不考虑泛型的 比如List[Int] 传字符串也匹配 但是Array不一样
    //Java中的Array没有泛型的概念 sacal中的泛型是特殊的 可以理解为Array[String]是一个整体类型

    //模式匹配应用场景
    val t = (1,"aaa",23)
    val (id,name,age)=(1,"aaa",23)
    println(t._1)
    println(id)
    val map = List((1,"qqq",2),(2,"bbb",3))

    //这是真正的用法 功能函数中使用
    //模式匹配 注意 要加case 还有 花括号
    val tuples: List[(Int, String, Int)] = map.map {
      case (id, name, age) => {
        (id, name, age * 2)
      }

    }
    println(tuples)//List((1,qqq,4), (2,bbb,6))


    val list = List(("a",1),("b",2),("c",3))
    println(list.map {
      case (_, count) => count
    }.sum) //6

    //todo 匹配对象


    //scala中匹配对象不是真正的匹配对象 是比较对象的所有属性是否相同
    //这里对象的属性比较是由scala自动完成的 但是需要遵循特殊的原则 伴生对象应该有一个unapply方法
    val user: User = User("zhangsan", 11)
    val result1 = user match {
      case User("zhangsan", 11) => "yes"
      case _ => "no"
    }
    println(result1)
    //User1("bbb",20).name = "aaa"

  }

 // class User(val name: String, val age: Int)
//  object User{
//    def apply(name: String, age: Int): User = new User(name, age)
//
//    def unapply(user: User): Option[(String, Int)] = {
//      Option(user.name,user.age)
//    }
//
//  }

 //不想写上面的一大堆 在累前使用case 关键字 这样的类叫做样例类
 //样例类在编译时自动实现可序列化接口 自动生成伴生对象
 //样例类也是个类 所以可以应用在各个场合
 //一般情况下样例类就是没有主体内容
 //样例类在构造对象时 构造参数可以不需要声明为val var
 // 默认使用val 如果需要更改 需要显示声明为var   User1("bbb",20).name = "aaa" 会报错
 case class User(val name: String, val age: Int)
 case class User1(name: String,age: Int)


}
