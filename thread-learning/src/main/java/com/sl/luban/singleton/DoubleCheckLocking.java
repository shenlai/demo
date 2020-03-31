package com.sl.luban.singleton;

import java.net.Socket;
import java.sql.Connection;

//Double-check-locking
public class DoubleCheckLocking {
    Connection conn;
    Socket socket;

    //private static DoubleCheckLocking instance = null;
    //避免指令重排
    private volatile static DoubleCheckLocking instance = null;

    private DoubleCheckLocking() {
    }

    /**
     * double check
     *
     * @return
     */
    public static DoubleCheckLocking getInstance() {
        if (null == instance)
            synchronized (DoubleCheckLocking.class) {
                if (null == instance) {
                    instance = new DoubleCheckLocking();
                }
            }
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                System.out.println(DoubleCheckLocking.getInstance());
            }).start();
        }
    }
}

