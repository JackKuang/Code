package com.hurenjieee.flink.window.time;

import com.hurenjieee.flink.window.simple.SlidingWindowTest;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * @author Jack
 * @date 2019/11/21 0:02
 */
public class NormalWindowsTest {

    /**
     * 每隔5秒计算最近10秒单词出现的次数
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        SingleOutputStreamOperator<Tuple2<String, Long>> dataStream = env.addSource(new MySource())
                .flatMap(new FlatMapFunction<Tuple2<String, Long>, Tuple2<String, Long>>() {
                    @Override
                    public void flatMap(Tuple2<String, Long> stringLongTuple2, Collector<Tuple2<String, Long>> collector) throws Exception {
                        collector.collect(new Tuple2<String, Long>(stringLongTuple2.f0, 1L));
                    }
                })
                .keyBy(0)
                .timeWindow(Time.seconds(10), Time.seconds(5))
                .sum(1);

        dataStream.print();
        env.execute("Window WordCount");
    }


}
