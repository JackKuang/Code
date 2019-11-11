package com.hurenjieee.code.collection

/**
  * 定长数组
  * 长度不可变，元素可变
  */
object ArrayTest {
  def main(args: Array[String]): Unit = {
    // 通过指定长度定义数组
    var a = new Array[String](3)
    // 这里回编译失败
    //    a += "3"
    a(0) = "0"
    a(1) = "1"
    a(2) = "2"
    a.foreach(println)
    // 用元素直接初始化数组
    var b = Array("a","b","c")
    b.foreach(println)
  }
}
