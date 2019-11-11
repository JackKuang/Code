package com.hurenjieee.bigdata.partitioner;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

import java.util.HashMap;

/**
 * @author Jack
 * @date 2019/7/15 21:09
 */
public class CustomPartitioner extends Partitioner<Text, IntWritable> {

    public static HashMap<String, Integer> dict = new HashMap<String, Integer>();

    static {
        dict.put("this", 0);
        dict.put("is", 1);
        dict.put("a", 2);
        dict.put("test", 3);
    }

    @Override
    public int getPartition(Text key, IntWritable value, int numPartitions) {
        int partitionIndex = dict.get(key.toString());
        return partitionIndex;
    }
}
