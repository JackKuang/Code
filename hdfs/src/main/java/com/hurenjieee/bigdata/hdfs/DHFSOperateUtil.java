package com.hurenjieee.bigdata.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

import java.io.*;
import java.net.URI;

/**
 * @author Jack
 * @date 2019/7/14 19:44
 */
public class DHFSOperateUtil {

    @Test
    public void uploadFile() {
        try {
            String filePath = "D:\\Server\\apache-maven-3.6.1-bin.zip";
            String destination = "hdfs://190.1.1.124:9000/test/apache-maven-3.6.1-bin.zip";
            //  本地文件作为输入流
            InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
            // 获取Hadoop 配置
            Configuration configuration = new Configuration();
            // Hadoop文件系统
            FileSystem fileSystem = FileSystem.get(URI.create(destination), configuration);
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
            String source = "hdfs://190.1.1.124:9000/test/apache-maven-3.6.1-bin.zip";
            String local = "D:\\Server\\apache-maven-3.6.1-bin.zip.new";
            // 获取Hadoop配置
            Configuration configuration = new Configuration();
            // Hadoop文件系统
            FileSystem fileSystem = FileSystem.get(URI.create(source), configuration);
            // Hadoop输入流
            InputStream inputStream = fileSystem.open(new Path(source));
            // 本地文件作为输出流
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(local));
            // 使用hadoop的IOUtils拷贝流
            IOUtils.copyBytes(inputStream, outputStream, 4096, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
