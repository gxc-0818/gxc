package sparkcore

import org.apache.spark.rdd.RDD
import org.apache.spark.util.{AccumulatorV2, LongAccumulator}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

object Accumulator {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("Acc")
    val sc = new SparkContext(sparkConf)
    val rdd: RDD[Int] = sc.makeRDD(List(1,2,3,4),2)
    val sum: LongAccumulator = sc.longAccumulator("sum")

    //Todo 累加器 分布式共享的只写变量
    //spark 提供了一种特殊的数据结构 用于通知executor 在计算完毕后的数据返回到driver
    //driver会将多个executor 计算的结果合并在一起 获取最终的结果
    //这种数据结构称之为累加器

    //Todo spark默认提供的累加器有三种类型
    sc.longAccumulator("s")//整形
    sc.doubleAccumulator("d")//浮点类型
    sc.collectionAccumulator("c")//List集合类型

//    rdd.foreach(i=>{
//      sum.add(i)
//    })
//    println(sum.value)

    //Todo 自定义累加器实现 wordCount  是数据的累加 不是数值的累加
    //1 继承AccumulatorV2
    //2 定义泛型【In Out】
    //In 定义将什么类型的数据增加到累加器中
    //Out mutable.Map[String,Int] 定义将什么类型的数据返回
    //3 重写抽象方法 3状态3计算


    //先调copy 在调reset 在调isZero 不过会报错
    class WordCountAccumulator extends AccumulatorV2[(String,Int),mutable.Map[String,Int]]{

      //var map  = mutable.Map[String,Int] 这是伴生对象 那个单例对象
      var map: mutable.Map[String, Int] = mutable.Map[String,Int]() //这是调用伴生对象的apply方法 返回的是一个map的对象

      //当前的累加器是不是初始状态
      override def isZero: Boolean = {
        map.isEmpty
      }

      //复制累加器 调用次数和分区次数无关 和序列化的次数有关
      override def copy(): AccumulatorV2[(String, Int), mutable.Map[String, Int]] = {
        println("copy------------------")
        new WordCountAccumulator()
      }

      //重置累加器
      override def reset(): Unit = {
        map.clear()
      }

      //像累加器中增加数据
      override def add(t: (String, Int)): Unit = {
        val k = t._1
        val v = t._2
        map.update(k,map.getOrElse(k,0)+v)
      }

      //合并多个累加器的值 在driver端执行
      override def merge(other: AccumulatorV2[(String, Int), mutable.Map[String, Int]]): Unit = {
        val map1 = this.map
        val map2 = other.value
        this.map = map1.foldLeft(map2){//注意这里聚合后的新map要赋值给当前的累加器 作为下一次计算的值 不赋值跟没加一样
          case (m,(k,v))=>{
             m.updated(k,m.getOrElse(k,0)+v)
          }
        }
      }

      //返回累加器的计算结果
      override def value: mutable.Map[String, Int] = {
        map
      }
    }


    var rdd1= sc.makeRDD(List(("a",1),("b",2),("a",3),("b",5),("c",2)))
    println("分区数 "+rdd1.getNumPartitions)

    //1 创建累加器
    var acc = new WordCountAccumulator()

    //2 向spark注册累加器
    sc.register(acc)

    rdd1.foreach(
      //3 向累加器中累加数据
      t => acc.add(t)
    )

    //获取累加器的累加结果
    val value: mutable.Map[String, Int] = acc.value
    println(value)

    //累加器在多次行动算子执行时 数据可能计算有问题
    //一般推荐累加器在行动算子中执行 如果非要在转换算子中执行 需要保证行动算子只会执行一次



    sc.stop()





  }
}
