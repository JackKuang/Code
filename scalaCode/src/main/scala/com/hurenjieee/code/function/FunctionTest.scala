package com.hurenjieee.code.function

/**
  * 方法和函数的区别：
  * 1. 方法是隶属于类或者对象的，在运行时，它是加载到JVM的方法区中
  * 2. 可以将函数对象赋值给一个变量，在运行时，它是加载到JVM的堆内存中
  * 3. - ==函数是一个对象，继承自FunctionN==，函数对象有apply，curried，toString，tupled这些方法，而方法则没有
  */
object FunctionTest {
  def main(args: Array[String]): Unit = {
    val add = (x: Int, y: Int) => x + y
    println(add(1, 2))
    val mult = method _
    println(method(2, 3))
    println(mult(2, 3))
  }

  def method(x: Int, y: Int): Int = {
    x * y
  }
}
