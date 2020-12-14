package controller

import common.CommonController
import service.{HotProductTenService3, HotProductTenSessionService}

/**
 * session 会话
 * user session
 * request 用户向服务器发送的数据
 * response 服务器向用户发送的数据
 * */
class HotProductTenSessionController extends CommonController{
  private val hotProductTenSessionService =  new HotProductTenSessionService
  override def execute: Unit = {
    val result= hotProductTenSessionService.analyse
    result.foreach(println)
  }
}
