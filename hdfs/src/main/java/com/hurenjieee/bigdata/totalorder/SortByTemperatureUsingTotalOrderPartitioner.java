package com.hurenjieee.bigdata.totalorder;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile.CompressionType;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.partition.InputSampler;
import org.apache.hadoop.mapreduce.lib.partition.TotalOrderPartitioner;

import java.net.URI;

//A MapReduce program for sorting a SequenceFile with IntWritable keys using the TotalOrderPartitioner to globally sort the data
public class SortByTemperatureUsingTotalOrderPartitioner {

    //两个参数：/ncdc/sfoutput /ncdc/totalorder
    public static void main(String[] args) throws Exception {

        String inputPath = "/ncdc/sfoutput";
        String outputPath = "/ncdc/totalorder";

        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf, SortByTemperatureUsingTotalOrderPartitioner.class.getSimpleName());
        job.setJarByClass(SortByTemperatureUsingTotalOrderPartitioner.class);

        FileInputFormat.addInputPath(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));

        job.setInputFormatClass(SequenceFileInputFormat.class);
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputFormatClass(SequenceFileOutputFormat.class);

        SequenceFileOutputFormat.setCompressOutput(job, true);
        //SequenceFileOutputFormat.setOutputCompressorClass(job, GzipCodec.class);
        SequenceFileOutputFormat.setOutputCompressionType(job, CompressionType.BLOCK);

        //
        job.setNumReduceTasks(3);

        //分区器
        job.setPartitionerClass(TotalOrderPartitioner.class);

        //每一个参数：采样率；第二个参数：最大样本数；第三个参数：最大分区数；三者任一满足，就停止采样
        InputSampler.Sampler<IntWritable, Text> sampler =
                new InputSampler.RandomSampler<IntWritable, Text>(0.1, 10000, 10);

        InputSampler.writePartitionFile(job, sampler);

        // 获取缓存文件地址
        String partitionFile = TotalOrderPartitioner.getPartitionFile(conf);
        URI partitionUri = new URI(partitionFile);
        //添加到分布式缓存中
        job.addCacheFile(partitionUri);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
