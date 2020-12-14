package sparksql

import org.apache.spark.sql.expressions.Aggregator
import org.apache.spark.sql.{Encoder, Encoders, SparkSession, functions}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
 * 需求：各区域热门商品 Top3
 * 需求简介
 * 这里的热门商品是从点击量的维度来看的，计算各个区域前三大热门商品，并备注上每个商品在主要城市中的分布比例，超过两个城市用其他显示。
 * */
object SparkHiveTest {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder().master("local[8]").appName("ProductThree").enableHiveSupport().getOrCreate()
    spark.sql("use gxc")

    val udaf = new remark
    spark.udf.register("udaf",functions.udaf(udaf))

    spark.sql(
      """
        |select
        |a.*,
        |b.product_name,
        |c.city_name,
        |c.area
        |from user_visit_action a
        |join product_info b on a.click_product_id = b.product_id
        |join city_info c on a.city_id = c.city_id
        |where a.click_product_id !=-1
        |""".stripMargin).createOrReplaceTempView("t1")

    spark.sql(
      """
        |select area,product_name,count(*) clickCount,udaf(city_name) remark from t1 group by area,product_name
        |""".stripMargin).createOrReplaceTempView("t2")

    spark.sql(
      """
        |select  area,product_name,clickCount,remark,rank() over(partition by area order by clickCount) as rank from t2
        |""".stripMargin).createOrReplaceTempView("t3")

    spark.sql(
      """
        |select area,product_name,clickCount,remark from t3 where rank <= 3
        |""".stripMargin).show(false)


  }

  case class Buff(var total:Long,var map: mutable.Map[String,Long])

  //自定义城市备注聚合函数
  class remark extends Aggregator[String,Buff,String]{
    override def zero: Buff = {
      Buff(0L,mutable.Map[String,Long]())
    }

    override def reduce(b: Buff, a: String): Buff = {
      b.total+=1
      val newCount: Long = b.map.getOrElse(a,0L)+1
      b.map.update(a,newCount)
      b
    }

    override def merge(b1: Buff, b2: Buff): Buff = {
      b1.total = b1.total+b2.total
      val map1: mutable.Map[String, Long] = b1.map
      val map2: mutable.Map[String, Long] = b2.map
      map2.foreach{
        case (city,count)=>{
          val newCount: Long = map1.getOrElse(city,0L)+count
          map1.update(city,newCount)
        }
      }
      b1.map=map1
      b1
    }

    override def finish(reduction: Buff): String = {
      val total = reduction.total
      val map = reduction.map
      val list: List[(String, Long)] = map.toList.sortBy(_._2).take(2)
      val has2More = map.size > 2
      val listBuffer = ListBuffer[String]()
      var left:Double = 100.0
      list.foreach{
        case (city,count) =>{
          val percent: Double = count * 100.0 /total
          left = left-percent
          listBuffer.append(city+"%1.2f".format(percent)+"%")
        }
      }
      if (has2More){
         listBuffer.append("其他"+"%1.2f".format(left)+"%")
      }
      listBuffer.mkString(",")
    }

    override def bufferEncoder: Encoder[Buff] = Encoders.product

    override def outputEncoder: Encoder[String] = Encoders.STRING
  }


}
