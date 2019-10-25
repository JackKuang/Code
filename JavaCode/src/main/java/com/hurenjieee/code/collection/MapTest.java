package com.hurenjieee.code.collection;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;


public class MapTest {
	/*
	 * Map主要用于存储健值对，根据键得到值，因此不允许键重复（重复了覆盖了），但允许值重复。
	 * 
	 * HashMap
	 * 是一个最常用的Map，它根据键的HashCode 值存储数据，根据键可以直接获取它的值，具有很快的访问速度。遍历时，取得数据的顺序是完全随机的。
	 * 
	 * Hashtable与 HashMap类似，它继承自Dictionary类。不同的是：它不允许记录的键或者值为空；
	 * 它支持线程的同步（即任一时刻只有一个线程能写Hashtable），因此也导致了 Hashtable在写入时会比较慢。
	 * 
	 * LinkedHashMap
	 * LinkedHashMap保存了记录的插入顺序，在用Iterator遍历LinkedHashMap时，先得到的记录肯定是先插入的。也可以在构造时带参数，按照应用次数排序。
	 * 
	 * TreeMap
	 * 
	 * TreeMap实现SortMap接口，能够把它保存的记录根据键排序。
	 * 默认是按【键值】的升序排序，也可以指定排序的比较器，当用Iterator 遍历TreeMap时，得到的记录是排过序的。
	 * */
	public void test(){
		//Map<String,String> map = new HashMap<String,String>();
		//Map<String,String> map = new LinkedHashMap<String,String>();
		Map<String,String> map = new TreeMap<String,String>();
		for(int i = 0;i<100;i++){
			map.put("key"+i, "value"+i);
		}
		Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			System.out.println(entry.getKey()+":"+entry.getValue());
		}
	}
}
