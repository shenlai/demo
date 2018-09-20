package com.sl.threadlearning.safe;

import org.junit.Test;

public class VolatileNoAtomicTest {


    /**
     * 看testVolatile2
     */
    @Test
    public  void testVolatile() throws InterruptedException {

        VolatileNoAtomic volatileNoAtomic = new VolatileNoAtomic();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(volatileNoAtomic);
            thread.start();
        }
        Thread.sleep(1000);
    }


    /**
     *  看这个例子，主线程中修改变量值，子线程并没与及时更新该值
     */
    @Test
    public  void testVolatile2() throws InterruptedException {

        VoliatileThread thread = new VoliatileThread();
        new Thread(thread).start();
        Thread.sleep(100);   //这一步很重要  注释前后打印结果不同, 子线程已经运行，读取了isRunning=true,所以不知道isRunning值被后面的程序修改了
        thread.setRunning(false);
        System.out.println("已经赋值为false");
        // new Thread(thread).start();  //线程2  线程2正常终止
        Thread.sleep(1000);
    }

    /**
     *
     * 运行结果：
     * 线程Thread-0进入run了
     * 线程Thread-1进入run了
     * 线程Thread-1执行count++          //说明trhead1中修改了count  thread0中还是能读取到的，为什么呢？Volatile有什么用呢？
     * 线程Thread-0线程被停止了,count值为：1
     * 线程Thread-1线程被停止了,count值为：1
     */
    @Test
    public  void testVolatile4() throws InterruptedException {

        VoliatileThread2 thread = new VoliatileThread2();
        new Thread(thread).start();  //线程1
        Thread.sleep(110);  //确保线程1没有读取到isChangeCount
        thread.setChangeCount(true);
        new Thread(thread).start();  //线程2

        Thread.sleep(1000);


    }
}
