package aaa

//import aaa.java.util
//import aaa.java.util.HashMap
import aaa.test.Parent

import scala.beans.BeanProperty

object BeanProgram {
  /**
   * 面向对象编程
   * scala面向对象的语法和java基本一致
   *
   * 面向对象编程 - 包 - 、7
   * package
   * java中package的作用
   * 1 管理类  分门别类的管理
   * 2 权限管理
   * 3 区分相同名称的类 同名类不同包
   * 4 源码路径和包名相同
   *
   * 包名的起名规则 ：域名 + 项目名 + 模块名 + 程序类型名
   *             com.baidu.tets.user.bean
   *
   * www 是二级域名  不是顶级域名
   *
   * java中package的语法功能比较单一 所以scala进行了扩展
   * package关键字可以多次声明
   * 源码路径和包路径没有关系
   * package可以嵌套声明使用
   * package可以当成对象来用 并声明属性和函数
   * 同一个源码文件中子包可以直接访问父包中的内容 无需import
   *
   *
   *
   *面向对象编程 - 导入 - import
   * java import
   * 1 导入类
   * 2 静态导入
   *
   * java中import关键字的功能比较单一 所以scala进行了扩展
   *
   * 1 采用下划线代替* 导入一个包中的所有的类
   * 2 import关键字可以放置在任何地方
   * 3 scala才是真正的导包 (包对象的概念)
   *
   * 自己的写的类和jdk重名了怎么办 jdk不会使用自己的类 采用jdk的类 因为安全
   * 采用了一种特殊的类加载机制 这就是双亲委派机制
   *
   * java中加载器有三种
   * 1 启动类加载器 ： 加载JDK类库
   * 2 扩展类加载器 ： 加载JDK的扩展类库
   * 3 应用类加载器 ： 加载自己写的类 classpath下
   *
   * 自己写个类
   * 应用类加载器加载 但是不会立刻加载 会先委托上一级 扩展类加载器 扩展类加载器也不会立刻加载 会继续委托上一级 启动类加载器
   * 启动类加载器在自己的位置找到了 就用自己的类 你自己写的都不会看
   * 如果找不到 会告诉下一级扩展类加载器 扩展类加载器在自己找 找到了用自己的 找不到告诉下一级应用类加载器
   * 应用类加载器有就加载 没有就报类找不到的异常
   *
   * 不同的包里有相同的类名
   * 屏蔽类 import java.sql.{Date=>_, _}
   * 给类起别名   import  java.util.{HashMap=>JavaHashMap}  给类起别名
   *
   * scala中import操作使用相对包路径 以当前包为查找的基准路径
   * 如果不想使用相对包路径 需要采用特殊操作
   * new _root_.java.util.HashMap[String,String]()
   *
   * 相对路径 可以改变的路径 应该存在基准路径
   * ./ 当前  ../  上一层
   * source.fromFile("./input/word.txt")
   *
   * 绝对路径 不可改变的路径
   * 网络：http://www.baidu.com:8080/xxx/xxx.jpg
   * 本地: file:///c:/test/test.jpg
   *
   * Idea中文件的相对路径以project的根路径为基准
   *
   *
   * 面向对象编程 - 类
   * class 类名 { 类体 }
   * 声明的类必须构建对象才能访问其中的方法和属性
   *
   * 访问权限：权利和限制
   *
   * java中
   * 1 private:同类
   * 2 :同类同包
   * 3 protected:同类同包子类
   * 4 public:任意
   * 更多理解参考Test关于父类子类访问权限的举例
   *
   * finilize :析构方法  GC要回收一个对象的时候 申辩的机会 重新建立自己的关联 不让这个对象被回收 只会走一次
   *
   *
   * sacala访问权限
   * 1 private 私有
   * 2 private[包名] :包访问权限
   * 3 protected 受保护的 只能同类和子类使用 同包不能用
   * 4  :公共的
   *
   * 面向对象编程 -方法
   * 方法其实就是函数 只是声明在类中
   * 方法可以通过对象进行访问 函数无法在外部使用
   *
   *
   * 方法重写 在Test有详细的笔记
   * 方法重载 方法名相同 但是参数列表不一样(个数 顺序 类型)
   * 所谓的方法重载 其实就是不同的方法 只是巧了 方法名一样 靠参数列表来区分
   * 如果指定类型的参数不存在 那么会按照类型下限的方式往上查找类型
   *
   * Predef(预先声明) 一个伴生对象 类似静态导入
   *
   * val user = new User()
   * import user. _
   * test() //User类里的一个方法 这里不用user.test()
   * 导入对象的所有内容 在访问这个对象的成员方法和属性时 不需要增加对象
   * 导入对象功能只能对val声明的对象起作用
   *
   * O(open) C(close) P开发原则
   *
   *
   ** */


