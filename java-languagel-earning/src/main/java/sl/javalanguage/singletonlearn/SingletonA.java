package sl.javalanguage.singletonlearn;


/**
* 思路：饿汉模式--推荐使用
*  1.静态初始化
*  2.构造函数私有化
*  （密封类？）
*
*  初试化静态的instance创建一次。如果我们在Singleton类里面写一个静态的方法不需要创建实例，
*  它仍然会早早的创建一次实例。而降低内存的使用率。
* */
public class SingletonA {

    //1.静态初始化
    private static SingletonA instance = new SingletonA();

    //构造函数私有化

    private SingletonA(){}

    //公开入口
    public  static  SingletonA getInstance(){
        return instance;
    }

}
