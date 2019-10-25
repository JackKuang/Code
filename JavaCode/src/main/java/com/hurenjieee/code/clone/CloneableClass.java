package com.hurenjieee.code.clone;

import java.io.Serializable;

class CloneableClass implements Cloneable,Serializable {

	private static final long serialVersionUID = 4951608239627949555L;
	public Test data1 = null;
	public double data2 = 0;
	public String data3 = null;
	public StringBuffer data4 = null;
	
	public CloneableClass() {
		super();
	}

	public CloneableClass(Test data1, double data2, String data3, StringBuffer data4) {
		this.data1 = data1;
		this.data2 = data2;
		this.data3 = data3;
		this.data4 = data4;
	}// constructor

	/**
	 * 用于显示对象中各字段的值
	 */
	public void show() {
		System.out.println("data1 = " + data1.userData);
		System.out.println("data2 = " + data2);
		System.out.println("data3 = " + data3);
		System.out.println("data4 = " + data4);
	}// show

	/**
	 * 重写clone()方法为public类型，并调用Object的本地clone()方法，实现浅拷贝功能
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		/* 深拷贝
		Test data1 = new Test(this.data1.userData);
		double data2 = this.data2;
		String data3 = new String(this.data3);
		StringBuffer data4 = new StringBuffer(this.data4.toString());

		CloneableClass copy = new CloneableClass(data1, data2, data3, data4);
		return copy;*/
		// 浅拷贝
		return super.clone();
	}// clone

	public Test getData1() {
		return data1;
	}

	public void setData1(Test data1) {
		this.data1 = data1;
	}

	public double getData2() {
		return data2;
	}

	public void setData2(double data2) {
		this.data2 = data2;
	}

	public String getData3() {
		return data3;
	}

	public void setData3(String data3) {
		this.data3 = data3;
	}

	public StringBuffer getData4() {
		return data4;
	}

	public void setData4(StringBuffer data4) {
		this.data4 = data4;
	}
	
	
	

}/* CloneableClass */