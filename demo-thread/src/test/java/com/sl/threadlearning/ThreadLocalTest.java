package com.sl.threadlearning;

import org.junit.Test;

import java.util.Hashtable;

/**
 *  可以参考博客https://www.cnblogs.com/chengxiao/p/6152824.html#t1
 *
 *  从ThreadLocal中获得的对象是线程私有的，也就是线程安全的
 *
 *  ThreadLocal提高一个线程的局部变量，访问某个线程拥有自己局部变量。
 *  当使用ThreadLocal维护变量时，ThreadLocal为每个使用该变量的线程提供独立的变量副本，所以每一个线程都可以独立地改变自己的副本，而不会影响其它线程所对应的副本。
 *
 *  ThreadLocal的接口方法
 *  •	void set(Object value)设置当前线程的线程局部变量的值。
 *  •	public Object get()该方法返回当前线程所对应的线程局部变量。
 *  •	public void remove()将当前线程局部变量的值删除，目的是为了减少内存的占用，该方法是JDK 5.0新增的方法。需要指出的是，当线程结束后，对应该线程的局部变量将自动被垃圾回收，
 *      所以显式调用该方法清除线程的局部变量并不是必须的操作，但它可以加快内存回收的速度。
 *
 * •	protected Object initialValue()返回该线程局部变量的初始值，该方法是一个protected的方法，显然是为了让子类覆盖而设计的。
 *      这个方法是一个延迟调用方法，在线程第1次调用get()或set(Object)时才执行，并且仅执行1次。ThreadLocal中的缺省实现直接返回一个null。
 */
public class ThreadLocalTest {

    @Test
    public void testThreadLocal() throws InterruptedException {
        ThreadLocalRes res = new ThreadLocalRes();
        ThreadLocalDemo threadLocaDemo1 = new ThreadLocalDemo(res);
        ThreadLocalDemo threadLocaDemo2 = new ThreadLocalDemo(res);
        ThreadLocalDemo threadLocaDemo3 = new ThreadLocalDemo(res);
        threadLocaDemo1.start();
        threadLocaDemo2.start();
        threadLocaDemo3.start();

        Thread.sleep(1000);
    }
}
