import java.io.{ObjectOutput, ObjectOutputStream}
import java.net.Socket

import bean.Task



object ClientTest {

  def main(args: Array[String]): Unit = {

    val socket = new Socket("localhost",9999)
    //发数字
    val stream = socket.getOutputStream
    stream.write(10)

    //发送对象
    val task = new Task()
    val objectStream = new ObjectOutputStream(stream)
    objectStream.writeObject(task)

    stream.close()
    socket.close()

  }

}
