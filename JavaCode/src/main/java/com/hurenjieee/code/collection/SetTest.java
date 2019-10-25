package com.hurenjieee.code.collection;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


public class SetTest {
	/*
	 * Set不允许重复元素，判断方法equal
	 * 
	 * HashSet
	 * 不能保证元素的排列顺序，顺序有可能发生变化
	 * 不是同步的
	 * 集合元素可以是null,但只能放入一个null
	 * 当向HashSet结合中存入一个元素时，HashSet会调用该对象的hashCode()方法来得到该对象的hashCode值，然后根据 hashCode值来决定该对象在HashSet中存储位置。
	 * HashSet集合判断两个元素相等的标准是两个对象通过equals方法比较相等，并且两个对象的hashCode()方法返回值相 等
	 * 
	 * LinkedHashSet
	 * LinkedHashSet集合同样是根据元素的hashCode值来决定元素的存储位置，但是它同时使用链表维护元素的次序。这样使得元素看起 来像是以插入顺序保存的，
	 * 也就是说，当遍历该集合时候，LinkedHashSet将会以元素的添加顺序访问集合的元素。
	 * LinkedHashSet在迭代访问Set中的全部元素时，性能比HashSet好，但是插入时性能稍微逊色于HashSet。
	 * 
	 * TreeSet
	 * TreeSet是SortedSet接口的唯一实现类，TreeSet可以确保集合元素处于排序状态。
	 * TreeSet支持两种排序方式，【自然排序 】和【定制排序】，其中自然排序为默认的排序方式。
	 * 自然排序是根据集合元素的大小，以升序排列，如果要定制排序，应该使用Comparator接口，实现 int compare(T o1,T o2)方法
	 * 
	*/
	public void test(){
		//Set<String> set = new HashSet<String>();
		//Set<String> set = new LinkedHashSet<String>();
		Set<String> set = new TreeSet<String>();
		for(int i = 0;i<100;i++){
			set.add("set"+i);
		}
		Iterator<String> iterator = set.iterator();
		while (iterator.hasNext()) {
			String s = iterator.next();
			System.out.println(s);
		}
	}
}
