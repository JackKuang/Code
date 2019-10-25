package com.hurenjieee.code.collection;

import java.util.*;


public class ListTest {

    /*
     * 针对ArrayList和LinkedList使用
     * 1.ArrayList是实现了基于动态数组的数据结构，LinkedList基于链表的数据结构。
     * 2.对于随机访问get和set，ArrayList觉得优于LinkedList，因为LinkedList要移动指针。
     * 3.对于新增和删除操作add和remove，LinedList比较占优势，因为ArrayList要移动数据。
     *
     * 采用ArrayList对随机访问比较快，而for循环中的get()方法，采用的即是随机访问的方法，因此在ArrayList里，for循环较快
     * 采用LinkedList则是顺序访问比较快，iterator中的next()方法，采用的即是顺序访问的方法，因此在LinkedList里，
     * 使用iterator较快 测试对比发现，LinkedList传统For循环特别慢
     *
     *
     * ArrayList用于随机访问，LinkedList用于频繁增删
     * ArratList遍历用for，LinkedList用iterator
     *
     */
    public void testArrayList() {
        List<String> list = new ArrayList<String>();
        Long start, end;
        int count = 1000000;
        start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            String obj = new String("s" + i);
            list.add(obj);
        }
        end = System.currentTimeMillis();
        System.out.println("ArrayList插入时间:" + (end - start));
        start = System.currentTimeMillis();
        for (int j = 0; j < list.size(); j++) {
            list.get(j);
        }
        end = System.currentTimeMillis();
        System.out.println("传统for循环：" + (end - start));

        start = System.currentTimeMillis();
        for (String s : list) {
        }
        end = System.currentTimeMillis();
        System.out.println("forEach循环：" + (end - start));

        start = System.currentTimeMillis();
        ListIterator<String> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            String s = listIterator.next();
        }
        end = System.currentTimeMillis();
        System.out.println("interator循环：" + (end - start));

        List<String> list2 = new LinkedList<String>();
        start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            String obj = new String("s" + i);
            list2.add(obj);
        }
        end = System.currentTimeMillis();

        System.out.println("LinkedList插入时间:" + (end - start));
        start = System.currentTimeMillis();
        for (int j = 0; j < list2.size(); j++) {
            list2.get(j);
        }
        end = System.currentTimeMillis();
        System.out.println("传统for循环：" + (end - start));

        start = System.currentTimeMillis();
        for (String s : list2) {
        }
        end = System.currentTimeMillis();
        System.out.println("forEach循环：" + (end - start));

        start = System.currentTimeMillis();
        ListIterator<String> listIterator2 = list2.listIterator();
        while (listIterator2.hasNext()) {
            String s = listIterator2.next();
        }
        end = System.currentTimeMillis();
        System.out.println("interator循环：" + (end - start));


        //LinkedList是线程不安全的，若需要线程安全则需要调用静态类Collections类中的synchronizedList方法
        List linkedList= Collections.synchronizedList(new LinkedList());
    }

    /**
     * List集合
     */
    void testList() {
        // TODO Auto-generated method stub
        List<Integer> list1 = new ArrayList<Integer>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        list1.add(4);

        List<Integer> list2 = new ArrayList<Integer>();
        list2.add(2);
        list2.add(3);
        list2.add(4);
        list2.add(5);
        // 并集
        list1.addAll(list2);
        // 交集
        list1.retainAll(list2);
        // 差集
        list1.removeAll(list2);
        // 无重复并集
        list2.removeAll(list1);
        list1.addAll(list2);

        for (Integer i : list1) {
            System.out.println(i);
        }
    }

}
