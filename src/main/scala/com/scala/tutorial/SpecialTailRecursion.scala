package com.scala.tutorial

object SpecialTailRecursion {

  sealed trait Action[A]

  case class Done[A](v: A) extends Action[A]

  case class Doing[A](x: Action[A], f: A => Action[A]) extends Action[A]

  def evaluate[A](x: Action[A]): A = {
    x match {
      case Done(v) => v
      case Doing(v, f) => v match {
        case Done(v1) => evaluate(f(v1))
        case Doing(v1, f1) => evaluate(Doing[A](v1, y => Doing(f1(y), f)))
      }
    }
  }

  def construct[A](f1: A => Action[A])(f2: A => Action[A]): A => Action[A] =
    x => Doing(f1(x), f2)

  def construct1[A](f1: A => Action[A])(f2: A => Action[A]): A => Action[A] =
    x => Doing(Done(x), y => Doing(f1(y), f2))

  def flatMap[A](v: Action[A])(f: A => Action[A]): Action[A] =
    Doing(v, f)

  def map[A](v: Action[A])(f: A => A): Action[A] =
    flatMap(v)(x => Done(f(x)))


  def main(arg: Array[String]) = {
    val stack: Action[() => Int] = Range(0, 100000).foldLeft[Action[() => Int]](Done(() => 0))((acc, ele) => {
      flatMap(acc)(f => Done(() => f() + 1))
    })


    val result = evaluate(stack)
    println(result())

    //    val func = Range(0, 100000).foldLeft[Int => Int](identity)((acc, ele) => {
    //      def f(v: Int): Int = v + ele
    //
    //      x: Int => f(acc(x))
    //    })
    //
    //    val result = func(1)
    //    println(result)
  }

}
