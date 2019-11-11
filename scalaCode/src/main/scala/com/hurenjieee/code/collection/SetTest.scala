package com.hurenjieee.code.collection

/**
  * - Set是代表没有重复元素的集合。
  * - Set具备以下性质：
  *   - 1、元素不重复
  *   - 2、不保证插入顺序
  */
object SetTest {
  def main(args: Array[String]): Unit = {
    //创建一个空的不可变集
    var set1 = Set[String]()
    //给定元素来创建一个不可变集
    var set2 = Set[String]("1", "2", "3")
    // 创建方法类似于List
    println(set2.size)
    println()
    for (i <- set2) print(i)
    println()
    // 添加元素生成新的集合，原来的那个是不变的，只不过是创建了一个新的元素集合
    var set3 = set2 + "4"
    for (i <- set2) print(i)
    println("")
    for (i <- set3) print(i)

    println()
    println("----------------------")
    // 删除元素生成新的集合，原来的那个是不变的，只不过是创建了一个新的元素集合
    var set4 = set2 - "1"
    for (i <- set2) print(i)
    println("")
    for (i <- set4) print(i)

    println(set2 -- Set("2", "3"))
    println(set2 ++ Set("2", "3", "6", "7"))
    println(set2 & Set("2", "3", "6", "7"))


  }
}
