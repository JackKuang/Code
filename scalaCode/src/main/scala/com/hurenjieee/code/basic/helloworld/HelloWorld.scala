package com.hurenjieee.code.basic.helloworld

//类：class
//单例对象:object
//scala和Java一样，如果要运行一个程序，必须有一个main方法。
//而在Java中main方法是静态的，而在scala中没有静态方法。
//在scala中，这个main方法必须放在一个object中
object HelloWorld {

  def main(args: Array[String]): Unit = {
    println("hello world")
  }
}
