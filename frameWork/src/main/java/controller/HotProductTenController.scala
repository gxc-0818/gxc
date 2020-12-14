package controller

import common.CommonController
import service.HotProductTenService3

class HotProductTenController extends CommonController{
  private val hotProductTenService = new HotProductTenService3
  override def execute: Unit = {
    val result= hotProductTenService.analyse
    result.foreach(println)
  }
}
