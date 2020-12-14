package common

import util.EnvUtil

trait CommonAppliction {
  //限定访问范围 同类和子类  控制抽象逻辑传进来
  protected def startApp(master:String = "local[*]",appName:String)(f : =>Unit): Unit ={

    //隐式转换 putEnv里面有一个隐式变量 是字符串的 那么在外面 只要找到一个隐式的字符串变量就行了 就不用了传值了
    implicit val name:String = appName

    EnvUtil.putEnv(master)
    try {
      f
    }catch {
      case e:Exception=>println(e.getMessage)
    }
    EnvUtil.stopEnv
  }

}
