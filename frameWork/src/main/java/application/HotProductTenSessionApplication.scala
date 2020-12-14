package application

import common.CommonAppliction
import controller.{HotProductTenController, HotProductTenSessionController}

object HotProductTenSessionApplication extends CommonAppliction with App {
  startApp(appName = "HotProductTenSession"){
    //逻辑传进去
    val controller = new HotProductTenSessionController
    controller.execute
  }
}
