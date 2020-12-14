package sparksql

import org.apache.spark.sql.expressions.Aggregator
import org.apache.spark.sql.{DataFrame, Dataset, Encoder, Encoders, SparkSession, TypedColumn}

/**
 *
 * */
object SparkSqlUDAF {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder().appName("SparkSqlUDAF").master("local[8]").getOrCreate()
    import spark.implicits._
    val dataFrame: DataFrame = spark.read.json("input/test.json")

    val myAvg = new MyAvg

    // Spark3.0版本前，无法将强类型聚合函数使用在SQL文中
    // 早期版本中，将数据的一行当成对象传递个聚合函数
    // DSL
    val column: TypedColumn[User, Long] = myAvg.toColumn
    val ds: Dataset[User] = dataFrame.as[User]
    ds.select(column).show()

  }

  case class User(username:String,age:Long)
  case class Buff(var sum:Long, var count:Long)

  class MyAvg extends Aggregator[User,Buff,Long]{
    override def zero: Buff = {
      Buff(0L,0L)
    }

    override def reduce(b: Buff, a: User): Buff = {
      b.sum+=a.age
      b.count+=1
      b
    }

    override def merge(b1: Buff, b2: Buff): Buff = {
      b1.sum+=b2.sum
      b1.count+=b2.sum
      b1
    }

    override def finish(reduction: Buff): Long = {
      reduction.sum/reduction.count
    }

    override def bufferEncoder: Encoder[Buff] = Encoders.product

    override def outputEncoder: Encoder[Long] = Encoders.scalaLong
  }
}
