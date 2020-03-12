package com.hurenjieee.code.guava;

import com.google.common.base.Preconditions;

/**
 * Preconditions提供静态方法来检查方法或构造函数
 * 被调用是否给定适当的参数。
 *
 * @author Jack
 * @date 2020/3/12 19:55
 */
public class PreconditionsTest {
    public static void main(String[] args) {
        PreconditionsTest preconditionsTest = new PreconditionsTest();
        preconditionsTest.sqrt(-1);
        preconditionsTest.sum(null, 1);
    }

    /**
     * 数学校验
     *
     * @param input
     * @return
     */
    public double sqrt(double input) throws IllegalArgumentException {
        Preconditions.checkArgument(input > 0.0, "Illegal Argument passed: Negative value %s.", input);
        return Math.sqrt(input);
    }


    /**
     * 空值校验
     *
     * @param a
     * @param b
     * @return
     * @throws NullPointerException
     */
    public int sum(Integer a, Integer b) throws NullPointerException {
        a = Preconditions.checkNotNull(a,
                "Illegal Argument passed: First parameter is Null.");
        b = Preconditions.checkNotNull(b,
                "Illegal Argument passed: Second parameter is Null.");
        return a + b;
    }


    /**
     * 数组长度校验
     *
     * @param input
     * @return
     */
    public int getValue(int input) {
        int[] data = {1, 2, 3, 4, 5};
        Preconditions.checkElementIndex(input, data.length,
                "Illegal Argument passed: Invalid index.");
        return 0;
    }


}