  def main(args: Array[String]): Unit = {
    //给类起别名
    //import  java.util.{HashMap=>JavaHashMap}
    //new JavaHashMap[String,String]()

    //import java.sql.{Date=>_, _}
    //new Date() //这个类上面两个包都有 怎么区分 是哪个包下的 可以屏蔽类

    //new HashMap; //自己本地声明的 在最下面
    //new _root_.java.util.HashMap[String,String]() //jdk的ashMap


    //* 面向对象编程 - 属性
    //* 所谓的属性 其实就会类中声明的变量
    //* 类的属性可以通过变量来访问

    val user = new User

    //* 使用val声明普通的变量 那么不会增加final操作
    //* 但是使用val声明类的属性 会增加final操作
    // val声明的 编译器在编译时不会提供对应的set方法
    //.password = "111" 这样会报错

    //scala中给一个属性赋值 其实不是赋值 是调用属性的set方法
    user.name = "bbb"
    //scala中访问属性 其实并不是访问属性 而是调用属性的get方法
    println(user.name)

    //如果给属性加上@BeanProperty注解 编译器在生成类时 会自动添加符合bean规范的set get方法
    user.setName("ccc")

    //当属性声明为private  那么编译器会在编译时 提供私有的set get方法
    //println(user.password)


      val p = new Parent
   // p.email 访问不到 私有的
   // p.sex  访问不到 不同包 可以把定义的那个包扩大 改成最上面的aaa就可以了
      p.haha

    //构造对象 new 类名 反射 clone
    //val stu = new Stu
    //new _root_.java.util.ArrayList().clone()

    //构造方法  scala中 类名和方法名相同 并不是构造方法 是普通方法
    //scala中 万物皆对象 万物皆函数
    //类也是函数 在类名后面增加参数列表 其实就是构造函数
    //如果构造方法有参数 在构建对象时必须传递构造参数 Stu(name :String) val stu = new Stu("aaa")
    val stu = new Stu("aaa") //会打印 init

    //如果在构造函数中传递参数时 那么可以在参数前增加val 或者var关键字 scala会将这个参数作为类的属性使用
    println(stu.name)

    //scala 构造方法分为2大类 主构造方法(类的初始化) 和 辅助构造方法
    //类名后的构造方法就是主构造方法
    //辅助构造方法主要使用this关键字 辅助构造方法在执行时 必须显示的调用(直接 间接)主构造方法 完成类的初始化
    //辅助构造方法存在重载的概念


    //再看如果忘记为啥 请看视频59
    //子类的构造方法先执行父类的 构造方法先执行完毕
    new son()//1111
               4444
               3333

    new son1()//1111
                2222
                4444
                3333


    //单例模式 创建对象不会释放 除非你明确的把它变为null 静态的东西在方法区 方法区没有自动释放的概念 不像成员方法 可以弹栈
    //scala 采用特殊的方式(object) 创建单例对象 编译时会编译两个类 一个就是当前类本身 另外一个就是单例对象的类

    //当调用object的方法时 其实就是调用单例对象的方法 所以模拟了静态语法 一般情况下 将这个类称之为伴生类 将单例对象称之为伴生对象
    //一般情况 可以将成员方法声明在伴生类中
    //将所谓的静态方法声明在伴生对象中

    //抽象 不完整
    //不完整的类成为抽象类 使用abstract声明的类 abstractTest
    //不完整的方法就是抽象方法 只有声明没有实现
    //抽象类不能直接构建对象 但是可以有子类继承后 重写抽象方法创建对象


    //抽象属性 不完整的属性就称之为抽象属性 只有声明 没有初始化  Java里没有这个概念 因为虚拟机帮你默认初始化了
    //如果属性不完整 就是抽象属性 对应的类就是抽象类
    //子类继承抽象类 将属性补充完整
    //java 没有抽象属性的概念 那么scala中抽象属性到底是什么
    //1 抽象属性在编译时 不会产生属性的 而是产生两个抽象的set get方法
    //2 子类重写父类的抽象属性时 其实就是产生属性以及实现 set get方法
    val child = new aTestChild()
    println(child.age)//0

    //构造对象 如果类的构造方法私有化了

    //伴生对象可以访问伴生类的私有方法和属性
    //val singleton= single.apply() 省略写法就是下面
    //apply 方法可以被编译器识别 所以可以省略
    //apply 方法可以重载
    val singleton = single()//apply方法
    val singleton1 = new single("aaa") //构造函数
    val singleton2 = single //伴生对象

    //apply 方法不仅仅可以声明在伴生对象中 还可以声明在伴生类中 通过对象调用 编译器一样可以识别
    //类名 加() 表示调用伴生对象的apply 方法 对象+() 表示调用伴生类的apply方法
    singleton1.apply()//可以省略成下面
    singleton1()


    //scala认为多个类可能存在相同的特征 那么可以将这个特征从类中剥离出来
    // 形成特殊的结构 然后如果类符合这个特征 那么可以混入这个特征
    //scala中采用特殊的关键字实现这种语法
    //scala中的trait可以简单地理解为接口和抽象类的结合体

    val person = new Person()
    person.run()

    val person1 = new Person() with PersonExt //动态混入 可以在不改变类的体系结构的情况下扩展类的功能 符合OCP开发原则
    person1.addFunction()

    //特质只和当前类相关 和父类子类无关 所以父类初始化优先于当前的特质
    //如果将特质理解为抽象类 特质的初始化会优先于当前类
    //如果混入多个特质 那么多个特质的初始化顺序为从左到右
    //特质的初始化只会初始化一次

    //功能叠加 其实就是多个相同功能的调用顺序问题
    //功能调用顺序为从右向左
    //super不是父类引用的意思 而是在编译时起了标记的作用
    //scala功能叠加时 super代表的不是上一级 而是上一个 参考那个图理解 super[新指向] 可以这样改调度顺序


    //扩展
//    val obj:Any = null
//    if(obj.isInstanceOf[User]){
//      val user1: User = obj.asInstanceOf[User]
//    }
    println(colour.RED)
    println(colour.HAHA)
  }


