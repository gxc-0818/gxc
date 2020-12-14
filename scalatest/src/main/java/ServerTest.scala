import java.io.{ObjectInputStream, ObjectOutputStream}
import java.net.ServerSocket

object ServerTest {
  //80默认的web访问端口 http://www/jd.com:80
  //https://www.baidu.com:443 百度不一样 他是https 安全协议 端口是443
  //两个进程之前相互通信 谁传数据谁就是客户端 谁收数据谁就是服务端

  //序列化：把内存中的数据存储在磁盘中 也称作持久化 在网络中想要传输对象 需要对象实现序列化接口
  def main(args: Array[String]): Unit = {
    val server = new ServerSocket(9999)
    println("服务器启动")
    //阻塞在这个位置等待连接
    val client = server.accept()

    val stream = client.getInputStream
    //这里不能发送数字 其实发送的是ascii码 数字都当成字符串了 超过范围就不对了
    val i = stream.read()
    println(i)

    //传对象
    val objectStream = new ObjectInputStream(stream)
    val value = objectStream .readObject()
    println(value)
    stream.close()
    client.close()
    server.close()
  }

}
