package com.hurenjieee.code.nio;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author Jack
 * @date 2019/11/10 20:33
 */
public class TestBlockingNIO {


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

            FileChannel outChannel = FileChannel.open(Paths.get("D:\\jenkins.war.new"),StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            SocketChannel socketChannel = serverSocketChannel.accept();
            while (socketChannel.read(buffer) != -1) {
                buffer.flip();
                outChannel.write(buffer);
                buffer.clear();
            }
            outChannel.close();
            socketChannel.close();
            serverSocketChannel.close();
            buffer.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
