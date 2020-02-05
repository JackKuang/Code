package com.hurenjieee.flink.window.time;

import org.apache.commons.lang3.time.DateFormatUtils;
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
    SingleOutputStreamOperator<Tuple2<String, Long>> dataStream =
        tuple2DataStreamSource
            .flatMap(
                new FlatMapFunction<Tuple2<String, Long>, Tuple2<String, Long>>() {
                  @Override
                  public void flatMap(
                      Tuple2<String, Long> stringLongTuple2,
                      Collector<Tuple2<String, Long>> collector)
                      throws Exception {
                    collector.collect(Tuple2.of(stringLongTuple2.f0, 1L));
                  }
                })
            .keyBy(0)
            .timeWindow(Time.seconds(10), Time.seconds(5))
            .process(new MyProcessWindowFunction());

    dataStream.print().setParallelism(1);
    env.execute("Window WordCount");
  }
}
