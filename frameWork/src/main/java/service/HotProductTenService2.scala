package service

import common.CommonService
import dao.HotProductTenDAO
import org.apache.spark.rdd.RDD

/**
 * 统计品类前十 优化2 reduceByKey多 shuffle多
 */
class HotProductTenService2 extends CommonService {
  val dao:HotProductTenDAO = new HotProductTenDAO()
  override def analyse = {
    //Todo 1获取用户行为数据
    val lines: RDD[String] = dao.getFileData("input/user_visit_action.txt")

    //(cid,(1,0,0)) 或者 (cid,(0,1,0)) 或者(cid,(0,0,1))
    val mapDatas: RDD[(String, (Int, Int, Int))] = lines.flatMap {
      line => {
        val datas = line.split("_")
        if (datas(6) != "-1") {
          List((datas(6), (1, 0, 0)))
        } else if (datas(8) != "null") {
          val ids: Array[String] = datas(8).split(",")
          ids.map((_, (0, 1, 0)))
        } else if (datas(10) != "null") {
          val ids: Array[String] = datas(10).split(",")
          ids.map((_, (0, 0, 1)))
        } else {
          Nil.iterator
        }
      }
    }

    mapDatas.reduceByKey {
      case ((c1, c2, c3), (c4, c5, c6)) => {
        (c1 + c4, c2 + c5, c3 + c6)
      }
    }.sortBy(_._2,false).take(10)







  }
}
