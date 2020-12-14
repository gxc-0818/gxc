package common

import util.EnvUtil
/**
 * DAO:Data Access object 数据访问对象
 *
 * */
trait CommonDAO {

  def getFileData(path: String) ={
     EnvUtil.getEnv.textFile(path)
  }

  def getMemoryData: Unit ={

  }

}
