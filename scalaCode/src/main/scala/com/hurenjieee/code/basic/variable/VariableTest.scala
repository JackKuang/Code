package com.hurenjieee.code.basic.variable

//val 定义的是**不可重新赋值**的变量(值不可修改)
//var 定义的是**可重新赋值**的变量(值可以修改)
object VariableTest {

  def main(args: Array[String]): Unit = {
    var a = 1;
    a = 2;
    val b = 1;
    // 修改b变量会报错
    //    b = 2;

    var c: Int = 1;
    // 定义之后默认其实给予了变量类型,编译就已经不能成立
    //    c = "2";

    lazy val d = 1;
    // 懒加载,理解为,默认无赋值,只有用到的时候会赋值
  }

}
