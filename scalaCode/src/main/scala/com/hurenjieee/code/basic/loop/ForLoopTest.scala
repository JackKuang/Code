package com.hurenjieee.code.basic.loop

object ForLoopTest {
  def main(args: Array[String]): Unit = {
    //
    println("------------------to")
    // 两种用法都是一样的，生成给1,2,3,4,5,6,7,9,10p的数组
    var a = 1 to 10
    var b = 1.to(10,2)
    // 原以为这里会是和java一样的输出数组，但是其实并不是,是@
    // @scala.collection.immutable.Range.Inclusive
    println(a);
    for(a1 <- a){
      print(a1)
    }
    println(b);
    for(a1 <- b){
      print(a1)
    }
    println()
    println("------------------until")
    //
    var c = 1 until 10;
    println(c)
    for(a1 <- c){
      print(a1)
    }

    println()
    /**
      * 9*9乘法表
      */
    println("------------------9*9")
    for(i <- 1 to 9 ; j <- 1 to i){
      print(i+"*"+j+"="+i*j+"\t")
        if(i == j){
          println()
        }
    }

    /**
      * 多阶段循环
      */
    println()
    println("------------------多重循环")
    for(i <- 1 to 3;j <- 1 to 3 ;k <- 1 to 3){
      println(i+"_"+j+"_"+k)
    }

    /**
      * if守卫
      * 循环体中的判断条件
      */
    println()
    println("------------------if守卫")
    for(i <- 1 to 100 if i % 5 == 0){
      print(i + " ");
    }


    /**
      * for循环推导式
      */
    println()
    println("------------------for循环推导式")
    var v1 = for(i <- 1 to 10 ) yield  i * 10
    print(v1)

    /**
      * 当然，我们也可以使用函数式编程来处理类似的逻辑
      */
    println()
    println("------------------循环推导的另一种实现")
    var v2 = (1.to(10)).map(i => i*10)
    print(v2)

  }
}
