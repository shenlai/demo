package com.generics;

/**
 * 1、有界类型参数
 * <p>
 * 有时可能希望限制可在参数化类型化中用作类型参数的类型，例如对数字进行操作的方法可能只想接收Number或其子类的实例，这时需要用到有界类型参数
 * 要声明有界类型参数，先要列出类型参数的名称，然后是extends 关键字，后面跟着它的上限，比如下面例子中的Number:
 *
 * 2、多个边界
 * 前面的示例说明了使用带有单个边界的类型参数 ，但是类型参数其实是可以有多个边界的：
 * <T extends B1 & B2 &B3>
 * 注意： 在由界类型参数中的extends,即可以表示"extends"也可以表示"implements"
 *
 * @param <T>
 */
public class Box<T extends Number> {
    private T t;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
