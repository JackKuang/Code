package com.hurenjieee.code.collection

object ListTest {
  def main(args: Array[String]): Unit = {
    // 创建一个不可变的列表
    var list1 = List(1, 2, 3, 4)
    //使用 Nil 创建一个不可变的空列表
    var list2 = Nil
    //使用 :: 方法创建一个不可变列表
    var list3 = 1 :: 2 :: 3 :: Nil

  }
}
