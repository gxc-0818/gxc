package service

import common.CommonService
import dao.HotProductTenDAO
import org.apache.spark.rdd.RDD

//Todo 每个页面的平均停留时间

/**
 * 统计品类前十 排序规则(点击 订单 支付)
 * */
class HotProductTenService extends CommonService {
  val dao:HotProductTenDAO = new HotProductTenDAO()
  override def analyse = {
    //Todo 1获取用户行为数据
    val lines: RDD[String] = dao.getFileData("input/user_visit_action.txt")

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

    //Todo 组合到一起 (品类 (点击数 下单数 支付数)) 再排名 取前十
    val coRdd: RDD[(String, (Iterable[Int], Iterable[Int], Iterable[Int]))] = clickRdd.cogroup(orderRdd,payRdd)

    //Todo 将不同的统计结果按照品类汇总到一起
    val coCountRdd: RDD[(String, (Int, Int, Int))] = coRdd.map {
      case (cid, (t1, t2, t3)) => {
      var count1 = 0
      var count2 = 0
      var count3 = 0
      if (t1.iterator.hasNext) {
        count1 = t1.iterator.next()
      }
      if (t2.iterator.hasNext) {
        count2 = t2.iterator.next()
      }
      if (t3.iterator.hasNext) {
        count3 = t3.iterator.next()
      }
      (cid, (count1, count2, count3))

    }}

    //元组是可以排序的 先比第一个 第一个相同再比第二个
    coCountRdd.sortBy(_._2,false).take(10)
  }
}
