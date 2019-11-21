package com.hurenjieee.flink.window.time;

import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import javax.annotation.Nullable;
import java.util.Date;

import static java.lang.Long.valueOf;

/**
 * @author Jack
 * @date 2019/11/21 0:02
 */
public class WaitEventWindowsTest {

    /**
     * 每隔5秒计算最近10秒单词出现的次数
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        DataStreamSource<Tuple2<String, Long>> tuple2DataStreamSource = env.addSource(new MySource());
        SingleOutputStreamOperator<Tuple2<String, Long>> dataStream = tuple2DataStreamSource
                .assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks<Tuple2<String, Long>>() {
                    @Nullable
                    @Override
                    public Watermark getCurrentWatermark() {
                        return new Watermark(System.currentTimeMillis() - 5000);
                    }

                    @Override
                    public long extractTimestamp(Tuple2<String, Long> element, long previousElementTimestamp) {
                        return element.f1;
                    }
                })
                .keyBy(0)
                .timeWindow(Time.seconds(10), Time.seconds(5))
                .process(new Myfunction());

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
}
