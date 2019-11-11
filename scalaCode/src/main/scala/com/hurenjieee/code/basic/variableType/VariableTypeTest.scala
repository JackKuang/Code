package com.hurenjieee.code.basic.variableType

//Scala中,变量类型与java对应关系
/**
  *
  * Byte 8位带符号整数
  * Short 16位带符号整数
  * Int 32位带符号整数
  * Long 64位带符号整数
  * Char 16位无符号
  * Unicode 字符
  * String Char类型的序列（字符串）
  * Float 32位单精度浮点数
  * Double 64位双精度浮点数
  * Boolean true或false
  * --------------------------------------------
  * 1. scala中所有的类型都使用大写字母开头
  * 2. 整形使用Int而不是Integer
  * 3. scala中定义变量可以不写类型，让scala编译器自动推断
  */
object VariableTypeTest {
  def main(args: Array[String]): Unit = {
    var a: Int = 1;
    var b = 1;
    print(a == b)
  }
}
