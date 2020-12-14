package bean

case class HotProductCategory(
 cid:String,
 var clickCount:Int,
 var orderCount:Int,
 var payCount:Int
){

  def addClickCount(addValue:Int): Unit ={
    clickCount = clickCount+addValue
  }
  def addOrderCount(addValue:Int): Unit ={
    orderCount = orderCount+addValue
  }
  def addPayCount(addValue:Int): Unit ={
    payCount = payCount+addValue
  }
}

