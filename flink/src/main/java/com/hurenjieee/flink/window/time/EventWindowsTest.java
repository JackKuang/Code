package com.hurenjieee.flink.window.time;

import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.flink.api.common.functions.FlatMapFunction;
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
public class EventWindowsTest {

  public static void main(String[] args) throws Exception {

    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    env.setParallelism(1);
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
    DataStreamSource<Tuple2<String, Long>> tuple2DataStreamSource = env.addSource(new MySource());
    SingleOutputStreamOperator<Tuple2<String, Long>> dataStream =
        tuple2DataStreamSource
            .assignTimestampsAndWatermarks(
                new AssignerWithPeriodicWatermarks<Tuple2<String, Long>>() {
                  @Nullable
                  @Override
                  public Watermark getCurrentWatermark() {
                    return new Watermark(System.currentTimeMillis() - 100000);
                  }

                  @Override
                  public long extractTimestamp(
                      Tuple2<String, Long> element, long previousElementTimestamp) {
                    return element.f1;
                  }
                })
            .keyBy(0)
            .timeWindow(Time.seconds(10), Time.seconds(5))
            .process(new MyProcessWindowFunction());

    dataStream.print().setParallelism(1);
    env.execute("Window WordCount");
  }
}