  object colour extends Enumeration{
    val RED = Value(1,"red")
    val Yellow = Value(2,"yellow")
    val HAHA = Value(3)
  }


  trait Run{
    def run():Unit
    def test(): Unit ={

    }
  }
  trait Jump{
    def jump():Unit
  }

  trait PersonExt{
    def addFunction(): Unit ={
      println("额外功能")
    }
  }

  //如果类有父类 可以采用with关键字混入 而且可以多次混入
  class Person extends User with Run with Jump {
    override def run(): Unit ={
      println("Person run...")
    }
    override def test(): Unit ={

    }

    override def jump(): Unit = {
      println("Person jump...")
    }
  }

  //如果类没有父类 可以采用extends关键字混入特质
  class Person1 extends Run with Jump {
    override def run(): Unit ={
      println("Person1 run...")
    }

    override def jump(): Unit = {
      println("Person1 jump...")
    }
  }


  //抽象类
  abstract class abstraTest{
    val name :String
    val email:String = null
    //如果希望scala类的属性和java一样 有系统的默认初始化 就用下划线
    var age :Int = _

    def test():Unit

    def aaa(): Unit ={

    }
  }


  class aTestChild extends abstraTest{

    override val name: String = "aaa"

    //完整属性的重写需要增加override关键字 而且需要声明为val 否则编译会报错
    override val email:String = "bbb"
    //重写抽象方法 可以不加override关键字 但是还是加上比较好 这样看起来会清晰一些
    def test(): Unit = {

    }
    //重写完整的方法 必须加上override关键字
    override def aaa(): Unit ={

    }

  }


  class single (name :String){ //伴生类

    def apply(){
      println("eeeee")
    }

  }

  //伴生对象可以直接使用 而无需构建 所以没有构造方法
  object single{//伴生对象
    def apply(){
      println("cccc")
      new single("single")
    }

    def apply(name:String){
      println("dddddd")
      new single(name)
    }
  }

  //构造方法私有化
  class Users private(){

  }

  class parent(name :String){
    println("1111")
    def this(){
      this("")
      println("2222")
    }
  }
  class son(name:String) extends parent(name) {
    def this(){
      this("")
      println("3333")
    }
    println("4444")
  }

  class son1(name:String) extends parent() {
    def this(){
      this("")
      println("3333")
    }

    println("4444")
  }


  class User{ //可以加访问权限
    //变量
    @BeanProperty
    var  name :String = "aaa"
    private val password = "11111"
  }


  class Stu(val name:String){
    //花括号里又是 函数体 & 类体

    println("init")
    println(name)

    def this(){
      this("aaa")
    }
    def this(int:Int){
      this()
    }

    def Stu(): Unit ={ //这个不是构造方法 这是个普通方法
      println("instance")
    }

  }






}

package test{
  class Parent{
    private val email:String = "aaa"
    private[test] val sex:String = "man"
    protected val address = "beijing"
    val haha = "hahha"
    def test(): Unit ={
      println(email)//本类可以用
      println(sex)
      println(address)
      println(haha)

    }
  }
  class Emp extends Parent{

    def test1(): Unit ={
      //println(email)
      println(sex)
      println(address)
      println(haha)

    }
  }

  class other{
    def test1(): Unit ={
      val p = new Parent
      println(p.sex)//无关类 同包可用
     // println(p.address) 子类和同类可以 同包也不行
      println(p.haha)

    }
  }
}

//package java {
//  package util {
//    class HashMap{
//
//    }
//  }
//}
