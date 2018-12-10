package sl.javalanguage.singletonlearn;

/**
 * 懒汉模式  --不推荐
 * 多线程情况可以使用
 * 缺点：每次通过getInstance方法得到singleton实例的时候都有一个试图去获取同步锁的过程。
 * 而众所周知，加锁是很耗时的。能避免则避免。
 */
public class SingletonB {
    //暂不初始化
    private static SingletonB instance = null;

    //构造函数私有化
    private SingletonB(){}

    //synchronized实现同步（不加synchronized只适合单线程）
    public  static  synchronized SingletonB getInstance(){
        if(instance==null){
            instance = new SingletonB();
        }
        return instance;
    }

}
