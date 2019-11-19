package com.hurenjieee.flink;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.IterativeStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author Jack
 * @date 2019/11/12 21:04
 */
public class IterativeStreamTest {
  public static void main(String[] args) {
    //
    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    DataStream<Long> someIntegers = env.generateSequence(0, 1000);

    IterativeStream<Long> iteration = someIntegers.iterate();

    DataStream<Long> minusOne =
        iteration.map(
            new MapFunction<Long, Long>() {
              @Override
              public Long map(Long value) throws Exception {
                return value - 1;
              }
            });

    DataStream<Long> stillGreaterThanZero =
        minusOne.filter(
            new FilterFunction<Long>() {
              @Override
              public boolean filter(Long value) throws Exception {
                return (value > 0);
              }
            });

    iteration.closeWith(stillGreaterThanZero);

    DataStream<Long> lessThanZero =
        minusOne.filter(
            new FilterFunction<Long>() {
              @Override
              public boolean filter(Long value) throws Exception {
                return (value <= 0);
              }
            });
    lessThanZero.print();
  }
}
