import org.omg.CORBA.Any;

import java.lang.reflect.Field;

public class Test {


    /**
     * 注意 scala是面向对象的语言 所以 scala中不存在静态方法（静态方法不是面向对象）
     *
     * sleep 和wait 的区别
     * 先看字体 sleep为什么是斜体(静态方法 所以和对象没有关系 也就是t1不可能休眠 真正休眠的是调用t1的主线程 main)
     * 拿不到对象锁 更谈不上释放对象锁
     * wait是成员方法 所以真正等待的是t2 拿得到对象锁自然也能释放对象锁
     *
     *
     * classpath:类路径
     *
     * 静态属性的初始化是在静态代码块中进行初始化的 所以你在外部调用一个类的静态属性 会先执行静态代码快的内容
     * 但是如果静态属性用final修饰 就不会调用静态代码块了 因为调用的时候 有值 无需去静态代码块中去初始化了
     *
     *
     * 不可变字符串 String  引用不可变 但是内容是可变的(用反射)
     * 可变字符串 StringBuilder StringBuffer
     *
     * java中方法参数的传递都是值传递
     *
     * java中每一行都应该是一个表达式 一个操作
     *
     * StackOverFlowError
     * 栈溢出 不等于栈内存溢出 执行程序的时候进行压栈 压得的是栈祯 至于压栈 没往出弹 栈有一个深度的概念 超了就是栈溢出了
     *
     * 栈内存溢出 每个线程会分配一个栈 线程过多 内存不够分配了 这就是一台服务器不能有太多线程的原因
     * 堆内存溢出 堆内已经没有足够的内存空间分配给对象了
     *
     * do while 一定会执行一次 即使条件不成立
     *
     * 所谓的线程安全其实就是多线程在并发执行时 对共享内存中的共享对象的属性进行修改导致的数据冲突问题 方法没事 方法需要压栈 每个线程一个栈
     *
     * 重写equals 就应该重写hashcode 为什么？
     * 如果你不将自定义的类定义为 HashMap的key值的话，那么我们重写了 equals方法而没有重写 hashCode方法，编译器不会报任何错，在运行时也不会抛任何异常。
     * 如果你想将自定义的类定义为 HashMap的key值得话，那么如果重写了 equals 方法那么就必须也重写 hashCode方法。
     * 数组默认长度10 扩容是1.5倍扩容 10 -》15 -》 22 -》 33 -》
     *
     * 调用一个为null的成员方法或属性 会发生空指针异常 记住是成员方法 静态的东西 不受对象影响 管你是不是null
     * 所谓的拆箱其实就是底层调用Integer.intvalue 装箱Integer.valueof
     *
     * 串行 A执行完 B执行 B执行完 C执行 每个线程走完了之后才开始下一个线程 t1.join(t2)
     *
     * 并发 交叉执行 A抢到了CPU的执行权 执行一部分 休眠 换B 执行 一会A 一会B 一会C 单核的时候
     *
     * 并行 多核情况下 线程同时执行 多个线程真正的同时执行
     * 超频技术 一个核模拟两个核心 A 抢到一个核 BC 抢到一个核 AB就是并行 BC就是并发
     *
     * 泛型(类型参数)的使用渊源
     * 泛型的目是为了增加类型的约束提高程序的健壮性
     * 语法糖 没什么用 加上会更方便 为了编译器服务
     * 泛型和类型的关系 没有直接关系 但是类型表示对外部类型的数据约束 泛型主要是对内部数据类型的约束
     * 泛型一般也分为构建和使用
     * 泛型的擦除
     * 泛型其实就是一种语法糖 可以对操作的数据进行类型约束 为了让编译器更好的执行类型验证
     * 所以jvm执行时不会用上泛型(ArrayList<String> 在字节码中根本是没有String的)
     * 泛型的擦除是在执行的时候擦除 就是编译通过以后在进行擦除
     * 泛型由于是设定对象内部数据的类型 所以不能考虑父子关系 定义了个一个<类型> 再传个子类或者父类都是不行的
     * 泛型和类型不是一个整体 在java中
     * 泛型不可变 定义的时候是什么类型 用的时候也得用什么类型 List<User> list = new ArrayList<Parent>()这就是错的
     * 泛型进行变化
     * <? extends T> 泛型的上限 功能不缺失 一般在消费数据的场合
     * <? super T> 泛型的下限 限定数据的功能 一般在生产数据的场合
     *
     * 克隆浅复制 List1 里面有一个user clone list1 不会复制里面user的内存 复制的是指向user的引用
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     * */
    public static void main(String[] args) throws Exception {

        //Thread t1 = new Thread();
        //Thread t2 = new Thread();
        //t1.sleep(1000);// == Thread.sleep(1000);
        //t2.wait(1000); //wait方法也是可以传时间的

        String s = " a a ";
        //s.trim();//注意去除的是半角空格
        //System.out.println(s);

        //java中的字符串没有提供改变自身内容的方法 所有对内容的操作都是产生新的字符串
        Field value = s.getClass().getDeclaredField("value");
        value.setAccessible(true);//强力破解一下 不然私有的访问不了
        char[] value1 = (char[])value.get(s);//所谓不可变 因为里面是一个final修饰的char数组 指向的内存空间不可改
        //System.out.println(value1.length);
        value1[2] = '6';
        //System.out.println(s);



        //0111 1111  加1以后下面 扩容到int四个字节 32位
        //0000 0000 0000 0000 0000 0000 1000 0000  转为byte 留八位
        //                              1000 0000

        //1000 0000 => 1+ 0000000 第一个是符号位 这后面全是零 就可以理解为负数中的最小值 也就是-128 这个加1 就是-127 即 1000 0001
        //1111 1111 => 1+ 1111111 第一个是符号位 后面全是1 可以理解为负数中的最大值 也就是-1


        byte b= 127;
        byte b1= (byte)(b+1);
        //System.out.println(b1);//==> -128

        Integer i1 = 100;
        Integer i2 = 100;
        //System.out.println(i1==i2);//true
        Integer i3 = 200;
        Integer i4 = 200;
        //System.out.println(i3 == i4);//false

        //原因自动装箱 调得是Integer的valueof方法 -128-127 是从缓存中拿出来的 超出范围就是new了 地址自然不一样了 记住那个上限127是可能改变的 有个参数可以设置 看Integer源码就知道了

//      int i= 0;
//      int j= i++;先赋值 在进行自增
//      System.out.println("i = "+i+" j="+j); //i= 1 j=0

        int i =0;
        i= i++;//临时变量 1.val=i++ 先赋值 在自增 所以val是0  2.i=val  这里i自增了变成1 但是因为val赋值又变回了0
        //System.out.println("i= "+i);//i= 0

        //总结 等号:赋值 将等号有右边的计算结果给等号左边




        //点是什么意思 不是调用的意思 表示从属关系
        //User user = new User();
        // 1)user 对象调用name属性 赋值为aaa 说法错误
        // 2)给user对象的name属性赋值为aaa
        // user.name = "aaa";

        //访问权限
        //方法提供者 ：java.lang.Object
        //方法访问者 : Test 的main
        //提供者 和访问者啥关系 没关系 不是父子 为什么 张三的父亲不是李四的父亲 参考那个图 每个类都有自己的父类对象
        AAA a = new AAA();
        //a.clone(); //访问不了
        //AAA里重写了clone方法 这里就可以用了 因为方法的提供者变成成了AAA Test和AAA同包 所以可以访问
        //a.clone();


        C c = new C();
        //System.out.println(c.sum());//20
        D d = new D();
        //System.out.println(d.sum());//40

        C cc = new D();
        //System.out.println(cc.sum());//40

        //现在把D中的sum注释掉
        C ccc = new D();
        System.out.println(ccc.sum());//20

        //方法的重写 子类重写父类的方法
        //动态绑定机制：当调用一个对象的成员方法 JVM会将这个方法和对象的实际内存进行绑定 然后调用
        //           属性没有动态绑定机制 D里面找不到sum方法的时候 就用的手机是父类对象的sum方法 sum中用的i是属性 没有动态绑定 用的也是父类的i 所以结果是20


       // new ExceptionTest().test();




    }

    public static int test1(){
        int i= 0;
        try {
            return i++;
        }finally {
            return i++;
        }
    }

    public boolean test(String s){
        //return s !=null && !s.isEmpty();//短路与 前面不满足 就不会执行后面了 所以选这个
        return s!= null & !s.isEmpty();//逻辑与 前后都会执行 所以可能有空指针异常

    }


}

class C{
    public  int i =10;
    public int sum(){
        return i+10;
    }
}
class D extends C{
    public  int i =20;
//    public int sum(){
//        return i+20;
//    }
}


class AAA{

    public void test() throws CloneNotSupportedException {
        clone();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
