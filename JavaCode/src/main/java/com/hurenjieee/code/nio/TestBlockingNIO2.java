package com.hurenjieee.code.nio;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author Jack
 * @date 2019/11/10 20:33
 */
public class TestBlockingNIO2 {


    @Test
    public void client() {
        try {
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            FileChannel inChannel = FileChannel.open(Paths.get("D:\\jenkins.war"),StandardOpenOption.READ);
            while (inChannel.read(buffer) != -1) {
                buffer.flip();
                socketChannel.write(buffer);
                buffer.clear();
            }

            socketChannel.shutdownOutput();
            int length = 0;
            while ((length = socketChannel.read(buffer))  != -1 ) {
                buffer.flip();
                System.out.println(new String(buffer.array(), 0, length));
                //清空缓冲区
                buffer.clear();
            }
            // 告知服务端关闭输出
            socketChannel.shutdownOutput();
            inChannel.close();
            socketChannel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void server() {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(8888));

            FileChannel outChannel = FileChannel.open(Paths.get("D:\\jenkins.war.new2"),StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            SocketChannel socketChannel = serverSocketChannel.accept();
            while (socketChannel.read(buffer) != -1) {
                buffer.flip();
                outChannel.write(buffer);
                buffer.clear();
            }

            buffer.put("服务端接受数据成功".getBytes());
            buffer.flip();
            socketChannel.write(buffer);

            outChannel.close();
            socketChannel.close();
            serverSocketChannel.close();
            buffer.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
