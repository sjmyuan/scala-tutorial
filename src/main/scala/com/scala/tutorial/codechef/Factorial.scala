package com.scala.tutorial.codechef

/**
  * How to do the big number computation?
  * Do we have to do this? is there any workaround?
  * 75*2=150
  * 75*4=300
  *
  * deconstruct the number to small 5?
  */
object Factorial {

  def getDataFromConsole(): List[Long] = {
    val size = scala.io.StdIn.readLine().toInt
    Range(0, size).map(x => {
      scala.io.StdIn.readLine().toLong
    }).toList
  }

  def factorialZerors(n: Long): Long = {
    var acc: Long = 0
    var stack: Long = 5
    while (stack <= n) {
      val total: Long = n / stack
      acc += total
      stack = stack * 5
    }
    return acc
  }

  def main(args: Array[String]) = {
    val input = getDataFromConsole()
    //    val start = System.currentTimeMillis()
    input.map(factorialZerors).foreach(println(_))
    //    val end = System.currentTimeMillis()
    //    println(s"Time: ${end-start}")
  }
}
