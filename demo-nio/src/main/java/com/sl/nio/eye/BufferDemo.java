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

    //数据是从通道读入缓冲区，从缓冲区写入到通道中的
    public static void main(String[] args) throws Exception {
       new BufferDemo().testBuffer();



    }


}
