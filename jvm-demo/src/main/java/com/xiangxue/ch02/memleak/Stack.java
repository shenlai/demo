package com.xiangxue.ch02.memleak;

public class Stack {
	
	public  Object[] elements;
	private int size = 0;//指示器，指示当前栈顶的位置

    private static final int Cap = 16;

    public Stack() {
    	elements = new Object[Cap];
    }

    //入栈
    public void push(Object e){
    	elements[size] = e;
    	size++;
    }

    //出栈
    public Object pop(){
    	size = size-1;
    	Object o = elements[size];
    	elements[size] = null;// 不加这一行则有可能发生内存泄漏， 原因：数组element[x] 一直持有对象的引用，除非element[x]被最新的入栈元素覆盖
        return o;
    }
}
