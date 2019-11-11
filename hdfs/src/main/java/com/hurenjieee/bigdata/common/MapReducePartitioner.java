package com.hurenjieee.bigdata.common;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

import java.util.HashMap;

/**
 * @author Jack
 * @date 2019/7/22 0:20
 */
class MapReducePartitioner extends Partitioner<Text, IntWritable> {

    public HashMap<String, Integer> dict = new HashMap<String, Integer>();

    {
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
