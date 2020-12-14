import java.io.{File, PrintWriter}

object IoTest {
  def main(args: Array[String]): Unit = {

    //控制台输入一个int 并打印
    var int = scala.io.StdIn.readInt();
    println(int)

    //文件输出
    val source = scala.io.Source.fromFile("input/test.txt") //注意 idea读取文件是以根路径为基准
    val strings = source.getLines()
    val iterator = strings.toIterator
    while(iterator.hasNext){
      println(iterator.next())
    }
    source.close();

    //文件写入
    val writer = new PrintWriter(new File("output/out2.txt"))
    writer.println("aaa")
    writer.println("bbb")
    writer.printf("jcy")
    writer.flush()
    writer.close()




  }

}
