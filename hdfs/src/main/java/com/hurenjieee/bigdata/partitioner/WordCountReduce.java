package com.hurenjieee.bigdata.partitioner;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author Jack
 * @date 2019/7/14 23:17
 */
public class WordCountReduce extends Reducer<Text, IntWritable, Text, IntWritable> {
    /*
        如果是(hello,1),(hello,1),(hello,1)，(hello,1)
        那么这里入参就是
        key: hello
        value: List(1, 1, ...)
    */
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable count : values) {
            sum = sum + count.get();
        }
        context.write(key, new IntWritable(sum));
    }
}
