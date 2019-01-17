package com.scala.tutorial

object PartialFunctionType {

  trait Effect[A]

  case object Effect1 extends Effect[Int]

  case object Effect2 extends Effect[String]

  def generate[X]: PartialFunction[Effect[X], Option[X]] = {
    case Effect1 => Option(1)
    case Effect2 => Option("Test")
  }

  def generate2[X](v: Effect[X]): Option[X] = v match {
    case Effect1 => Option(1)
    case Effect2 => Option("Test")
  }

}
