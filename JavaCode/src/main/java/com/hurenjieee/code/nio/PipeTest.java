package com.hurenjieee.code.nio;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * @author renjie.hu@guuidea.com
 * @date 2019/10/28 17:41
 */
public class PipeTest {

  @Test
  public void pipe() {
    try {
      // 1. 获取通道
      Pipe pipe = Pipe.open();

      // 2. 创建缓冲区对象
      ByteBuffer buffer = ByteBuffer.allocate(1024);

      // 3. 写入sinkChannel
      Pipe.SinkChannel sinkChannel = pipe.sink();
      buffer.put("data".getBytes());
      buffer.flip();
      sinkChannel.write(buffer);

      // 4. 读取sourceChannel
      Pipe.SourceChannel sourceChannel = pipe.source();
      buffer.flip();
      int len = sourceChannel.read(buffer);

      System.out.println(new String(buffer.array(), 0, len));

      // 5.关闭
      sourceChannel.close();
      sinkChannel.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
