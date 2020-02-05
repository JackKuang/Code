package com.hurenjieee.code;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author Jack
 * @date 2019/3/18 22:54
 */
public class Test {

    public static void main(String[] args) {
        String a = "00002205a2f5a7038948380ab364d8c3";
        System.out.println(StringUtils.substring(a, 0, 8));
        System.out.println(StringUtils.substring(a, 8, 16));
        System.out.println(StringUtils.substring(a, 16, 24));
        System.out.println(StringUtils.substring(a, 24, 32));
        System.out.println(Long.    valueOf("2205", 16));

    }
}
