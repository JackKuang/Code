package com.hurenjieee.code.collection

import scala.collection.mutable.Map

/**
  * Map可以称之为映射。它是由键值对组成的集合。
  * scala当中的Map集合与java当中的Map类似，也是key，value对形式的。
  * 也区分不可变Map和可变Map
  */
object MapTestPlus {
  def main(args: Array[String]): Unit = {
    // 定义map 两者是一致的
    var map1 = Map(1 -> "1", 2 -> "2", 3 -> "3")
    var map2 = Map((1, "1"), (2, "2"), (3, "3"))

    println(map1(1))

    map1(1) = "5"

    println(map1.getOrElse(1, -1))
    println(map1.getOrElse(5, -1))

    map1.foreach(println(_))
    println()
    map1 += (4 -> "4")
    map1 -= 1
    println("遍历map")
    map1.foreach(println(_))
    println()
    println("遍历key")
    map1.keys.foreach(println(_))
    map1.keySet.foreach(println(_))
    println()
    println("遍历value")
    map1.values.foreach(println(_))

    println("遍历输出")
    for ((k, v) <- map1) println(k + "_" + v)
    for (k <- map1.keys) println(k + "_" + map1(k))
  }

}
