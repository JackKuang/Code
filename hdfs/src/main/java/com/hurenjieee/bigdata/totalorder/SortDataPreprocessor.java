package com.hurenjieee.bigdata.totalorder;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

import java.io.IOException;


// 转换器，text 转化为SequenceFile
public class SortDataPreprocessor {

    static class CleanerMapper extends Mapper<LongWritable, Text, IntWritable, Text> {

        private NcdcRecordParser parser = new NcdcRecordParser();

        @Override
        protected void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            //0029029070999991901010106004+64333+023450FM-12+000599999V0202701N015919999999N0000001N9-00781+99999102001ADDGF108991999999999999999999
            //内容解析，实际上就是吧String 转化成一个对象内容
            parser.parse(value);
            if (parser.isValidTemperature()) {
                // 以气温排序，气温升序
                context.write(new IntWritable(parser.getAirTemperature()), value);
            }
        }
    }


    //两个参数：/ncdc/input /ncdc/sfoutput
    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();

        String inputPath = "/ncdc/input";
        String outputPath = "/ncdc/sfouput";

        Job job = Job.getInstance(conf, SortDataPreprocessor.class.getSimpleName());
        job.setJarByClass(SortDataPreprocessor.class);
        //
        FileInputFormat.addInputPath(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));

        job.setMapperClass(CleanerMapper.class);

        //最终输出的键、值类型
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(Text.class);
        //reduce个数为0
        job.setNumReduceTasks(0);
        //以sequencefile的格式输出
        job.setOutputFormatClass(SequenceFileOutputFormat.class);

        //设置sequencefile的压缩、压缩算法、sequencefile文件压缩格式block
        SequenceFileOutputFormat.setCompressOutput(job, true);
        //SequenceFileOutputFormat.setOutputCompressorClass(job, GzipCodec.class);
        //SequenceFileOutputFormat.setOutputCompressorClass(job, SnappyCodec.class);
        SequenceFileOutputFormat.setOutputCompressionType(job, SequenceFile.CompressionType.BLOCK);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}