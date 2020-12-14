package application

import common.CommonAppliction
import controller.{HotProductTenController, ProductJumpController}

object ProductJumpApplication extends CommonAppliction with App {
  startApp(appName = "ProductJump"){
    //逻辑传进去
    val controller = new ProductJumpController
    controller.execute
  }
}
