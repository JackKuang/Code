package com.hurenjieee.bigdata.smallfile;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.net.URI;

public class SequenceFileWriteNewVersion {

    private static final String[] DATA = {
            "One, two, buckle my shoe",
            "Three, four, shut the door",
            "Five, six, pick up sticks",
            "Seven, eight, lay them straight",
            "Nine, ten, a big fat hen"
    };

    public static void main(String[] args) throws IOException {
        //输出路径
        String uri = args[0];
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(uri), conf);
        Path path = new Path(args[0]);

        IntWritable key = new IntWritable();
        Text value = new Text();
//
//            FileContext fileContext = FileContext.getFileContext(URI.create(uri));
//            Class<?> codecClass = Class.forName("org.apache.hadoop.io.compress.SnappyCodec");
//            CompressionCodec SnappyCodec = (CompressionCodec)ReflectionUtils.newInstance(codecClass, conf);
//            SequenceFile.Metadata metadata = new SequenceFile.Metadata();
//            //writer = SequenceFile.createWriter(fs, conf, path, key.getClass(), value.getClass());
//            writer = SequenceFile.createWriter(conf, SequenceFile.Writer.file(path), SequenceFile.Writer.keyClass(IntWritable.class),
//                                        SequenceFile.Writer.valueClass(Text.class));

        SequenceFile.Writer.Option pathOption       = SequenceFile.Writer.file(path);
        SequenceFile.Writer.Option keyOption        = SequenceFile.Writer.keyClass(IntWritable.class);
        SequenceFile.Writer.Option valueOption      = SequenceFile.Writer.valueClass(Text.class);
        SequenceFile.Writer.Option compressOption   = SequenceFile.Writer.compression(SequenceFile.CompressionType.BLOCK);

        SequenceFile.Writer writer = SequenceFile.createWriter(conf, pathOption, keyOption, valueOption, compressOption);

        for (int i = 0; i < 100; i++) {
            key.set(100 - i);
            value.set(DATA[i % DATA.length]);
            System.out.printf("[%s]\t%s\t%s\n", writer.getLength(), key, value);
            writer.append(key, value);
        }
        IOUtils.closeStream(writer);
    }
}
