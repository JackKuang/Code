package com.hurenjieee.code.clone;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.SerializationUtils;

public class CloneableDemo {

	public static void main(String[] args) throws CloneNotSupportedException {
		Test test1 = new Test("original data");
		StringBuffer strBuf = new StringBuffer("origin data");

		CloneableClass org = new CloneableClass(test1, 1.0, "original", strBuf);
		CloneableClass copy = null;
		Object objTemp = org.clone();
		if (objTemp instanceof CloneableClass) {
			copy = (CloneableClass) objTemp;
		} // if

		System.out.println("copy == original? " + (copy == org));
		System.out.println();
		System.out.println("data of original:");
		org.show();
		System.out.println();
		System.out.println("data of copy:");
		copy.show();

		System.out.println();
		System.out.println("org.data1 == copy.data1? " + (org.data1 == copy.data1));
		System.out.println("org.data2 == copy.data2? " + (org.data2 == copy.data2));
		System.out.println("org.data3 == copy.data3? " + (org.data3 == copy.data3));
		System.out.println("org.data4 == copy.data4? " + (org.data4 == copy.data4));

		/**
		 * 从执行结果上来看copy == org为false说明copy和org是两个不同的对象实例（对于引用类型的比变量，
		 * “==”判断的是对象地址是否相同，也就是是不是指向了同一个对象），
		 * 但是他们字段的值却是相同的。copy和org各字段的“==”判断全为true也说明本地Object本地clone()
		 * 对实例引用型字段进行的是浅拷贝。
		 * 
		 * 那么浅拷贝会造成什么样的后果呢？由于浅拷贝仅将字段的引用值复制给了新的字段，但是却并没有创建新的相应对象，
		 * 也就是说copy和org中的两个字段都指向了同一个对象实例。
		 * 这样，我们对copy中各字段所指向对象的属性进行了修改，org中的成员对象也会随之改变，客户端代码和运行结果如下所示。
		 *
		 * 
		 */

		System.out.println("-----------------------------------------------");
		// 修改copy中各字段指向对象的属性
		copy.data1.userData = "Copy data";
		copy.data2 = 2.0;
		copy.data3 = "Copy";
		copy.data4.replace(0, copy.data4.length(), "Copy data");

		System.out.println();
		System.out.println("After modify, data of original:");
		org.show();
		System.out.println();
		System.out.println("After modify, data of copy:");
		copy.show();

		/**
		 * 利用common-lang序列化工具clone工具【推荐】
		 */
		System.out.println("-----------------------------------------------");
		copy = SerializationUtils.clone(org);
		// 修改copy中各字段指向对象的属性
		copy.data1.userData = "Copy data";
		copy.data2 = 2.0;
		copy.data3 = "Copy";
		copy.data4.replace(0, copy.data4.length(), "Copy data");

		System.out.println();
		System.out.println("After modify, data of original:");
		org.show();
		System.out.println();
		System.out.println("After modify, data of copy:");
		copy.show();

		/**
		 * 利用common-beanutail的BeanUtils工具clone工具（要求较多不推荐）
		 */
		System.out.println("-----------------------------------------------");
		try {
			copy = (CloneableClass) BeanUtils.cloneBean(org);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// main

}/* CloneableDemo */