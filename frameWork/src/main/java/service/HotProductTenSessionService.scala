package service

import common.CommonService
import dao.HotProductTenSessionDAO
import org.apache.spark.rdd.RDD

/**
 * Top10热门品类中每个品类的Top10活跃Session统计
 */
class HotProductTenSessionService extends CommonService {
  val dao = new HotProductTenSessionDAO()
  override def analyse = {
    val tenService = new HotProductTenService3
    val topTenIds: List[String] = tenService.analyse.map(_._1)
    val lines: RDD[String] = dao.getFileData("input/user_visit_action.txt")
    val datas: RDD[String] = lines.filter(line => {
      val datas: Array[String] = line.split("_")
      datas(6) != "-1" && topTenIds.contains(datas(6))
    })
    val cGroup: RDD[(String, Int)] = datas.map(line => {
      val datas: Array[String] = line.split("_")
      (datas(6) + "-" + datas(2), 1)
    }).reduceByKey(_ + _)
    val cGroupMap: RDD[(String, Iterable[(String, Int)])] = cGroup.map {
      case (s, num) => {
        val strings: Array[String] = s.split("-")
        (strings(0), (strings(1), num))
      }
    }.groupByKey()
    cGroupMap.mapValues(iter=>{
      iter.toList.sortBy(_._2)(Ordering.Int.reverse).take(10)
    })




  }
}
