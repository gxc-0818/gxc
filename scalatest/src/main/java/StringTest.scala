object StringTest {
  /*
   变量 采用var或者val声明
   var声明的是可变的
   val声明的值是不可变的 称之为不可变变量 类似于java中的final 不要叫成常量 （总不能管 1 true这种叫变量 这块的理解 参考Test里静态属性初始化和静态代码块的关系）

   变量必须初始化 java中也是 只声明一个变量不初始化 都不会分配内存空间

   网络中传递不了对象 也传递不了数字
   Json JavaScript Object Notation 可以解析
   */

  def main(args: Array[String]): Unit = {
    var name = "aaa"
    val password = "123456"

    //字符串拼接
    println("name "+name)

    //传值字符串 f是format
    printf("name=%s \n",name)

    //插值字符串 注意前面的s 不加s就无法解析进行值得插入了
    printf(s"name = ${name} pasword = ${password} \n")
    printf("name = ${name}")

    //多行字符串 在封装JSON或SQL时比较常用 使用插值字符串生成json字符串会有问题
    // 三对双引号 竖线是顶格符 相当于 前面是一堵墙 就不会内容打印之前的空格了 顶格符可以自己设置 默认是竖线
    val ss=
      //这里的s记住不要少 不然下面的插值还是识别不了
      s"""
        |{ "name":"${name}","password":"${password}"}
        |""".stripMargin

    var sql =
      """
        |select
        |*
        |from t_user
        |where id = 1
        |group by name,password
        |""".stripMargin('#')//这里可以自定义顶格符 传一个字符

    println(ss)


    val a1:String = new String("aaa")
    val a2:String = new String("aaa")
    println(a1 == a2)//true 比较内容 scala把==包装了一下 其实这是判断非空的equals 不信自己去编译一下字节码文件
    println(a1.equals(a2))//比较内容
    println(a1.eq(a2))//比较内存地址 里面是java的 == 其实

    val a3:String = null
    val a4:String = null
    println(a3 == a4) //true





  }
}
