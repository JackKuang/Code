package com.hurenjieee.bigdata.common;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;

/**
 * @author Jack
 * @date 2019/7/21 23:55
 */
public class MapReduce {

    public static void main(String[] args) {
        try {
            String inputPath = "/test.txt";
            String outputPath = "/common/countWord1";
            // 读取加载配置
            Configuration configuration = new Configuration();
            // 创建Job实例
            Job job = Job.getInstance(configuration, MapReduce.class.getSimpleName());
            // 确定运行Jar
            job.setJarByClass(MapReduce.class);
            // 设置输入/输出路径
            FileInputFormat.setInputPaths(job, new Path(inputPath));
            FileOutputFormat.setOutputPath(job, new Path(outputPath));

            // 通过job设置输入/输出格式，这两个是默认实现
            job.setInputFormatClass(TextInputFormat.class);
            job.setOutputFormatClass(TextOutputFormat.class);

            // 设置Map处理函数
            job.setMapperClass(MapReduceMap.class);
            // 设置Mapper输出格式
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(IntWritable.class);
            // 设置Reduce处理函数
            job.setReducerClass(MapReduceReduce.class);
            // 设置Reduce输出格式
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);

            // 设置分块处理方法
            job.setPartitionerClass(MapReducePartitioner.class);
            job.setNumReduceTasks(4);

            // 提交作业
            job.waitForCompletion(true);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}