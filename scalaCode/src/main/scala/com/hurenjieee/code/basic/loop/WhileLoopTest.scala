package com.hurenjieee.code.basic.loop

object WhileLoopTest {

  def main(args: Array[String]): Unit = {
    var i = 1;
    var j = 1;
    while (j < 10) {
      print(i + "*" + j + "=" + i * j + "\t")
      if (j == i) {
        println()
        j += 1
        i = 1
      } else {
        i += 1
      }
    }
  }
}
