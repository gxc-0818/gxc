package sparksql

import java.util.Properties

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, SaveMode, SparkSession}

object SparkMysql {

  // case class 类的声明需要放在 main 函数外面。

  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder().master("local[8]").appName("sparkMysql").getOrCreate()
    import spark.implicits._
    //Todo 读取数据
    //Todo 1 通用的load方法
    val dfLoad: DataFrame = spark.read.format("jdbc")
      .option("url", "jdbc:mysql://gxc188:3306/gxc")
      .option("driver", "com.mysql.jdbc.Driver")
      .option("user", "root")
      .option("password", "root")
      .option("dbtable", "test")
      .load()
    //dfLoad.show()

    //Todo 2 通用的load方法读取 参数另一种形式
    val dfLoadMap: DataFrame = spark.read.format("jdbc").options(Map("url"->"jdbc:mysql://gxc188:3306/gxc?user=root&password=root","dbtable"->"test","driver"->"com.mysql.jdbc.Driver")).load()
    //dfLoadMap.show()

    //Todo 3 使用jdbc读取
    val properties = new Properties()
    properties.setProperty("user","root")
    properties.setProperty("password","root")
    val dfjdbc: DataFrame = spark.read.jdbc("jdbc:mysql://gxc188:3306/gxc","test",properties)
    //dfjdbc.show()

    //Todo 写入数据

    //todo 1通用的方式 format指定写出类型
    val rdd: RDD[User] = spark.sparkContext.makeRDD(List(User(1,"ccc",100)))
    val ds: Dataset[User] = rdd.toDS
    ds.write.format("jdbc")
        .option("url","jdbc:mysql://gxc188:3306/gxc")
        .option("user","root")
        .option("password","root")
        .option("dbtable","test")
        .mode(SaveMode.Append)
        .save()

    //todo 2 通过jdbc方法
    val rdd1: RDD[User] = spark.sparkContext.makeRDD(List(User(10,"lisi",100)))
    val ds1: Dataset[User] = rdd1.toDS
    val properties1 = new Properties()
    properties1.setProperty("user","root")
    properties1.setProperty("password","root")
    ds1.write.mode(SaveMode.Append).jdbc("jdbc:mysql://gxc188:3306/gxc","test",properties1)

    spark.stop()


  }

  //这个样例类必须写在main函数外 因为用到了隐式转换 外部是不可以用的 必须声明在外面才可以
  //只是在当前作用域使用的话 可以写在里面 但是还是不建议在函数里写类
  case class User(id:Int,name:String,age:Int)



}
