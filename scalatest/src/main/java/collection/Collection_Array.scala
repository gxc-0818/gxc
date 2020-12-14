package collection

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Collection_Array {

  /**
   * 序列 Seq 映射 Map 集 Set
   * 对于几乎所有集合 scala都同时提供了可变和不可变版本  这里的不可变不是变量本身的值不可变 而是变量指向的那个内存地址不可变
   * immutable 不可变
   * */

  def main(args: Array[String]): Unit = {
    //Todo 集合 数组
    //scala中默认的集合都是不可变的
    //scala中的数组在编译时其实就是编译为java中的数组

    //创建
    val strings = new Array[String](5)

    //创建的同时初始化 相当于调用伴生对象的apply方法
    val ints = Array(1,2,3,4,5)
    val strings3 = Array("aaa","bbb","ccc")

    ints.update(2,5)//把索引2的位置改为5 其实就是 ints[2] =5

    //数组访问 scala中中括号有特殊含义 所以不能作为索引进行访问
    strings(0) = "aaa"
    //println(strings(0))


    //添加数据
    val strings1 = strings:+"bbb"
    //scala中如果不采用方法调用 而是采用运算符的方式调用方法的话
    //如果这个运算符以冒号结尾 那么运算规则为从右向左执行
    val strings2 = strings +: "ccc" // 相当于 "ccc"+(array)

    println(strings1.mkString(","))//aaa,null,null,null,null,bbb
    println(strings2.mkString(","))//[Ljava.lang.String;@2812cbfa,c,c,c


    //遍历数组
//    for (s <- strings){
//      println(s)
//    }
    println(strings.mkString(","))//aaa,null,null,null,null

    //foreach方法就是为了遍历数据 所以将循环的每一个数据传递到处理函数中
    strings.foreach(println(_))//传一个函数进来

    val array:Array[Int] = Array(1,2,3,4)
    val array1:Array[Int] = Array(5,6,7,8)
    val array2:Array[Int] = array.++:(array1)
    println(array2.mkString(","))//5,6,7,8,1,2,3,4 我的理解 冒号对着谁 谁在前

    //多维数组
    val array3: Array[Array[Int]] = Array.ofDim(3,3) //三行三列的矩阵
    array3.foreach(
      array=>{
        println(array.mkString(","))
      }
    )

    //合并数组
    val array4: Array[Int] = Array.concat(array,array1)
    println(array4.mkString(","))//1,2,3,4,5,6,7,8

    val array5: Array[Int] = Array.range(0,5) //不包含第二个数
    println(array5.mkString(","))//0,1,2,3,4


    //创建并填充指定数量的数组
    val array6: Array[Int] = Array.fill[Int](5)(-1)
    println(array6.mkString(","))//-1,-1,-1,-1,-1

    //Todo 数组 可变
    //默认情况下 scala提供的数组不可变 但是也可以提供可变的数组
    val buffer1 = new ArrayBuffer[String]()
    val buffer = ArrayBuffer(1,2,3,4)
    val buffer2 = ArrayBuffer(5,6,7,8)

    //增加数据 可变数组 增加数据 可以对原来的集合进行改变
    buffer.append(5)//追加
    buffer.insert(1,6)//在1的位置 添加6
    buffer.appendAll(buffer2)

    //删除数据 按位置删
    buffer.remove(2)
    buffer.remove(2,2)//从2开始删 删两个 多删会报错 越界异常

    //不可变集合的操作    println(buffer)都使用运算符 可变集合的操作都使用方法

    //不可变 =》 可变
    val buffer3: mutable.Buffer[String] = strings.toBuffer

    //可变 => 不可变
    val array7: Array[Int] = buffer.toArray









  }

}
