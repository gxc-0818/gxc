package sparksql

import org.apache.spark.sql.expressions.Aggregator
import org.apache.spark.sql.{DataFrame, Encoder, Encoders, SparkSession, functions}

/**
 * Spark3.0版本可以采用强类型的Aggregate方式代替UserDefinedAggregateFunction
 */
object SparkSqlUDFAggregate {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder().master("local[8]").appName("UDFAggregate" ).getOrCreate()
    val df: DataFrame = spark.read.json("input/test.json")
    df.createOrReplaceTempView("user")

    //创建聚合函数
    val myAvg = new MyAvg

    // SQL本身就是弱类型操作，支持弱类型的聚合函数,不能直接支持强类型的聚合函数 这里需要转一下
    spark.udf.register("myAvg",functions.udaf(myAvg))

    spark.sql("select myAvg(age) from user").show()

    spark.stop()



  }

  //自定义年龄平均值的 聚合函数
  case class Buff( var sum:Long, var cnt:Long)

  class MyAvg extends Aggregator[Long,Buff,Long]{
    override def zero: Buff = {
      Buff(0,0)
    }

    override def reduce(b: Buff, a: Long): Buff = {
      b.sum+=a
      b.cnt+=1
      b
    }
    override def merge(b1: Buff, b2: Buff): Buff = {
      b1.sum+=b2.sum
      b1.cnt+=b2.cnt
      b1
    }
    override def finish(reduction: Buff): Long = {
      reduction.sum/reduction.cnt
    }

    override def bufferEncoder: Encoder[Buff] = Encoders.product

    override def outputEncoder: Encoder[Long] = Encoders.scalaLong
  }


}
