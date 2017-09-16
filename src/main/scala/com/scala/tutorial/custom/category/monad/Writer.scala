package com.scala.tutorial.custom.category.monad

import com.scala.tutorial.custom.category.monoid.Monoid

/**
  * Created by jiaming.shang on 9/16/17.
  */
case class Writer[A: Monoid, B](l: A, v: B) {

  val leftMonoid = implicitly[Monoid[A]]

  def map[C](f: B => C): Writer[A, C] = {
    Writer(l, f(v))
  }

  def flatMap[C](f: B => Writer[A, C]): Writer[A, C] = {
    val nextValue = f(v)
    Writer(leftMonoid.|+|(l, nextValue.l), nextValue.v)
  }

}
