package com.hurenjieee.flink.window.api;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.collector.selector.OutputSelector;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * @author Jack
 * @date 2019/11/12 19:11
 */
public class WindowWordCountAggregate {

  public static void main(String[] args) throws Exception {

    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

    DataStream<Tuple2<String, Integer>> dataStream =
        env.socketTextStream("localhost", 8888)
            .flatMap(new Splitter())
            .keyBy(0)
            .timeWindow(Time.seconds(5))
            // 聚合函数
            .aggregate(
                new AggregateFunction<
                    Tuple2<String, Integer>, Tuple2<String, Integer>, Tuple2<String, Integer>>() {
                  @Override
                  public Tuple2<String, Integer> createAccumulator() {
                    return Tuple2.of("", 0);
                  }

                  @Override
                  public Tuple2<String, Integer> add(
                      Tuple2<String, Integer> in, Tuple2<String, Integer> acc) {
                    return Tuple2.of(in.f0, acc.f1 + 1);
                  }

                  @Override
                  public Tuple2<String, Integer> getResult(
                      Tuple2<String, Integer> stringIntegerTuple2) {
                    return stringIntegerTuple2;
                  }

                  @Override
                  public Tuple2<String, Integer> merge(
                      Tuple2<String, Integer> a, Tuple2<String, Integer> b) {
                    return Tuple2.of(a.f0, a.f1 + b.f1);
                  }
                });

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
