package com.sl.luban.singleton;

/**
 * 饿汉模式
 */
public class HungerySingleton {

    private byte[] data = new byte[1024];

    //1:构造函数私有化
    private HungerySingleton() {
    }

    //加载的时候就产生的实例对象,ClassLoader
    //2:静态初始化
    private static HungerySingleton instance = new HungerySingleton();

    //返回实例对象
    public static HungerySingleton getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                System.out.println(HungerySingleton.getInstance());
            }).start();
        }
    }
}
