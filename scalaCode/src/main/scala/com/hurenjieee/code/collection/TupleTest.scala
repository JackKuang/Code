package com.hurenjieee.code.collection

/**
  * 元组可以用来包含一组不同类型的值。
  * 例如：姓名，年龄，性别，出生年月。
  * 元组的元素是不可变 的。
  */
object TupleTest {
  def main(args: Array[String]): Unit = {
    // 定义元组
    var tuple = (1,"2",3,"4")
    // 或者使用箭头，但是箭头只能包含两个，下面两者是一致的
    var tuple1 = 1->"2"
    var tuple2 = (1,"2")

    // 访问元组，使用 _1、_2、_3....
    println(tuple._1)
    // 编译报错，不能修改
    // tuple._4 = "test"

  }

}
