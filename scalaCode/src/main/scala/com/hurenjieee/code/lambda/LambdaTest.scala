package com.hurenjieee.code.lambda

import scala.collection.mutable.ListBuffer

object LambdaTest {
  def main(args: Array[String]): Unit = {
    var a = ListBuffer(1, 2, 3, 4, 5)

    /**
      * 三种用法一致
      */
    println("foreach---------------------")
    a.foreach((i: Int) => print(i))
    println()
    a.foreach(i => print(i))
    println()
    a.foreach(print(_))
    println()

    /**
      * 同样，三种用法一致
      */
    println("map---------------------")
    println()
    a.map((i: Int) => i * 10).foreach(print)
    println()
    a.map(i => i * 10).foreach(print)
    println()
    a.map(_ * 10).foreach(print)

    /**
      * flatmap映射扁平化
      *
      */
    println("flatMap---------------------")
    // =>("1”,"2”,"3”,"4”,"5”,"6”,"7”)
    var strList = ListBuffer("1 2","3 4 5 6","7")
    strList.flatMap(x => x.split(" "))
    strList.flatMap(_.split(" "))
    // 不推荐下面的用法
    strList.map(_.split(" ")).flatten

  }
}
