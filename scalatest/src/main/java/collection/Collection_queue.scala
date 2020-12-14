package collection

import scala.collection.mutable

object Collection_queue {
  def main(args: Array[String]): Unit = {
    //集合 - 队列
    val queue = mutable.Queue[String]()
    //添加数据
    queue.enqueue("aaa")
    queue.enqueue("bbb")

    queue.dequeue()

    println(queue)

  }

}
