package com.hurenjieee.code.collection

import scala.collection.mutable.ListBuffer

object ListTestPlus {
  def main(args: Array[String]): Unit = {
    // 创建一个可变的列表
    var list1 = ListBuffer(1, 2, 3, 4)
    // 创建一个空的可变列表
    var list2 = ListBuffer[Int]()

    //获取第一个元素
    println(list1(0))
    println(list1.head)
    //获取除了第一个元素外其他元素组成的列表
    println(list1.tail)

    println(list1 += 5)

    println(list1 ++= List(6, 7))
    println(list1 ++= ListBuffer(8, 9))

    println(list1 -= 5)

    println(list1 --= List(6, 7))
    println(list1 --= ListBuffer(8, 9))

    //可变的列表转为不可变列表
    list1.toList

    //可变的列表转为不可变数组
    list1.toArray


  }
}
