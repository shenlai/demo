package com.xiangxue.ch02;

/**
 * 新生代大小配置参数的优先级：
 * 高：-XX:NewSize/MaxNewSize
 * 中间 -Xmn （NewSize= MaxNewSize）
 * 低：-XX:NewRatio  表示比例，例如=2，表示 新生代：老年代 = 1:2
 *
 * -XX:SurvivorRatio 表示Eden和Survivor的比值，
 * 缺省为8 表示 Eden:FromSurvivor:ToSurvivor= 8:1:1
 */
public class NewSize {

	/**
	 * -Xms20M -Xmx20M -XX:+PrintGCDetails -Xmn2M -XX:SurvivorRatio=2  没有垃圾回收、大对象直接进入老年代
	 *
	 *  PSYoungGen = 1536K 为什么是1536K 因为from 和 to 有一个会空出来！！
	 *  PSYoungGen      total 1536K, used 1528K [0x00000000ffe00000, 0x0000000100000000, 0x0000000100000000)
	 *   eden space 1024K, 100% used [0x00000000ffe00000,0x00000000fff00000,0x00000000fff00000)
	 *   from space 512K, 98% used [0x00000000fff00000,0x00000000fff7e030,0x00000000fff80000)
	 *   to   space 512K, 0% used [0x00000000fff80000,0x00000000fff80000,0x0000000100000000)
	 *
	 * -Xms20M -Xmx20M -XX:+PrintGCDetails -Xmn8M -XX:SurvivorRatio=2 发生垃圾回收、部分对象进去老年代（发生晋升现象）
	 *
	 * [GC (Allocation Failure) [PSYoungGen: 3083K->1848K(6144K)] 3083K->1848K(18432K), 0.0014507 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
	 * [GC (Allocation Failure) [PSYoungGen: 4996K->1896K(6144K)] 4996K->4976K(18432K), 0.0016910 secs] [Times: user=0.00 sys=	0.00, real=0.00 secs]
	 * [GC (Allocation Failure) [PSYoungGen: 5119K->1816K(6144K)] 8199K->7968K(18432K), 0.0017620 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
	 * Heap
	 *  PSYoungGen      total 6144K, used 5001K [0x00000000ff800000, 0x0000000100000000, 0x0000000100000000)
	 *   eden space 4096K, 77% used [0x00000000ff800000,0x00000000ffb1c680,0x00000000ffc00000)
	 *   from space 2048K, 88% used [0x00000000ffc00000,0x00000000ffdc6040,0x00000000ffe00000)
	 *   to   space 2048K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x0000000100000000)
	 *  ParOldGen       total 12288K, used 6152K [0x00000000fec00000, 0x00000000ff800000, 0x00000000ff800000)
	 *   object space 12288K, 50% used [0x00000000fec00000,0x00000000ff202060,0x00000000ff800000)
	 *  Metaspace       used 3440K, capacity 4496K, committed 4864K, reserved 1056768K
	 *   class space    used 374K, capacity 388K, committed 512K, reserved 1048576K
	 *
	 *   -Xms20M -Xmx20M -XX:+PrintGCDetails -Xmn15m -XX:SurvivorRatio=8  全部分配在新生代
	 *
	 *   -Xms20M -Xmx20M -XX:+PrintGCDetails -XX:NewRatio=2  发生 FULL GC
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		int cap = 1*1024*1024;//1M
		byte[] b1 = new byte[cap];
		byte[] b2 = new byte[cap];
		byte[] b3 = new byte[cap];
		byte[] b4 = new byte[cap];
		byte[] b5 = new byte[cap];
		byte[] b6 = new byte[cap];
		byte[] b7 = new byte[cap];
		byte[] b8 = new byte[cap];
		byte[] b9 = new byte[cap];
		byte[] b0 = new byte[cap];
//		for(int i=0;i<10;i++) {
//			b = new byte[1*1024*1024];
//		}
	}
}
