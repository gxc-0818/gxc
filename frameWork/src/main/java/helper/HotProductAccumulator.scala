package helper

import bean.HotProductCategory
import org.apache.spark.util.AccumulatorV2

import scala.collection.mutable

class HotProductAccumulator extends AccumulatorV2[(String,String),mutable.Map[String,HotProductCategory]]{
  var map = mutable.Map[String,HotProductCategory]()

  override def isZero: Boolean ={
    map.isEmpty
  }

  override def copy(): AccumulatorV2[(String, String), mutable.Map[String, HotProductCategory]] = {
    new HotProductAccumulator
  }
  override def reset(): Unit = {
    map.clear()
  }

  override def add(v: (String, String)): Unit = {
    val cid = v._1
    val cType = v._2

    val category: HotProductCategory = map.getOrElse(cid,new HotProductCategory(cid,0,0,0))
    cType match {
      case "click"=> category.clickCount = category.clickCount+1
      case "order"=> category.orderCount = category.orderCount+1
      case "pay" => category.payCount = category.payCount+1
    }
    map.update(cid,category)
  }

  override def merge(other: AccumulatorV2[(String, String), mutable.Map[String, HotProductCategory]]): Unit = {
    val map1 = this.map
    val map2 = other.value
    map1.foldLeft(map2){
      case (m,(k,v))=>{
        val category: HotProductCategory = m.getOrElse(k,new HotProductCategory(k,0,0,0))
        category.clickCount +=v.clickCount
        category.orderCount +=v.orderCount
        category.payCount += v.payCount
        m.update(k,category)
        m
      }
    }
  }

  override def value: mutable.Map[String, HotProductCategory] = {
    map
  }
}
