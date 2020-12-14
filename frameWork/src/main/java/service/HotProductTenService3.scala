package service

import bean.HotProductCategory
import common.{CommonConstant, CommonService}
import dao.HotProductTenDAO
import helper.HotProductAccumulator1
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import util.EnvUtil

import scala.collection.mutable

/**
 * 统计品类前十 优化3 累加器 一次shuffle都不要
 */
class HotProductTenService3 extends CommonService {
  val dao:HotProductTenDAO = new HotProductTenDAO()
  override def analyse = {

    val lines: RDD[String] = dao.getFileData("input/user_visit_action.txt")

    val sc: SparkContext = EnvUtil.getEnv
    val accumulator = new HotProductAccumulator1
    sc.register(accumulator)

    lines.foreach(line=>{
      val datas: Array[String] = line.split("_")
      if(datas(6) != "-1"){
        accumulator.add(datas(6),CommonConstant.Click)
      }else if (datas(8) != "null"){
        val ids: Array[String] = datas(8).split(",")
        ids.foreach(accumulator.add(_,CommonConstant.Order))
      }else if (datas(10) != "null"){
        val ids: Array[String] = datas(10).split(",")
        ids.foreach(accumulator.add(_,CommonConstant.Pay))
      }
    })

    val value: mutable.Map[String, HotProductCategory] = accumulator.value
    val resultMap: mutable.Map[String, (Int, Int, Int)] = value.map {
      case (cid, category) => {
        (cid, (category.clickCount, category.orderCount, category.payCount))
      }
    }
    resultMap.toList.sortBy(_._2)(Ordering.Tuple3[Int,Int,Int](Ordering.Int.reverse,Ordering.Int.reverse,Ordering.Int.reverse)).take(10)

  }
}
