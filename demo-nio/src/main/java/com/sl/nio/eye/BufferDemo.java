package com.sl.nio.eye;


import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author shenlai
 * @Description https://ifeve.com/buffers/
 * @create 2021/4/29 17:53
 */
public class BufferDemo {


    //数据是从通道读入缓冲区，从缓冲区写入到通道中的
    public static void main(String[] args) throws Exception {
        //new BufferDemo().testBuffer();

        //new BufferDemo().testWriteBuffer();

        new BufferDemo().testReadBuffer();


    }

    /**
     * 调用clear()或compact()方法。clear()方法会清空整个缓冲区。compact()方法只会清除已经读过的数据
     * @throws Exception
     */
    public void testBuffer() throws Exception {
        RandomAccessFile aFile = new RandomAccessFile("D:\\textdata.json", "rw");
        FileChannel inChannel = aFile.getChannel();
        //create buffer with capacity of 48 bytes
        ByteBuffer buf = ByteBuffer.allocate(4);
        //read into buffer.
        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {
            System.out.println("Read " + bytesRead);
            //make buffer ready for read
            buf.flip();
            while (buf.hasRemaining()) {
                // read 1 byte at a time
                System.out.print((char) buf.get());
            }
            //make buffer ready for writing
            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
    }




    /**
     * 写数据到Buffer有两种方式：
     *
     * 1: 从Channel写到Buffer。
     *          通过Buffer的put()方法写到Buffer里。
     *
     * 2:   通过put方法写Buffer的例子：
     *      buf.put(127);
     *
     * @throws Exception
     */
    public void testWriteBuffer() throws Exception {
        RandomAccessFile aFile = new RandomAccessFile("D:\\textdata.json", "rw");
        FileChannel inChannel = aFile.getChannel();
        //create buffer with capacity of 48 bytes
        ByteBuffer buf = ByteBuffer.allocate(4);
        //read into buffer.
        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {
            System.out.println("Read " + bytesRead);
            //make buffer ready for read
            buf.flip();
            while (buf.hasRemaining()) {
                // read 1 byte at a time
                System.out.print((char) buf.get());
            }
            //make buffer ready for writing
            buf.clear();

            //通过put方法写Buffer的例子：
            buf.put(new byte[1]);
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
    }


    /**
     * 从Buffer中读取数据有两种方式：
     * 1: 从Buffer读取数据到Channel。
     * 2: 使用get()方法从Buffer中读取数据。
     * @throws Exception
     */
    public void testReadBuffer() throws Exception {
        RandomAccessFile aFile = new RandomAccessFile("D:\\textdata.json", "rw");
        FileChannel inChannel = aFile.getChannel();
        //create buffer with capacity of 48 bytes
        ByteBuffer buf = ByteBuffer.allocate(4);
        //read into buffer.
        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {
            System.out.println("Read " + bytesRead);
            buf.flip();

            //1:从Buffer读取数据到Channel的例子
            int bytesWritten = inChannel.write(buf);

            while (buf.hasRemaining()) {
                //2: 使用get()方法从Buffer中读取数据。
                System.out.print((char) buf.get());
            }
            //make buffer ready for writing
            buf.clear();

            //通过put方法写Buffer的例子：
            buf.put(new byte[1]);
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
    }
}
