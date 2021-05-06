package com.sl.luban.singleton;

public enum EnumSingleton {
    INSTANCE;
    public static EnumSingleton getInstance(){
        return INSTANCE;
    }
}

//holder
//枚举
//DoubleCheckLocking
