package com.hurenjieee.bigdata.common;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author Jack
 * @date 2019/7/22 0:19
 */
class MapReduceMap extends Mapper<LongWritable, Text, Text, IntWritable> {

    public MapReduceMap() {
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] words = value.toString().split(" ");
        System.out.println("Map:::key:" + key.toString() + ":value:" + value.toString());
        for (String word : words) {
            context.write(new Text(word), new IntWritable(1));
        }
    }
}
