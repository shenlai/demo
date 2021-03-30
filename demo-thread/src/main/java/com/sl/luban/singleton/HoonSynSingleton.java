package com.sl.luban.singleton;

public class HoonSynSingleton {

    //1:构造函数私有化
    private HoonSynSingleton() {
    }

    //2:静态变量
    private static HoonSynSingleton instance = null;


    //静态方法synchronized 锁class对象
    public synchronized static HoonSynSingleton getInstance() {
        if (null == instance)
            instance = new HoonSynSingleton();
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                System.out.println(HoonSynSingleton.getInstance());
            }).start();
        }
    }
}
