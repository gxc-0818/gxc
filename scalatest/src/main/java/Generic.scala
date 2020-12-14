object Generic {
  def main(args: Array[String]): Unit = {
    //scala中泛型不可变 m1 m2 编译不过
    val m :Message[User]=new Message[User]
    //val m1:Message[User]=new Message[Parent]
    //val m2:Message[User]=new Message[Child]

    //将类型和泛型当成一个整体来用
    //当成整体后 如果泛型是子类型也可以代替父类型
    //这就做 泛型的协变 泛型前增加 +
    val ma:MessageAdd[User]=new MessageAdd[User]
    //val ma1:MessageAdd[User]=new MessageAdd[Parent]//会报错
    val ma2:MessageAdd[User]=new MessageAdd[Child]

    //泛型的逆变 泛型前加- 父类可以代替子类对象
    val sa:MessageSub[User]=new MessageSub[User]
    val sa1:MessageSub[User]=new MessageSub[Parent]
    //val sa2:MessageSub[User]=new MessageSub[Child]//会报错

    //Todo 泛型的上限(往下找)和下限(往上找)


    val list = List(new User,new User)
    //返回结果是User类型(因为list里都是User类型) 前面用parent类型来接 子类对象给一个父类引用没有问题
    val parent: Parent = list.reduce[Parent]((x,y)=>x)

    //返回结果是User类型 前面用Child类型来接 父类对象给一个子类引用 会报错
    //val child: Child = list.reduce[Child]((x, y) => x) //运行会报错



  }
}

class Consumer[T]{
  //泛型的上限(往下找) 消费者可能需要更多的功能
  //? extends T
  def consume(): Message[_<:T] ={
     null
  }
}

class Producer[T]{
  //泛型的下限 往上找 生产者
  //? super T
  def product(): Message[_>:T] ={
    null
  }
}


class Message[T]{

}
//泛型的协变 子类可以代替父类
class MessageAdd[+T]{

}

//泛型的逆变 父类可以代替子类
class MessageSub[-T]{

}



class Parent{

}
class User extends Parent {

}

class Child extends User {

}


