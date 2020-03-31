package com.sl.luban;

import java.util.concurrent.TimeUnit;

public class VolatileReaderAndUpdater {

    final static int MAX=50;

    static volatile int init_value=0;
    //static int init_value=0;

    public static void main(String[] args) {
        new Thread(()->{
                int localValue=init_value;
                while(localValue<MAX){
                    //if(localValue!=init_value){
                    if(localValue<MAX){

                        System.out.println("Reader,localValue="+localValue+ ", init_value="+init_value);
                        localValue=init_value;
                    }
                }
        },"Reader").start();

        new Thread(()->{
            int localValue=init_value;
            while(localValue<MAX){
                System.out.println("updater:"+(++localValue));
                init_value=localValue;
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"Updater").start();
    }
}
