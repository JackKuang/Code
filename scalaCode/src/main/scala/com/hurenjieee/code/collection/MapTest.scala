package com.hurenjieee.code.collection

/**
  * Map可以称之为映射。它是由键值对组成的集合。
  * scala当中的Map集合与java当中的Map类似，也是key，value对形式的。
  * 也区分不可变Map和可变Map
  */
object MapTest {
  def main(args: Array[String]): Unit = {
    // 定义map 两者是一致的
    var map1 = Map(1 -> "1", 2 -> "2", 3 -> "3")
    var map2 = Map((1, "1"), (2, "2"), (3, "3"))

    println(map1(1))



    //编译会报错
    //    map1(1)= "3"

  }

}
