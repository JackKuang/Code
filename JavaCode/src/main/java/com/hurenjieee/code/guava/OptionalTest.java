package com.hurenjieee.code.guava;

import com.google.common.base.Optional;

/**
 * https://www.yiibai.com/guava/guava_preconditions_class.html
 * Optional用于包含非空对象的不可变对象
 * @author Jack
 * @date 2020/3/12 19:50
 */
public class OptionalTest {
  public static void main(String args[]) {
    OptionalTest optionalTest = new OptionalTest();

    Integer value1 = null;
    Integer value2 = new Integer(10);
    // 允许参数为空
    Optional<Integer> a = Optional.fromNullable(value1);
    // 如果参数为空则会抛出空指针异常
    //      Optional<Integer> b = Optional.of(value1);
    Optional<Integer> b = Optional.of(value2);
    System.out.println(optionalTest.sum(a, b));
  }

  public Integer sum(Optional<Integer> a, Optional<Integer> b) {
    System.out.println("First parameter is present: " + a.isPresent());
    System.out.println("Second parameter is present: " + b.isPresent());
    Integer value1 = a.or(new Integer(0));
    Integer value2 = b.get();
    return value1 + value2;
  }
}
