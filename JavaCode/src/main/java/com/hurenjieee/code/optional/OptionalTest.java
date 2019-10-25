package com.hurenjieee.code.optional;

import org.junit.Test;

import java.util.Optional;

/**
 * @author Jack
 * @date 2019/7/7 12:19
 */
public class OptionalTest {

    class Student{
        String name;

        public Student(String name) {
            this.name = name;
        }
    }

    /**
     * A container object which may or may not contain a non-null value.
     * @see java.util.Optional
     */
    @Test
    public void optionTest(){
        Student student = new Student("张三");
        System.out.println(Optional.of(student).orElse(new Student("李四")).name);
        //张三
        Student student2 = null;
        System.out.println(Optional.ofNullable(student2).orElse(new Student("李四")).name);
        //李四
        System.out.println(Optional.ofNullable(student).map(a -> a.name));
        //Optional[张三]
        // ** flat的内部方法出参不一致
        System.out.println(Optional.ofNullable(student).flatMap(a -> Optional.of(a.name)));
        //Optional[张三]
    }
}
