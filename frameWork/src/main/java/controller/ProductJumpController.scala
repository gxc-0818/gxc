package controller

import common.CommonController
import service.{HotProductTenService3, ProductJumpService}

class ProductJumpController extends CommonController{
  private val productJumpService = new ProductJumpService
  override def execute: Unit = {
    val result= productJumpService.analyse
    result.foreach(println)
  }
}
