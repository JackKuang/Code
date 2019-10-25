package com.hurenjieee.code.lambda;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author Jack
 * @date 2019/5/15 23:35
 */
public class LambdaTest {
    /**
     * 以前的方法：定义入参和执行规则。
     * 函数式编程：定义入参。
     *
     */
    public static void main(String[] args) {
        String str = "str";
        Integer i = 1;
        // 1.单行表达式
        TestService testService = (a, b) -> "单行表示式" + a + b;
        System.out.println(testService.test(str, i));
        // 单行表达式（入参类型）
        TestService testService2 = (String a, Integer b) -> "单行表示式" + a + b;
        System.out.println(testService2.test(str, i));
        // 2.多行表达式
        TestService testService3 = (a, b) -> {
            return "多行表示式" + a + b;
        };
        System.out.println(testService3.test(str, i));
        // 3.方法表达式
        TestService testService4 = new LambdaTest()::testMethod;
        System.out.println(testService4.test(str, i));
        // 4.静态方法表达式
        TestService testService5 = LambdaTest::testMethod2;
        System.out.println(testService5.test(str, i));
        // 5.构造函数表达式
        Supplier supplier = LambdaTest::new;
        // 6.第一个参数引用的表达式
        TestService testService6 = String::substring;
        System.out.println(testService6.test(str, i));
    }

    public String testMethod(String a, Integer b) {
        return "方法表达式" + a + b;

    }

    public static String testMethod2(String a, Integer b) {
        return "静态方法表达式" + a + b;
    }

    static List<String> list = Arrays.asList(new String[]{"1", "2", "3"});

    @FunctionalInterface
    interface TestService {
        String test(String s, Integer i);
    }
}
