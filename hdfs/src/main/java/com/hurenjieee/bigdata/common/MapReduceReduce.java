package com.hurenjieee.bigdata.common;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author Jack
 * @date 2019/7/22 0:19
 */
class MapReduceReduce extends Reducer<Text, IntWritable, Text, IntWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable count : values) {
            sum = sum + count.get();
        }
        context.write(key, new IntWritable(sum));
    }
}
