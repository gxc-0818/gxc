object HelloScala {

  /**
   * object:scala语言是一个完全面向对象的语言 没有不是面向对象的语法 如基本数据类型
   * byte(1字节 8位) short int long float double char(没有负值 0-65535) boolean
   * object 模拟静态语法 可以简单理解为静态执行的方法
   *
   * def 声明方法
   * () 参数列表
   * args:Array[String] 参数
   *
   * java中 String[] args 因为java是强类型语言 更看重类型 所以类型放在前面
   * 但是scala不是那么看重类型 更看重名字 所以名字在前 类型在后
   *
   * Array 数组类 scala语言是一个完全面向对象的语言
   * []  scala中括号表示泛型
   * Unit 相当于java中的void 但是void是关键字 但是Unit是一个类型 没类型也是一个类型
   * 等号 赋值 将方法的实现逻辑赋值给方法
   * {} 方法体
   * System.out.println() scala语言来自于java 所以可以直接使用java代码
   *
   * 代码中没分号 一行代码一般就是写一段逻辑 所以划分不同的逻辑 不靠分号 靠回车 非要在一行里写多个逻辑 那就用分号隔开
   *
   *
   * 注意 scala是面向对象的语言 所以 scala中不存在静态方法（静态方法不是面向对象）理解参考test中sleep和wait的区别
   *
   *
   * sacla中标识符的基本用法和java一样
   * 关键字 保留字 行 数字不能开头 区分大小写
   * 避免使用$开头 可能会产生冲突
   * scala提供了更加丰富的标识符的命名 val + = "zhangsan" 可以 - ！ * / % ~ 总结：不用记。。出错了改就行了
   *
   * 非想用关键字做变量 可以用飘号  var `private` = "aaa"
   *
   * sacala中所有的表达式都有返回值
   * 返回值为满足条件的最后一行代码的执行结果
   *
   * kafka如何保证数据的有序？
   * 生产者有序(双端队列 要是发失败了 不用再从尾巴放 可以在从头放 来保证有序 1.6以后开始有的)  消费者分区内有序 分区间无序
   *
   **/




  def main(args: Array[String]): Unit = {
    System.out.println("Hello Scala");System.out.println("Hello Scala2")
    System.out.println("Hello Scala")
    var `private` = "aaa"

    10 //scala语言 万物皆对象 数字也是对象
    10+10 //所谓的运算符其实就是对象的方法 等同于下面 只不过省略写法后面函数会说的
    10.+(10)
    1 to 5

    //顺序执行 这会报错 右边赋给左边 还没声明 就去赋值
    //var i :Int= i


  }


  def print(aaa:String):Unit = {
    System.out.println(120/1.25)
  }


}

