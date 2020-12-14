package application

import common.CommonAppliction
import controller.HotProductTenController

object HotProductTenApplication extends CommonAppliction with App {
  startApp(appName = "HotProductTen"){
    //逻辑传进去
    val hotProductTenController = new HotProductTenController
    hotProductTenController.execute
  }
}
