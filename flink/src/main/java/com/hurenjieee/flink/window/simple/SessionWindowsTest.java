package com.hurenjieee.flink.window.simple;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.EventTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.assigners.ProcessingTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

import java.awt.*;

/**
 * @author Jack
 * @date 2019/11/21 0:02
 */
public class SessionWindowsTest {

  /**
   * 每收到n条消息的时候处理
   *
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {

    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

    DataStream<Tuple2<String, Integer>> dataStream =
        env.socketTextStream("localhost", 9999)
            .flatMap(new SlidingWindowTest.Splitter())
            .keyBy(0)
            .window(ProcessingTimeSessionWindows.withGap(Time.seconds(5)))
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