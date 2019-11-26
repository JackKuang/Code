package com.hurenjieee.flink.window.time;

import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.Date;

import static java.lang.Long.valueOf;

/**
 * @author Jack
 * @date 2019/11/25 19:30
 */
public class MyProcessWindowFunction
    extends ProcessWindowFunction<Tuple2<String, Long>, Tuple2<String, Long>, Tuple, TimeWindow> {
  FastDateFormat fdf = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

  @Override
  public void process(
      Tuple tuple,
      Context context,
      Iterable<Tuple2<String, Long>> elements,
      Collector<Tuple2<String, Long>> out)
      throws Exception {
    Integer count = 0;
    for (Tuple2<String, Long> tuple2 : elements) {
      count++;
    }
    System.out.println(
        "now:"
            + fdf.format(new Date())
            + ",window start:"
            + fdf.format(context.window().getStart())
            + ",window end:"
            + fdf.format(context.window().getEnd())
            + ",count data:"
            + Tuple2.of(tuple.getField(0), valueOf(count)));
    out.collect(Tuple2.of(tuple.getField(0), valueOf(count)));
  }
}
