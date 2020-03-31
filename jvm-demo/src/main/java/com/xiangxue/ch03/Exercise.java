package com.xiangxue.ch03;

public class Exercise {

	/**
	 * 作者：RednaxelaFX
	 * 链接：https://www.zhihu.com/question/52749416/answer/132831920
	 * 来源：知乎
	 *
	 * 题主的源码中，new Test()这个表达式的作用是：创建并默认初始化一个Test类型的对象调用Test类的signature为 <init>()V
	 * 的构造器表达式的值为一个指向这个新建对家的引用。对应到字节码，我们可以看到：
	 * new Test 对应上面的(1)invokespecial Test.<init>()V 对应上面的(2)然而(3)是怎么来的？再看实际的字节码：
	 * 								  // operand stack:
	 * new Test                       // ..., ref
	 * dup                            // ..., ref, ref
	 * invokespecial Test.<init>()V   // ..., ref
	 * astore_1                       // ...
	 *
	 * 可以看到，new字节码指令的作用是创建指定类型的对象实例、对其进行默认初始化，并且将指向该实例的一个引用压入操作数栈顶；
	 * 然后因为invokespecial会消耗掉操作数栈顶的引用作为传给构造器的“this”参数，所以如果我们希望在invokespecial调用后在操作数栈顶
	 * 还维持有一个指向新建对象的引用，就得在invokespecial之前先“复制”一份引用——这就是这个dup的来源。在上面的基础上，
	 * 我们把这个引用保存到局部变量去，就有：
	 * 								  // operand stack:
	 *                                // ..., ref
	 * astore_1                       // ...
	 * astore就会把操作数栈顶的那个引用消耗掉，保存到指定的局部变量去。
	 *
	 * @param e1
	 * @param e2
	 */
	static void print(Exercise e1,Exercise e2) {
		System.out.println(e1==(e1=e2));
	}
	
	public static void main(String[] args) {
		Exercise e1 = new Exercise();
		Exercise e2 = new Exercise();
		print(e1,e2);
	}

}
