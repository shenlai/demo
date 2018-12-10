package sl.javalanguage.singletonlearn;

/**
 * enum有且仅有private的构造器，防止外部的额外构造，这恰好和单例模式吻合
 */
public enum SingletonE {
    INSTANCE;

    /*单例中的测试方法*/
    public  void  fun(){

    }

    //调用
    //SingletonE.INSTANCE.fun();
}
