package com.hurenjieee.bigdata.load;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Hdfs;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;

import java.io.IOException;

/**
 * HDFS -> HBase
 * @author Jack
 * @date 2019/8/27 22:22
 */
public class Hdfs2Hbase {

    public static class HdfsMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            context.write(value, NullWritable.get());
        }
    }

    public static class HBaseReducer extends TableReducer<Text, NullWritable, ImmutableBytesWritable> {
        @Override
        protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            String[] split = key.toString().split(" ");
            Put put = new Put(Bytes.toBytes(split[0]));
            put.addColumn("f1".getBytes(), "name".getBytes(), split[1].getBytes());
            put.addColumn("f1".getBytes(), "age".getBytes(), split[2].getBytes());
            context.write(new ImmutableBytesWritable(Bytes.toBytes(split[0])), put);
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);
        job.setJarByClass(Hdfs2Hbase.class);

        job.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job, new Path(args[0]));
        job.setMapperClass(HdfsMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);

        TableMapReduceUtil.initTableReducerJob(args[1], HBaseReducer.class, job);
        job.setNumReduceTasks(1);
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
