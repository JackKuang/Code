package com.hurenjieee.code.nio;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author Jack
 * @date 2019/11/10 16:19
 */
public class NIOTest {

    public static void main(String[] args) {
        client();
    }

    /*
     * 使用nio完成网络通信需要三个核心对象
     *
     * 1. 通道Channel
     *  java.nio.channels.Channel接口
     *   SocketChannel
     *   ServerSocketChannel
     *   DatagramChannel
     *
     *   管道相关:
     *   Pipe.SinkChannel
     *   Pipe.SourceChannel
     *
     * 2. 缓冲buffer: 负责存储数据
     *
     * 3. Selector: 是SelectableChannel的多路复用器, 主要是用于监控SelectableChannel的IO状况
     * */

//    @Test
    public static void client() {
        try {
            // 1. 获取通道，默认就是阻塞通道
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));
            // 1.1 更新为非阻塞通道
            socketChannel.configureBlocking(false);
            // 2. 创建缓存区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            // 3. 发送美国数据到服务端-->直接讲数据写入缓存区:
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String str = scanner.next();
                buffer.put((new Date().toString() + str).getBytes());
                // 谢欢切换为读模式
                buffer.flip();
                // 把缓冲区里的数据写入通道中
                socketChannel.write(buffer);
                buffer.clear();
            }
            socketChannel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void server() {
        try {
            //1. 获取通道 ,默认就是阻塞
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //1.2 将阻塞的套接字变为非阻塞
            serverSocketChannel.configureBlocking(false);

            //2. 绑定端口号
            serverSocketChannel.bind(new InetSocketAddress(8888));

            //3. 创建选择器
            Selector selector = Selector.open();
            // 4. 讲通道注册到选择器上，
            // 此时，选择器开始监听这个通道的接受数据，如果有接受，准本接受数据，进行下一步操作
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            // 5.  通过轮询的方式获取选择器上准备就绪的事件
            while (selector.select() > 0) {
                // 6. 获取当前选择器中所有注册的选择键（已经就绪的监听事件）
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                // 7.迭代获取已经准备就绪的事件
                while (iterator.hasNext()) {
                    ///8.获取已经准备就绪的事件
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isAcceptable()) {
                        // accpeth获取SockerChannel
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        // 非阻塞
                        socketChannel.configureBlocking(false);
                        //11. 将该通道注册到选择器上, 向塘选择器能够监听这个通道,同样也需要完成注册
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } else if (selectionKey.isReadable()) {
                        //12.如果读状态已经准备就绪, 就开始读取数据
                        //获取当前选择器上读状态准备就绪的通道
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        //读取客户端发送的数据, 需要先创建缓冲区
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        //13. 读取缓冲区的数据
                        int length = 0;
                        while ((length = socketChannel.read(buffer)) > 0) {
                            buffer.flip();
                            System.out.println(new String(buffer.array(), 0, length));
                            //清空缓冲区
                            buffer.clear();
                        }
                    }
                    //当selectKey使用完毕之后需要移除, 否则会一直优先
                    iterator.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
