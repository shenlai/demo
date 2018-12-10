package sl.javalanguage.singletonlearn;

/**
 *
 * 静态内部类(推荐） --对象的加载也是延时加载
 *
 * 加载一个类时，其内部类不会同时被加载。
 * 一个类被加载，当且仅当其某个静态成员（静态域、构造器、静态方法等）被调用时发生
 */
public class SingletonD {

    //构造函数私有化
    private SingletonD(){}

    //使用静态内部类初始化实例
    private static  class SingletonIns{
        //静态常量，初始化后不变
         private final static  SingletonD instance = new SingletonD();
    }

    public  static  SingletonD getInstance(){
        return SingletonIns.instance; //外部类和内部类都可以互相访问对方的任何成员，包括私有成员
    }
}
