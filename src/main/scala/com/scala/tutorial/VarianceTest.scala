package com.scala.tutorial

trait VarianceTest[+T <: Int] {
 def hello = println("hello")
}

case class ChildVariance[+T <: Int](v:T) extends VarianceTest[T]