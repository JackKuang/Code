package com.hurenjieee.code.basic.block

//后期一个方法的返回值不需要加上return,把要返回的结果放在方法的最后一行就可以了
object BlockTest {
  def main(args: Array[String]): Unit = {

    val x = 0
    //result的值就是块表达式的结果
    val result = {
      val y = x + 10
      val z = y + "-hello"
      val m = z + "-test"
      "over"
    }
    print(result);
  }
}
