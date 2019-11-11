package com.hurenjieee.code.function

object MethodTest {

  def main(args: Array[String]): Unit = {
    println(add(1, 2))
    println(funtion_name(1, 2))
    println(method1(3))
    println(method2(1,4))
    println(method2(5))
    println(method3(1,2,3,4,5,6))
  }

  /**
    * 定义一个方法
    * - 参数列表的参数类型不能省略
    * - 返回值类型可以省略，由scala编译器自动推断，但是有一个例外，就是递归，递归必须要写返回值类型
    * - 返回值可以不写return，默认就是{}块表达式的值
    *
    * @param param1
    * @param param2
    */
  def funtion_name(param1: Int, param2: Int) = param1 * param2

  def add(a: Int, b: Int) = a + b


  /**
    * 为什么需要返回值，因为编译需要获取其返回值类型，那么在编译的时候就会一直循环下去，直到找到一个返回值
    *
    * @param x
    * @return
    */
  def method1(x: Int): Int = {
    if (x == 1) 1
    else x * method1(x - 1)
  }

  /**
    * 设定默认值
    * @param x
    * @param y
    * @return
    */
  def method2(x: Int = 1, y: Int = 2) = {
    x + y
  }

  def method3(x:Int *)={
    x.max
  }
}
