package com.hurenjieee.bigdata.load;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;

import java.io.IOException;

/**
 * HBase -> HBase
 * @author Jack
 * @date 2019/8/27 21:43
 */
public class HBaseMR {
    // TableMapper 与普通的Mapper不一样，<Text, Put> --> <key,value>
    public static class HBaseMapper extends TableMapper<Text, Put> {
        @Override
        protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {
            // 获取rowkey的字节数组
            byte[] bytes = key.get();
            String rowKey = Bytes.toHex(bytes);
            // 构建一个put对象
            Put put = new Put(bytes);
            // 获取一行中所有的cell对象
            Cell[] cells = value.rawCells();
            for (Cell cell : cells) {
                if ("f1".equals(Bytes.toString(CellUtil.cloneFamily(cell)))) {
                    if ("name".equals(Bytes.toString(CellUtil.cloneQualifier(cell)))) {
                        put.add(cell);
                    }
                }
            }
            if (!put.isEmpty()) {
                context.write(new Text(rowKey), put);
            }
        }
    }

    public static class HBaseReducer extends TableReducer<Text, Put, ImmutableBytesWritable> {
        @Override
        protected void reduce(Text key, Iterable<Put> values, Context context) throws IOException, InterruptedException {
            for (Put put : values) {
                context.write(null, put);
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();

        // 可以设置查询过滤
        Scan scan = new Scan();
        Job job = Job.getInstance(configuration);
        job.setJarByClass(HBaseMR.class);
        TableMapReduceUtil.initTableMapperJob(TableName.valueOf(args[0]), scan, HBaseMapper.class, Text.class, Put.class, job);
        TableMapReduceUtil.initTableReducerJob(args[1], HBaseReducer.class, job);
        job.setNumReduceTasks(1);
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
