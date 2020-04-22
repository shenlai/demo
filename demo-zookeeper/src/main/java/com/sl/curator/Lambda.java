package com.sl.curator;

public class Lambda {


    public static void main(String[] args) {


        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("dfsf");
            }
        };


        //lambda 简化匿名委托（匿名内部类）
        Runnable r2 = () -> {
            System.out.println("dssf");
        };


        new Thread(r2).start();

    }


}
