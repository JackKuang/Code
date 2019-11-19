package com.hurenjieee.code.nio;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author Jack
 * @date 2019/10/28 16:56
 */
public class ChannelTest {

  @Test
  public void copyFile() {
    long startTime = System.currentTimeMillis();
    // 1.创建输入输出流对象
    FileInputStream fis = null;
    FileOutputStream fos = null;

    FileChannel inChannel = null;
    FileChannel outChannel = null;

    try {
      fis = new FileInputStream("E:\\jdk-8u221-linux-x64.tar.gz");
      fos = new FileOutputStream("E:\\new.file");

      // 2. 通过流对象获取通道对象channel
      inChannel = fis.getChannel();
      outChannel = fos.getChannel();

      // 3.创建缓存区
      ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

      // 4.将通道数据写入到缓存区
      while (inChannel.read(byteBuffer) != -1) {
        // 切换读模式
        byteBuffer.flip();
        // 5.将缓存区的数据写入到输出通道
        outChannel.write(byteBuffer);
        byteBuffer.clear();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (outChannel != null) {
      try {
        outChannel.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    if (inChannel != null) {
      try {
        inChannel.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    if (fis != null) {
      try {
        fis.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    if (fos != null) {
      try {
        fos.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    long endTime = System.currentTimeMillis();

    System.out.println("cost time " + (endTime - startTime));
  }

  /**
   * 两者的区别，前面是通过数据流来传输数据 MappedByteBuffer 是Java
   * NIO中引入的一种硬盘物理文件和内存映射方式，当物理文件较大时，采用MappedByteBuffer，读写性能较高， 其内部的核心实现是DirectByteBuffer(JVM
   * 堆外直接物理内存)。
   *
   * <p>传统的基于文件流的方式读取文件方式是系统指令调用，文件数据首先会被读取到进程的内核空间的缓冲区， 而后复制到进程的用户空间，这个过程中存在两次数据拷贝；
   * 而内存映射方式读取文件的方式，也是系统指令调用，在产生缺页中断后， CPU直接从磁盘文件load数据到进程的用户空间，只有一次数据拷贝
   *
   * <p>cost time 4274
   *
   * <p>Mapped cost time 671
   */
  @Test
  public void copyFileMapped() {

    long startTime = System.currentTimeMillis();
    try {
      // 1.使用直接缓冲区完成文件的复制(基内存映射文件)
      FileChannel inChannel =
          FileChannel.open(Paths.get("E:\\jdk-8u221-linux-x64.tar.gz"), StandardOpenOption.READ);
      FileChannel outChannel =
          FileChannel.open(
              Paths.get("E:\\new.file2"),
              StandardOpenOption.WRITE,
              StandardOpenOption.READ,
              StandardOpenOption.CREATE);

      // 2. 内存映射文件
      MappedByteBuffer inMappedBuffer =
          inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());

      // 3. 对缓冲区进行数据的读写操作
      MappedByteBuffer outMappedBuffer =
          outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());
      byte[] dst = new byte[inMappedBuffer.limit()];
      inMappedBuffer.get(dst);
      outMappedBuffer.put(dst);

      // 4. 回收资源
      inChannel.close();
      outChannel.close();
      long endTime = System.currentTimeMillis();

      System.out.println("Mapped cost time " + (endTime - startTime));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
