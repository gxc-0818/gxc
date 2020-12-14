package test

object ScalaTest {
  def main(args: Array[String]): Unit = {

    //Todo 九层妖塔
//    for (i :Int <- 1 to 9){
//      val j=i-1;
//      val s=9-i;
//      print(" "*s)
//      print("*"*j)
//      print("*")
//      print("*"*j)
//      print(" "*s)
//      println()
//    }

    val s = "hello world scala spark"
    def filterRule(string:String): String ={
      val strings: Array[String] = string.split(" ")
      var s=""
      for (string <- strings if(string.startsWith("s"))){
        if(s == ""){
          s = string;
        }else{
          s = s +","+ string
        }
      }
      s
    }

    println(filter(s, filterRule _))

  }



  def filter(string:String,f:(String)=>String): String ={
     f(string)
  }



}
