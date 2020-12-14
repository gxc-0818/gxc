package function

object k {
  def main(args: Array[String]): Unit = {
    for(i <- 1 to 3 ; j <- 1 to 3; if i != j ){
      println(s"i=${i} j=${j}")
      print((10 * i + j) + " ")
    }
  }

}
