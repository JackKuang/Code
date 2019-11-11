package com.hurenjieee.bigdata.secondrysort;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author Jack
 * @date 2019/7/15 21:55
 */
public class SecondarySort {


    public static void main(String[] args) {
        try {
            String inputPath = "/salary.txt";
            String outputPath = "/SecondarySort/salary";

            Configuration configuration = new Configuration();

            Job job = Job.getInstance(configuration, SecondarySort.class.getSimpleName());

            // 设置输入/输出路径
            FileInputFormat.setInputPaths(job, new Path(inputPath));
            FileOutputFormat.setOutputPath(job, new Path(outputPath));

            // 设置处理Map/Reduce阶段的类
            job.setMapperClass(MyMap.class);
            job.setMapOutputKeyClass(Person.class);
            job.setMapOutputValueClass(NullWritable.class);


            job.setReducerClass(MyReduce.class);
            job.setOutputKeyClass(Person.class);
            job.setOutputValueClass(NullWritable.class);

            //设置reduce的个数
            job.setNumReduceTasks(1);

            job.waitForCompletion(true);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {

        }
    }

    public static class MyMap extends
            Mapper<LongWritable, Text, Person, NullWritable> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] field = value.toString().split("\\s");
            String name = field[0];
            int age = Integer.valueOf(field[1]);
            int salary = Integer.valueOf(field[2]);
            Person person = new Person(name, age, salary);
            context.write(person, NullWritable.get());
        }
    }

    public static class MyReduce extends Reducer<Person, NullWritable, Person, NullWritable> {

        @Override
        protected void reduce(Person key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            context.write(key, NullWritable.get());
        }
    }
}
