package sl.javalanguage.singletonlearn;

/**
 * 双重验证的线程安全实现
 *
 * 比懒汉模式好
 * 只有当instance为null时，需要获取同步锁，创建一次实例。当实例被创建，则无需试图加锁
 */
public class SingletonC {

    private static SingletonC instance = null;
    private static Object lockObj = new Object();
    private SingletonC(){}

    public  static  SingletonC getInstance(){
        if(instance==null){
            //或者：synchronized (Singleton.class)
            synchronized (lockObj){
                if(instance==null){
                    instance = new SingletonC();
                }
            }
        }
        return instance;
    }

}
