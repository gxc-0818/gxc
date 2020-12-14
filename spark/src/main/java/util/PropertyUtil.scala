package util

import java.util
import java.util.ResourceBundle

import scala.collection.mutable.ListBuffer

object PropertyUtil {
  def main(args: Array[String]): Unit = {
    val bundle: ResourceBundle = ResourceBundle.getBundle("test")
    println(bundle.getString("aaa"))

    val buffer = ListBuffer(1,2,3)
    import scala.collection.JavaConverters._
    val list: util.List[Int] = buffer.asJava
  }

}
