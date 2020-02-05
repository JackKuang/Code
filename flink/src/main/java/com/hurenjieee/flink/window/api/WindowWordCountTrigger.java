package com.hurenjieee.flink.window.api;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.GlobalWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.CountTrigger;
import org.apache.flink.streaming.api.windowing.triggers.Trigger;
import org.apache.flink.streaming.api.windowing.triggers.TriggerResult;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

/**
 * @author Jack
 * @date 2019/11/12 19:11
 */
public class WindowWordCountTrigger {

  public static void main(String[] args) throws Exception {

    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

    DataStream<Tuple2<String, Integer>> dataStream =
        env.socketTextStream("localhost", 9999)
            .flatMap(new Splitter())
            .keyBy(0)
            .window(GlobalWindows.create())
            // 聚合函数
            .trigger(CountTrigger.of(3))
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
