package com.hurenjieee.code.collection

import scala.collection.mutable.ArrayBuffer

/**
  * 变长数组 ArrayBuffer
  * 长度不可变，元素可变
  */
object ArrayTestPlus {
  def main(args: Array[String]): Unit = {
    // 通过指定长度定义数组
    var a = new ArrayBuffer[String]()
    // 这里回编译失败
    a += "3"
    print(a(0))
    //数组越界
    //    print(a(1))
    // 下面的用法同样也可能触发数组越界
    //    a(0) = "0"
    //    a(1) = "1"
    //    a(2) = "2"
    a.foreach(println)
    // 用元素直接初始化数组
    var b = ArrayBuffer("a", "b", "c")
    b.foreach(println)

    println("1.----------------------")
    // 添加一个元素
    a += ("4")
    a.foreach(print)
    println("")
    println("2.----------------------")
    //删除一个元素
    a -= ("3")
    a.foreach(print)
    println()
    println("3.----------------------")
    //添加一个数组
    a ++= ArrayBuffer("5", "6", "7", "8")
    a.foreach(print)

    println("")
    println()
    println("循环----------------------")
    for (i <- a) print(i)

    println()
    for (i <- 0 to a.length - 1) print(a(i))

    println()
    for (i <- 0 until a.length) print(a(i))

    //0 until n ——生成一系列的数字，包含0，不包含n
    //0 to n    ——包含0，也包含n

    println()
    println("特殊操作----------------------")

    println(a.max)
    println(a.min)
//    println(a.sum)
    // 升序
    b = a.sorted.reverse
    for (i <- b) print(i)
    println()
    a.sorted
    for (i <- a) print(i)
    println()

  }
}
