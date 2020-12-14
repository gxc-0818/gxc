package util

import org.apache.spark.{SparkConf, SparkContext}

/***
 * ThreadLocal 类似于线程的工具类 可以对线程的内存进行处理
 */

object EnvUtil {
  private val threadLocal = new ThreadLocal[SparkContext]
  def putEnv(master:String)(implicit appName:String) ={
     val sparkConf: SparkConf = new SparkConf().setMaster(master).setAppName(appName)
     val sc = new SparkContext(sparkConf)
     threadLocal.set(sc)
  }
  def getEnv={
    threadLocal.get()
  }

  def removeEnv: Unit ={
    threadLocal.remove()
  }
  def stopEnv: Unit ={
    threadLocal.get().stop()
    threadLocal.remove()
  }

}
