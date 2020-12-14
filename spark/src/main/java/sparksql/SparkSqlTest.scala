package sparksql

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

object SparkSqlTest {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder().appName("SparkSql").master("local[8]").getOrCreate()

    //在对DataFrame和Dataset进行操作许多操作都需要这个包:import spark.implicits._（在创建好SparkSession对象后尽量直接导入）
    //这个spark不是包名 是SparkSession的对象名 导入对象 把对象的东西都导过来 对象必须是val声明的 之前讲过 去复习

    //不用最好也要写上
    import spark.implicits._

    //RDD读取文件是一行一行读取的 所以sparksql读取json文件时 要求一行数据符合json格式即可

    //todo DataFrame
    val df: DataFrame = spark.read.json("input/test.json")
    //df.show()

    //todo SOL
    df.createOrReplaceTempView("user")
    spark.sql("select avg(age) from user").show()

    //todo DSL 用到了隐式转换 就是上面的 import spark.implicits._
    df.select('age+1).show()
    df.select($"age"+1).show()

    //todo DataSet
    val seq = Seq(1,2,3,4)
    val ds: Dataset[Int] = seq.toDS()
    //ds.show()

    //Todo RDD & DadaFrame & DataSet
    //RDD => DataFrame
    val rdd: RDD[(Int, String, Int)] = spark.sparkContext.makeRDD(List((1,"aaa",20),(2,"bbb",30),(3,"ccc",40)))
    val df1 = rdd.toDF("id","name","age")
    //df1.show()

    //DataFrame=>RDD
    val rdd1: RDD[Row] = df1.rdd

    //DataFrame => DataSet
    val ds1: Dataset[User] = df1.as[User]

    //DataSet => DataFrame
    val df2: DataFrame = ds1.toDF()
    //df2.show()


    //RDD => DataSet
    val ds2: Dataset[User] = rdd.map {
      case (id, name, age) => {
        User(id, name, age)
      }
    }.toDS()
    //ds2.show()

    //DataSet => RDD
    val rdd2: RDD[User] = ds2.rdd


    //DataFrame & DataSet
    //Dataset[Row] = DataFrame  DataFrame其实就是DataSet的一个特例 当DataSet的泛型为Row类型 就可以采用DataFrame代替
    val dataFrame: DataFrame = spark.read.json("input/test.json")
    val dataSet: Dataset[Row] = spark.read.json("input/test.json")

    val rdd3: RDD[Row] = dataFrame.rdd
    val rdd4: RDD[Row] = dataSet.rdd

    //DataFrame的map方法类似于SparkCore中Rdd的转换算子 只不过转换的为执行计划
    //dataFrame.map(r=>r)
    //rdd3.map(r=>r)
    //DataFrame的show方法类似于SparkCore中RDD的行动算子
    dataFrame.show()








    spark.stop()












  }

  case class User(id:Int,name:String,age:Int)
}

