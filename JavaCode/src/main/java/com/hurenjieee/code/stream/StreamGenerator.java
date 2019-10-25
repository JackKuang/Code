package com.hurenjieee.code.stream;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author Jack
 * @date 2019/7/6 15:38
 */
public class StreamGenerator {

    class Student {
        String name;
        String clazz;
        Integer age;

        public Student(String name, String clazz, Integer age) {
            this.name = name;
            this.clazz = clazz;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", clazz='" + clazz + '\'' +
                    ", age=" + age +
                    '}';
        }

        @Override
        public boolean equals(Object obj) {
            return this.name.equals(((Student) obj).name);
        }

        @Override
        public int hashCode() {
            return 1;
        }
    }


    private List<Student> studentList = new ArrayList<>();

    Stream<Student> studentStream;

    @Before
    public void init() {
        studentList.add(new Student("张三1", "A班", 10));
        studentList.add(new Student("李四2", "A班", 9));
        studentList.add(new Student("王五3", "A班", 10));
        studentList.add(new Student("张三1", "B班", 11));
        studentList.add(new Student("李四5", "B班", 10));
        studentStream = studentList.stream();
    }

    @Test
    public void generateSteam() {
        Stream<Student> stream = studentList.stream();
        Stream<Student> parallelStream = studentList.parallelStream();
    }

    @Test
    public void steamIntermediate() {

        // *****************************中间操作（intermediate operation）
        // 去重判断需要重写hashCode与equals方法
        System.out.println("distinct:去重判断需要重写hashCode与equals方法");
        studentList.stream().distinct().forEach(System.out::println);
        // 过滤
        System.out.println("filter:过滤");
        studentList.stream().filter(s -> s.age > 10).forEach(System.out::println);
        // 类型转换
        System.out.println("map:类型转换");
        studentList.stream().map(s -> s.name).forEach(System.out::println);
        // 排序
        System.out.println("sorted:排序");
        studentList.stream().sorted((a, b) -> a.age - b.age).forEach(System.out::println);
        // 获取前n个，如果是并行的，计算结果可能不一致
        System.out.println("limit:获取前n个");
        studentList.stream().limit(2).forEach(System.out::println);
        System.out.println("limit:串行流获取前n个");
        studentList.parallelStream().limit(2).forEach(System.out::println);
        // 跳过n个
        System.out.println("skip:跳过n个");
        studentList.stream().skip(2).forEach(System.out::println);

    }

    @Test
    public void streamTerminal() {
        // *****************************最终操作(terminal operation)
        // 找出适配
        System.out.println("match:找出适配");
        System.out.println(studentList.stream().allMatch((a) -> a.age > 10));
        System.out.println(studentList.stream().anyMatch((a) -> a.age > 10));
        System.out.println(studentList.stream().noneMatch((a) -> a.age > 10));
        // 统计
        System.out.println("count:统计");
        System.out.println(studentList.stream().count());

        System.out.println("collect:收集数据结果");
        System.out.println(studentList.stream().map(s -> s.name).collect(Collectors.joining(",")));
        System.out.println(studentList.stream().map(s -> s.age).collect(Collectors.averagingInt(s -> s)));

        System.out.println("find:查询");
        System.out.println(studentList.stream().filter(s -> s.age > 10).findAny());
        Optional<Student> student = studentList.stream().filter(s -> s.age > 100).findFirst();

        if(student.isPresent()){
            System.out.println("student Optional is null");
        }else {
            System.out.println("student Optional is not null");
        }

        System.out.println("forEach:遍历");
        studentList.stream().filter(s -> s.age > 10).forEach(System.out::println);

        System.out.println("max/min:最大值最小值");
        System.out.println(
                studentList.stream().max(new Comparator<Student>() {
                    @Override
                    public int compare(Student o1, Student o2) {
                        return o1.age - o2.age;
                    }
                }));

        System.out.println("reduce:计算结果值");
        System.out.println(studentList.stream().map(s -> s.age).reduce((a, b) -> a + b));

        System.out.println("toArray:数据转化为数组");
        System.out.println("concat:将两个steam连接");

    }

    @Test
    public void streamParallel() {

        /**
         * 数据量大且复杂，多核的情况下考虑
         * 所有的流操作都可以串行执行或者并行执行。
         * 除非显示地创建并行流，否则Java库中创建的都是串行流。
         * Collection.stream()为集合创建串行流，Collection.parallelStream()为集合创建并行流。通过parallel()方法可以将串行流转换成并行流,sequential()方法将流转换成串行流。
         * parallel stream通过默认的ForkJoinPool实现并行处理。处理的过程采用分治进行，将整个任务切分成多个段，分别对各个段进行处理。通过parallel stream对数据进行处理会使数据失去原始的顺序性。
         * 流的原始顺序性依赖于数据源的有序性。在使用并行流会改变流中元素的处理顺序，破坏流的原始顺序性，所以在使用并行流对数据源进行处理时应确定数据源的元素满足结合性。
         * 可以使用`forEachOrdered()`可以保持数据源原有的顺序性，或者通过`sorted()`重新定义数据的顺序。
         */
        IntStream.range(1, 10).forEach(System.out::print);
        System.out.println();
        IntStream.range(1, 10).parallel().forEach(System.out::print);
        System.out.println();
        IntStream.range(1, 10).parallel().forEachOrdered(System.out::print);

        // 流可以从非线程安全的集合中创建，当流执行终点操作的时候，非concurrent数据源不应该被改变。
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.stream().forEach(s -> list.add("three"));
        //并发异常 ：java.util.ConcurrentModificationException

        // 而concurrent数据源可以被修改，不会出现并发问题。
        List<String> list2 = new CopyOnWriteArrayList<>(Arrays.asList("one", "two"));
        list2.stream().forEach(s -> list2.add("three"));
        //正常执行

        //由于stream的延迟操作特性，在执行终点操作前可以修改数据源，在执行终点操作时会将修改应用。
        List<String> list3 = new ArrayList<>();
        list3.add("one");
        list3.add("two");
        Stream<String> listStream = list3.stream();
        list3.add("three");
        listStream.forEach(System.out::println);
        //输出 one two three

    }

    @Test
    public void flatMap() {
        // map是一对一映射
        // flatMap是将2维的集合映射成一维
        List<String> list = Arrays.asList("beijing changcheng", "beijing gugong", "beijing tiantan", "gugong tiananmen");
        //map只能将分割结果转成一个List,所以输出为list对象
        list.stream().map(item -> Arrays.stream(item.split(" "))).forEach(System.out::println);
        System.out.println("------------------------------------");
        //如果我们想要每个list里的元素，还需要一层foreach
        list.stream().map(item -> Arrays.stream(item.split(" "))).forEach(n -> {
            n.forEach(System.out::println);
        });
        //flatmap可以将字符串分割成各自的list之后直接合并成一个List
        //也就是flatmap可以将一个2维的集合转成1维度
        System.out.println("------------------------------------");
        list.stream().flatMap(item -> Arrays.stream(item.split(" "))).forEach(System.out::println);
        return;
    }
}
