package com.hurenjieee.code.collection

import scala.collection.mutable.Set
/**
  * - Set是代表没有重复元素的集合。
  * - Set具备以下性质：
  *   - 1、元素不重复
  *   - 2、不保证插入顺序
  */
object SetTestPlus {
  def main(args: Array[String]): Unit = {
    var set2 = Set[String]("1", "2", "3")

    set2 += "4"
    println(set2)

    set2 += ("5","6")
    println(set2)

    set2 ++= Set("7","8")
    println(set2)

    set2 -="2"
    println(set2)

    set2 -=("3","4")
    println(set2)

    set2 --= Set("7","8")
    println(set2)

    set2.remove("1")
    println(set2)
  }
}
