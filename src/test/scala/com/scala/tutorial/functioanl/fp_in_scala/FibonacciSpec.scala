package com.scala.tutorial.functioanl.fp_in_scala

import org.scalatest._

import scala.annotation.tailrec

/**
  * Created by jiaming.shang on 8/14/17.
  */
class FibonacciSpec extends FunSpec with Matchers {

  def fibonacci(n: Int): Int = {
    @tailrec
    def calculate(index: Int, first: Int, second: Int): Int = {
      if (index == 1) second
      else calculate(index - 1, second, first + second)
    }

    if (n == 0) 0
    else if (n == 1) 1
    else calculate(n, 0, 1)
  }

  it("should generate the correct fibonacci series"){
    val fibonacciList = List(0,1,2,3,4,5,6,7).map(fibonacci(_))
    fibonacciList should be(List(0,1,1,2,3,5,8,13))
  }

}
