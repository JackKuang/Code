package com.hurenjieee.bigdata.sequentce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.util.ReflectionUtils;
import org.junit.Test;

import java.net.URI;

/**
 * @author Jack
 * @date 2019/7/14 20:36
 */
public class SequenceFileUtil {

    @Test
    public void write() {
        SequenceFile.Writer writer = null;
        try {
            String[] data = {"this is test a.", "this is test b."};
            String destination = "hdfs://node1:9000/sequenceFile";

            Configuration configuration = new Configuration();
            FileSystem fileSystem = FileSystem.get(URI.create(destination), configuration);

            IntWritable key = new IntWritable();
            Text value = new Text();
            SequenceFile.Writer.Option pathOption = SequenceFile.Writer.file(new Path(destination));
            SequenceFile.Writer.Option keyOption = SequenceFile.Writer.keyClass(IntWritable.class);
            SequenceFile.Writer.Option valueOption = SequenceFile.Writer.valueClass(Text.class);
            // BLOCK，RECORD两种方式
            SequenceFile.Writer.Option compressOption = SequenceFile.Writer.compression(SequenceFile.CompressionType.BLOCK);


            writer = SequenceFile.createWriter(configuration, pathOption, keyOption, valueOption, compressOption);
            for (int i = 0; i < 100; i++) {
                key.set(i);
                value.set(data[i % data.length]);
                System.out.printf("[%s]____[%s]_____[%s]\n", writer.getLength(), key.get(), value);
                writer.append(key, value);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                IOUtils.closeStream(writer);
            }
        }
    }


    @Test
    public void read() {
        SequenceFile.Reader reader = null;
        try {
//            String destination = "hdfs://node1:9000/sequenceFile";
            String destination = "hdfs://node1:9000/ncdc/sfouput";
            SequenceFile.Reader.Option pathOption = SequenceFile.Reader.file(new Path(destination));

            Configuration configuration = new Configuration();
            reader = new SequenceFile.Reader(configuration, pathOption);
            Writable key = (Writable) ReflectionUtils.newInstance(reader.getKeyClass(), configuration);
            Writable value = (Writable) ReflectionUtils.newInstance(reader.getValueClass(), configuration);
            long position = reader.getPosition();
            while (reader.next(key, value)) {
                String syncSee = reader.syncSeen() ? "*" : "";
                System.out.printf("[%s]____[%s]_____[%s]_____[%s]\n", position, syncSee, key, value);
                // 跳转到下一个记录点
                position = reader.getPosition();


            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                IOUtils.closeStream(reader);
            }
        }
    }

}
