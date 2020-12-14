package collection

import scala.collection.mutable

object Collection_method2 {

  //Todo 集合常用方法
  def main(args: Array[String]): Unit = {
    val list = List(1,2,3,4,5)
    //折叠 函数柯里化
    // z-zero 零 初始值
    //fold方法存在两个参数列表 第一个参数列表中的参数表示计算的初始值 第二个参数列表表示两两计算的逻辑

    println(list.fold(6)(_ + _))//21
    //scan方法会保留所有计算结果
    println(list.scan(6)(_+_))//List(6, 7, 9, 12, 16, 21)
    println(list.fold(6)(_ - _))//-9

    println(list.foldLeft(6)(_ - _))//-9

    // reverse.foldLeft(z)((right, left) => op(left, right))
    println(list.foldRight(6)(_ - _))//-3
    println(list.scanRight(6)(_ - _))//List(-3, 4, -2, 5, -1, 6)

    val map1 = mutable.Map(("a",1),("b",2))
    val map2 = mutable.Map(("a",3),("c",4))

    //foldLeft[B](z: B)(op: (B, A) => B): B
    //第二个参数列表中的map 是第一个参数列表中的map(也就是map2) 后面的kv是map1(调用方)中的键值对 返回一个map2的类型
    val newMap = map1.foldLeft(map2)((map, kv) => {
      val k = kv._1
      val v = kv._2
      val newV = map.getOrElse(k, 0) + v
      map.update(k, newV)
      map
    })
    println(newMap)//Map(b -> 2, a -> 4, c -> 4)

    //Todo 集合功能函数 为了实现某些特殊的功能
    //所谓的功能方法 其实就是scala提供的个API 让开发人员可以提供自己的功能实现方法对集合 具体的实现由scala完成
    //Todo map =》映射转换 A-B
    val list1 = List(1,2,3,4)

    //把原来的数据都乘2
    println(list1.map(_ * 2)) //List(2, 4, 6, 8)
    val list2 =List("hello","scala","hadoop")
    println(list2.map(_.substring(0, 1).toUpperCase))//List(H, S, H)

    //Todo 扁平化 把整体拆分成一个一个个体的操作就是扁平化

    val listList = List(List(1,2),List(1,2,3),List(4,5,6))
    println(listList.flatten)//List(1, 2, 1, 2, 3, 4, 5, 6)

    val listList1 = List(List(List(3,4)),List(List(6,7)))
    //flatten只能拆除外层包装
    println(listList1.flatten)//List(List(3, 4), List(6, 7))
    println(listList1.flatten.flatten)//List(3, 4, 6, 7)

    val list3 =List("hello hive spark ","hello scala spark")
    //scala可以将字符串自动作为字符数组来使用
    println(list3.flatten)//List(h, e, l, l, o,  , h, i, v, e,  , s, p, a, r, k,  , h, e, l, l, o,  , s, c, a, l, a,  , s, p, a, r, k)
    //如果希望按照自己定义的规则进行数据扁平化 可以使用flatMap
    println(list3.flatMap(_.split(" ")))//List(hello, hive, spark, hello, scala, spark)

    //过滤 条件为true的留下
    println(list1.filter(_ % 2 == 0))//List(2, 4)

    //分组 按照指定的分组规则将数据放在一起
    //分组后返回的结果为map类型 K:分组key V:符合分组Key的数据集合
    println(list1.groupBy(_ % 2)) //Map(1 -> List(1, 3), 0 -> List(2, 4))

    val list4 =List("hello","scala","hadoop","spark")
    println(list4.groupBy(_.substring(0, 1)))//Map(h -> List(hello, hadoop), s -> List(scala, spark))

    //排序 将集合的每一条数据按照指定的规则进行排序
    val list5 = List(1,3,2,4)
    //默认的排序为升序
    println(list5.sortBy(num=>num))//List(1, 2, 3, 4)
    //降序
    println(list5.sortBy(num => num)(Ordering.Int.reverse))//List(4, 3, 2, 1)

    val list6= List("1","2","11","3","22")
    println(list6.sortBy(s => s))//List(1, 11, 2, 22, 3)

    //转成数字排
    println(list6.sortBy(s => s.toInt))//List(1, 2, 3, 11, 22)

    val user1 = new User
    user1.salary = 10000
    user1.age = 100

    val user2 = new User
    user2.salary = 15000
    user2.age = 90

    val user3 = new User
    user3.salary = 10000
    user3.age = 70

    val list7 = List(user1,user2,user3)
    //按照薪水排序
    println(list7.sortBy(_.salary))//List(salary:10000 age:100, salary:10000 age:70, salary:15000 age:90)

    //自定义排序 排序的函数 表示你认可的排序规则返回true 如果不认可返回false
    println(list7.sortWith(
      (user1, user2) => {
        if (user1.salary > user2.salary) {
          true
        } else if (user1.salary < user2.salary) {
          false
        } else {
          user1.age < user2.age
        }
      }
    ))//List(salary:15000 age:90, salary:10000 age:70, salary:10000 age:100)

    //元组可以作为整体进行排序 排序规则为 先排第一个 再排第二个 以此类推
    println(list7.sortBy(u => (u.salary, u.age))(Ordering.Tuple2[Int, Int](Ordering.Int.reverse, Ordering.Int)))//List(salary:15000 age:90, salary:10000 age:70, salary:10000 age:100)

  }

  class User{
    var salary:Int = _
    var age:Int = _

    override def toString: String = {
      s"salary:${salary} age:${age}"
    }
  }



}
