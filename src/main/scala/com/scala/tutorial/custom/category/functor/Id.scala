package com.scala.tutorial.custom.category.functor

/**
  * Created by jiaming.shang on 8/31/17.
  */
object Id{
  type Id[A]=A
  class IdFunctor extends Functor[Id]{
    override def map[A,B](v:Id[A])(f: (A) => B): Id[B] = f(v)
  }
}
