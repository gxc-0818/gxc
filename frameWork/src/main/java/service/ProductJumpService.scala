package service

import common.CommonService
import dao.ProductJumpDAO
import org.apache.spark.rdd.RDD

/**
 * 页面单跳转换率统计
 * */
class ProductJumpService extends CommonService {
  val dao = new ProductJumpDAO()
  override def analyse = {
    val lines: RDD[String] = dao.getFileData("input/user_visit_action.txt")
    val pageRdd: RDD[(String, Int)] = lines.map(line => {
      val datas: Array[String] = line.split("_")
      (datas(3), 1)

    }).reduceByKey(_ + _)

    //按照页面分组聚合
    val pageMap: RDD[Map[String, Int]] = pageRdd.map{case(k,v)=>Map[String,Int](k,v)}

    val sesssionGroup: RDD[(String, Iterable[(String, String, String)])] = lines.map(line => {
      val datas: Array[String] = line.split("_")
      (datas(0), datas(2), datas(3))
    }).groupBy(_._2).sortBy(_._1)



    lines.collect()

  }
}
