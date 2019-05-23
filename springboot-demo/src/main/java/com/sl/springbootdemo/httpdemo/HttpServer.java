package com.sl.springbootdemo.httpdemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * 参考链接：https://blog.csdn.net/qq_27605885/article/details/79561219
 * @Author: sl
 * @Date: 2019/5/16
 * @Description: TODO
 */
public class HttpServer {

    public static void main(String[] args) throws IOException {

        //创建serverSocketChannel 监听8080端口
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(8080));
        //非阻塞
        serverSocketChannel.configureBlocking(false);
        //
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //创建处理器
        while (true) {
            //等待请求，每次等待阻塞3秒，超过3秒后线程继续向下运行，如果传入0或者不传参数将一直阻塞
            if (selector.select(3000) == 0) {
                System.out.println("等待请求超时。。。");
                continue;
            }

            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();

                new Thread(new HttpHandler(key)).run();

                keyIterator.remove();
            }


        }

    }

    private static class HttpHandler implements Runnable {

        private int bufferSize = 1024;

        private String localCharset = "UTF-8";

        private SelectionKey key;

        public HttpHandler(SelectionKey key) {
            this.key = key;
        }


        public void handleAccept() throws IOException {
            SocketChannel clientChannel = ((ServerSocketChannel) key.channel()).accept();
            clientChannel.configureBlocking(false);
            clientChannel.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufferSize));
        }

        public void handleRead() throws IOException {
            //获取channel
            SocketChannel socketChannel = (SocketChannel) key.channel();
            //获取buffer 重置
            ByteBuffer buffer = (ByteBuffer) key.attachment();
            //buffer.clear();
            //接收请求
            if (socketChannel.read(buffer) != -1) {
                buffer.flip();
                String receivedString = Charset.forName(localCharset).newDecoder().decode(buffer).toString();
                System.out.println("收到数据：" + receivedString);
                //控制台打印请求报文
                String[] requestMessage = receivedString.split("\r\n");
                for (String s : requestMessage) {
                    System.out.println(s);
                    //遇到空行说明报文头已经打印完
                    if (s == null || s == "") {
                        break;
                    }
                }
                //控制台信息
                String[] firstLine = requestMessage[0].split(" ");
                System.out.println();
                System.out.println("Method:\t" + firstLine[0]);
                System.out.println("url:\t" + firstLine[1]);
                System.out.println("HTTP Version:\t" + firstLine[2]);
                System.out.println();

                //返回客户端
                StringBuilder sendString = new StringBuilder();
                sendString.append("HTTP/1.1 200 OK\r\n");//响应报文首行，200表示成功
                sendString.append("Content-Type:text/html;charset=" + localCharset + "\r\n");
                sendString.append("\r\n");//报文结束后加一个空行

                sendString.append("<html><head><title>显示报文</title></head><body>");
                sendString.append("接受到的请求报文是：<br/>");
                for (String s : requestMessage) {
                    sendString.append(s + "<br/>");
                }

                sendString.append("</body></html>");
                buffer = ByteBuffer.wrap(sendString.toString().getBytes(localCharset));
                socketChannel.write(buffer);
                socketChannel.close();

            } else {
                //没有读取到内容则关闭
                socketChannel.close();
            }


        }


        public void run() {

            try {
                //接收请求
                if (key.isAcceptable()) {
                    handleAccept();
                }
                //读数据

                if (key.isReadable()) {
                    handleRead();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

}
