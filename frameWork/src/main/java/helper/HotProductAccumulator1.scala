package helper

import bean.HotProductCategory
import common.CommonConstant
import org.apache.spark.util.AccumulatorV2

import scala.collection.mutable

class HotProductAccumulator1 extends AccumulatorV2[(String,Int),mutable.Map[String,HotProductCategory]]{
  var map = mutable.Map[String,HotProductCategory]()

  override def isZero: Boolean = {
    map.isEmpty
  }

  override def copy(): AccumulatorV2[(String, Int), mutable.Map[String, HotProductCategory]] = {
    new HotProductAccumulator1
  }

  override def reset(): Unit = {
    map.clear()
  }

  override def add(v: (String, Int)): Unit = {
    val cid = v._1
    val cType = v._2
    val hotProductCategory: HotProductCategory = map.getOrElse(cid,new HotProductCategory(cid,0,0,0))
    cType match {
      case CommonConstant.Click =>hotProductCategory.addClickCount(1)
      case CommonConstant.Order =>hotProductCategory.addOrderCount(1)
      case CommonConstant.Pay =>hotProductCategory.addPayCount(1)
    }
    map.update(cid,hotProductCategory)
  }

  override def merge(other: AccumulatorV2[(String, Int), mutable.Map[String, HotProductCategory]]): Unit = {
    val map1  = other.value
    map = map.foldLeft(map1){
      case (m,(k,v))=>{
        val hotProductCategory: HotProductCategory = m.getOrElse(k,new HotProductCategory(k,0,0,0))
        hotProductCategory.addClickCount(v.clickCount)
        hotProductCategory.addOrderCount(v.orderCount)
        hotProductCategory.addPayCount(v.payCount)
        m.updated(k,hotProductCategory)

      }
    }
  }

  override def value: mutable.Map[String, HotProductCategory] = {
    map
  }
}
