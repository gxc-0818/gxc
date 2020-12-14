下划线的作用
1 将函数作为整体对象来使用
2 代替匿名函数中的参数
3 代替* 导入时使用 代表导入一个包中所有的类(这么说不够准确 不是一口气都导入进来 用到什么就导入什么)
4 属性默认初始化
5 导入类的时候 可以屏蔽某个类
6 模式匹配时 用于匹配除了前置case以外的所有值 类似于default
7 泛型中如果使用下划线 表示任意类型







框架里
注解 反射
Bean规范
属性私有化

setXXX
getXXX

class User{
private String name;
public String getName(){
}

xxxx(new User(),"name") 给属性名
user.get"Name"

所以就用反射了
obj.getClass.getMethod(get"Name")




}
