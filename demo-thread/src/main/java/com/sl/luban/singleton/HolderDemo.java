package com.sl.luban.singleton;


/**
 * 对比： EnumSingletonDemo
 */
public class HolderDemo {
    private HolderDemo(){

    }
    private static class Holder{
        private static HolderDemo instance=new HolderDemo();
    }
    //懒加载
    //synchronized
    //Holder.instance触发 内部静态类初始化<init>
    public static HolderDemo getInstance(){
        return Holder.instance;
    }

    //广泛的一种单例模式
}
