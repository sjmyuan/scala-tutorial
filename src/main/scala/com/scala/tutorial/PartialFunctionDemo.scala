package com.scala.tutorial

object PartialFunctionDemo {

  val function1: PartialFunction[Int, Int] = {
    case 1 => 2
    case 2 => 3
  }

  class Function2 extends PartialFunction[Int, Int] {
    override def isDefinedAt(x: Int): Boolean = (x == 1 || x == 2)

    override def apply(v1: Int): Int = {
      if (v1 == 1) 2
      else 3
    }
  }

}
