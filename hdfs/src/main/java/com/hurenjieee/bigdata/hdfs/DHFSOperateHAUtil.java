package com.hurenjieee.bigdata.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

/**
 * @author Jack
 * @date 2019/7/14 19:44
 */
public class DHFSOperateHAUtil {

  static String ClusterName = "ha";
  private static final String HADOOP_URL = "hdfs://" + ClusterName;
  public static Configuration conf;

  static {
    conf = new Configuration();
    conf.set("fs.defaultFS", HADOOP_URL);
    conf.set("dfs.nameservices", ClusterName);
    conf.set("dfs.ha.namenodes." + ClusterName, "nn1,nn2");
    conf.set("dfs.namenode.rpc-address." + ClusterName + ".nn1", "172.17.0.3:9000");
    conf.set("dfs.namenode.rpc-address." + ClusterName + ".nn2", "172.17.0.4:9000");
    // conf.setBoolean(name, value);
    conf.set(
        "dfs.client.failover.proxy.provider." + ClusterName,
        "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
    conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
  }

  @Test
  public void uploadFile() {
    try {
      String filePath = "D:\\Server\\apache-maven-3.6.1-bin.zip";
      String destination = "/test/apache-maven-3.6.1-bin.zip.ha";
      //  本地文件作为输入流
      InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
      // Hadoop文件系统
      FileSystem fileSystem = FileSystem.get(URI.create(HADOOP_URL + destination), conf);
      // 输出流
      OutputStream outputStream = fileSystem.create(new Path(destination));
      // 使用hadoop的IOUtils拷贝流
      IOUtils.copyBytes(inputStream, outputStream, 4096, true);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void downloadFile() {
    try {
      String source = "/test/apache-maven-3.6.1-bin.zip";
      String local = "D:\\Server\\apache-maven-3.6.1-bin.zip.new2";
      // Hadoop文件系统
      FileSystem fileSystem = FileSystem.get(URI.create(HADOOP_URL + source), conf);
      // Hadoop输入流
      InputStream inputStream = fileSystem.open(new Path(HADOOP_URL + source));
      // 本地文件作为输出流
      OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(local));
      // 使用hadoop的IOUtils拷贝流
      IOUtils.copyBytes(inputStream, outputStream, 4096, true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
