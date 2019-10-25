package com.hurenjieee.code.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Java 8 日期时间 API
 */
public class DateUtil {

    //Instant：时间戳
    //Duration：持续时间，时间差
    //LocalDate：只包含日期，比如：2016-10-20
    //LocalTime：只包含时间，比如：23:12:10
    //LocalDateTime：包含日期和时间，比如：2016-10-20 23:14:21
    //Period：时间段
    //ZoneOffset：时区偏移量，比如：+8:00
    //ZonedDateTime：带时区的时间
    //Clock：时钟，比如获取目前美国纽约的时间

    public static void main(String args[]) {
        System.out.println(1);
        DateUtil java8tester = new DateUtil();
        java8tester.testLocalDateTime();
    }

    public void testLocalDateTime() {

        // 获取当前的日期时间
        LocalDateTime currentTime = LocalDateTime.now();
        System.out.println("当前时间: " + currentTime);

        LocalDate date1 = currentTime.toLocalDate();
        System.out.println("date1: " + date1);

        Month month = currentTime.getMonth();
        int day = currentTime.getDayOfMonth();
        int seconds = currentTime.getSecond();

        System.out.println("月: " + month + ", 日: " + day + ", 秒: " + seconds);

        LocalDateTime date2 = currentTime.withDayOfMonth(10).withYear(2012);
        System.out.println("date2: " + date2);

        // 12 december 2014
        LocalDate date3 = LocalDate.of(2014, Month.DECEMBER, 12);
        System.out.println("date3: " + date3);

        // 22 小时 15 分钟
        LocalTime date4 = LocalTime.of(22, 15);
        System.out.println("date4: " + date4);

        // 解析字符串
        LocalTime date5 = LocalTime.parse("20:15:30");
        System.out.println("date5: " + date5);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime date6 = LocalDateTime.parse("2014-12-11 00:00:00", formatter);
        System.out.println("date5: " + date6);

        // 时间戳转换
        LocalDateTime dateTime = LocalDateTime.now();
        dateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        dateTime.toInstant(ZoneOffset.of("+08:00")).toEpochMilli();
        dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        // 时间戳转LocalDateTime
        long timestamp = System.currentTimeMillis();
        Instant instant = Instant.ofEpochMilli(timestamp);
        LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        // 时区
        Clock clock = Clock.systemDefaultZone();
        System.out.println("Clock: " + clock);

        //主要是Period类方法getYears（），getMonths（）和getDays（）来计算.
        LocalDate today = LocalDate.now();
        System.out.println("Today : " + today);
        LocalDate birthDate = LocalDate.of(1993, Month.OCTOBER, 19);
        System.out.println("BirthDate : " + birthDate);
        Period p = Period.between(birthDate, today);
        System.out.printf("年龄 : %d 年 %d 月 %d 日", p.getYears(), p.getMonths(), p.getDays());


        //Duration提供了使用基于时间的值（如秒，纳秒）测量时间量的方法。(比Period更精细)
        Instant inst1 = Instant.now();
        System.out.println("Inst1 : " + inst1);
        Instant inst2 = inst1.plus(Duration.ofSeconds(10));
        System.out.println("Inst2 : " + inst2);
        Duration duration = Duration.between(inst1,inst2);
        System.out.println("Difference in milliseconds : " +duration.toMillis());
        System.out.println("Difference in seconds : " + duration.getSeconds());

        //ChronoUnit类可用于在单个时间单位内测量一段时间，例如天数或秒。
        LocalDate startDate = LocalDate.of(1993, Month.OCTOBER, 19);
        System.out.println("开始时间  : " + startDate);
        LocalDate endDate = LocalDate.of(2017, Month.JUNE, 16);
        System.out.println("结束时间 : " + endDate);
        long daysDiff = ChronoUnit.DAYS.between(startDate, endDate);
        System.out.println("两天之间的差在天数   : " + daysDiff);


    }
}
