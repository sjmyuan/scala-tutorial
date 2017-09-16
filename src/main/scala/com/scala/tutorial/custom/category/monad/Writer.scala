package com.scala.tutorial.custom.category.monad

import com.scala.tutorial.custom.category.monoid._

/**
  * Created by jiaming.shang on 9/16/17.
  */
case class Writer[A: Monoid, B](l: A, v: B) {

  def map[C](f: B => C): Writer[A, C] = {
    Writer(l, f(v))
  }

  def flatMap[C](f: B => Writer[A, C]): Writer[A, C] = {
    val nextValue = f(v)
    Writer(l |+| nextValue.l, nextValue.v)
  }

}

object Writer{
  def tell[A:Monoid](l:A)=Writer(l,Unit)
  def value[A:Monoid,B](v:B)=Writer(implicitly[Monoid[A]].id,v)
}
