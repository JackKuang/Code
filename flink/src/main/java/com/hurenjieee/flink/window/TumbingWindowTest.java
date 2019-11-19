package com.hurenjieee.flink.window;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * @author Jack
 * @date 2019/11/20 0:39
 */
public class TumbingWindowTest {
    public static void main(String[] args) throws Exception {

        /**
         * 每隔5秒计算最近5秒单词出现的次数
         */
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Tuple2<String, Integer>> dataStream =
                env.socketTextStream("localhost", 9999)
                        .flatMap(new SlidingWindowTest.Splitter())
                        .keyBy(0)
                        .timeWindow(Time.seconds(5))
                        .sum(1);

        dataStream.print();
        env.execute("Window WordCount");
    }

    public static class Splitter implements FlatMapFunction<String, Tuple2<String, Integer>> {
        @Override
        public void flatMap(String sentence, Collector<Tuple2<String, Integer>> out) throws Exception {
            for (String word : sentence.split(" ")) {
                out.collect(new Tuple2<String, Integer>(word, 1));
            }
        }
    }
}
