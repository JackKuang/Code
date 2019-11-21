package com.hurenjieee.flink.window.time;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.util.TimeUtils;
import sun.misc.Cache;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Jack
 * @date 2019/11/21 23:08
 */
public class MySource implements SourceFunction<Tuple2<String, Long>> {
    @Override
    public void run(SourceContext<Tuple2<String, Long>> context) throws Exception {
        long time = System.currentTimeMillis();
        while (Calendar.getInstance().get(Calendar.SECOND) % 10 != 0) {
        }
        System.out.println("开始输出：" + new Date());
        TimeUnit.SECONDS.sleep(13);
        context.collect(new Tuple2<>("a", System.currentTimeMillis()));
        long time2 = System.currentTimeMillis();
        TimeUnit.SECONDS.sleep(3);
        context.collect(new Tuple2<>("a", System.currentTimeMillis()));
        TimeUnit.SECONDS.sleep(3);
        context.collect(new Tuple2<>("a", time2));
        TimeUnit.SECONDS.sleep(60);
    }

    @Override
    public void cancel() {

    }
}
