package com.hurenjieee.code.nio;

import org.junit.Test;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * @author Jack
 * @date 2019/10/25 17:27
 */
public class BufferTest {

  /** 缓冲区基本操作 */
  @Test
  public void basicOperate() {
    // 1. 创建缓冲区 allocate
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    System.out.println("1.allocate----------------------");

    // 0 <= mark <= position <= limit <= capacity
    // 位置(position)：下一个要读取或写入的数据的索引。
    // 缓冲区的位置不能为负，并且不能大于其限制。
    System.out.println(buffer.position());
    // 限制(limit)*：第一个不应该读取或写入的数据的索引，即位于limit后的数据不可读写。
    // 缓冲区的限制不能为负，并且不能大于其容量。
    System.out.println(buffer.limit());
    // 容量(capacity):表示Buffer最大数据容量，缓冲区容量不能为负，并且创建后不能更改。
    System.out.println(buffer.capacity());

    // 2. 通过put方法 想缓冲区中存入数据
    System.out.println("2.put----------------------");
    buffer.put("abc".getBytes());
    System.out.println(buffer.position());
    System.out.println(buffer.limit());
    System.out.println(buffer.capacity());

    // 3. 通过get方法, 获取缓冲区的数据, 前提是需要切换缓冲区 的模式想缓冲区中存入数据
    // flip是将当前的位置position赋值给limit，所以适用于读写当前位置之前的数据。
    System.out.println("3. flip----------------------");
    // 切换读写模式（默认是写模式）

    // flip是将当前的位置position赋值给limit，所以适用于读写当前位置之前的数据。
    // rewind不会改变limit的值，一般会设置为capacity的值；所以会把limit后面的额内容也输出出来
    //    buffer.rewind();
    buffer.flip();
    System.out.println(buffer.position());
    System.out.println(buffer.limit());
    System.out.println(buffer.capacity());

    // 4. 读取数据
    System.out.println("4. get----------------------");
    byte[] dst = new byte[buffer.limit()];
    buffer.get(dst);
    System.out.println(new String(dst));
    System.out.println(buffer.position());
    System.out.println(buffer.limit());
    System.out.println(buffer.capacity());

    System.out.println("5. rewind----------------------");
    buffer.rewind();
    System.out.println(buffer.position());
    System.out.println(buffer.limit());
    System.out.println(buffer.capacity());

    // 6. clear() 清空缓冲区, 但是缓冲区的数据依然存在, 但是出于被遗忘状态
    System.out.println("6. rewind----------------------");
    buffer.clear();
    System.out.println(buffer.position());
    System.out.println(buffer.limit());
    System.out.println(buffer.capacity());
  }
}
