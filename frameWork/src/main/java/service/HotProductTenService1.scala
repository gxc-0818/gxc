package service

import common.CommonService
import dao.HotProductTenDAO
import org.apache.spark.rdd.RDD

/**
 * 统计品类前十 优化1 避免笛卡尔积 coGroup shuffle
 */
class HotProductTenService1 extends CommonService {
  val dao:HotProductTenDAO = new HotProductTenDAO()
  override def analyse = {
    //Todo 1获取用户行为数据
    val lines: RDD[String] = dao.getFileData("input/user_visit_action.txt")

    //将RDD的数据缓存起来 用于重复读取
    lines.cache()

    //Todo 2 筛选数据
    //点击数
    val clickRdd: RDD[(String, Int)] = lines.filter(line => {
      val datas: Array[String] = line.split("_")
      datas(6) != "-1"
    }).map(line => {
      val datas: Array[String] = line.split("_")
      (datas(6), 1)
    }).reduceByKey(_ + _)

    //下单数
    val orderRdd: RDD[(String, Int)] = lines.filter(lines => {
      val datas: Array[String] = lines.split("_")
      datas(8) != "null"
    }).flatMap(
      line => {
        val datas = line.split("_")
        val ids = datas(8).split(",")
        ids.map((_, 1))
      }
    ).reduceByKey(_ + _)

    //支付数
    val payRdd: RDD[(String, Int)] = lines.filter(lines => {
      val datas: Array[String] = lines.split("_")
      datas(10) != "null"
    }).flatMap(
      line => {
        val datas = line.split("_")
        val ids = datas(10).split(",")
        ids.map((_, 1))
      }
    ).reduceByKey(_ + _)

    val clickSumRdd: RDD[(String, (Int, Int, Int))] = clickRdd.map {
      case (cid, num) => (cid, (num, 0, 0))
    }

    val orderSumRdd: RDD[(String, (Int, Int, Int))] = orderRdd.map {
      case (cid, num) => (cid, (0, num, 0))
    }

    val paySumRdd: RDD[(String, (Int, Int, Int))] = payRdd.map {
      case (cid, num) => (cid, (0, 0, num))
    }

    val coRdd: RDD[(String, (Int, Int, Int))] = clickSumRdd.union(orderSumRdd).union(paySumRdd).reduceByKey {
      case ((c1, c2, c3), (c4, c5, c6)) => {
        (c1 + c4, c2 + c5, c3 + c6)
      }
    }
    coRdd.sortBy(_._2,false).take(10)





  }
}
