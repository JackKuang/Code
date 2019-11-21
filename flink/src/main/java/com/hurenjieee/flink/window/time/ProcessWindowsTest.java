package com.hurenjieee.flink.window.time;

import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.Date;

import static com.hurenjieee.flink.window.time.TimeWindowWordCount.*;
import static java.lang.Long.valueOf;

/**
 * @author Jack
 * @date 2019/11/21 0:02
 */
public class ProcessWindowsTest {

    /**
     * 每隔5秒计算最近10秒单词出现的次数
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        DataStreamSource<Tuple2<String, Long>> tuple2DataStreamSource = env.addSource(new MySource());
        SingleOutputStreamOperator<Tuple2<String, Long>> dataStream = tuple2DataStreamSource
                .flatMap(new FlatMapFunction<Tuple2<String, Long>, Tuple2<String, Long>>() {
                    @Override
                    public void flatMap(Tuple2<String, Long> stringLongTuple2, Collector<Tuple2<String, Long>> collector) throws Exception {
                        collector.collect(Tuple2.of(stringLongTuple2.f0, 1L));
                    }
                })
                .keyBy(0)
                .timeWindow(Time.seconds(10), Time.seconds(5))
                .process(new SumProcessWindowFunction());

        dataStream.print().setParallelism(1);
        env.execute("Window WordCount");
    }

    static class Myfunction extends ProcessWindowFunction<Tuple2<String, Long>, Tuple2<String, Long>, Tuple, TimeWindow> {
        /**
         * param key The key for which this window is evaluated.
         * * @param context The context in which the window is being evaluated.
         * * @param elements The elements in the window being evaluated.
         * * @param out A collector for emitting elements.
         *
         * @param tuple
         * @param context
         * @param elements
         * @param out
         * @throws Exception
         */
        @Override
        public void process(Tuple tuple, Context context, Iterable<Tuple2<String, Long>> elements, Collector<Tuple2<String, Long>> out) throws Exception {
            System.out.println(new Date());
            Integer count = 0;
            for (Tuple2<String, Long> tuple2 : elements) {
                count++;
            }
            out.collect(Tuple2.of(tuple.getField(0), valueOf(count)));
        }
    }


    static class SumProcessWindowFunction extends
            ProcessWindowFunction<Tuple2<String, Long>, Tuple2<String, Long>, Tuple, TimeWindow> {
        FastDateFormat dateFormat = FastDateFormat.getInstance("HH:mm:ss");

        /**
         * 当一个window触发计算的时候会调用这个方法
         *
         * @param tuple    key
         * @param context  operator的上下文
         * @param elements 指定window的所有元素
         * @param out      用户输出
         */
        @Override
        public void process(Tuple tuple, Context context, Iterable<Tuple2<String, Long>> elements,
                            Collector<Tuple2<String, Long>> out) {

//            System.out.println("当天系统的时间："+dateFormat.format(System.currentTimeMillis()));
//
//            System.out.println("Window的处理时间："+dateFormat.format(context.currentProcessingTime()));
//            System.out.println("Window的开始时间："+dateFormat.format(context.window().getStart()));
//            System.out.println("Window的结束时间："+dateFormat.format(context.window().getEnd()));

            int sum = 0;
            for (Tuple2<String, Long> ele : elements) {
                sum += 1;
            }
            // 输出单词出现的次数
            out.collect(Tuple2.of(tuple.getField(0), Long.valueOf(sum)));

        }
    }
}
