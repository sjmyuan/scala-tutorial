package com.scala.tutorial.custom.category.functor

/**
  * Created by jiaming.shang on 8/31/17.
  */
case class Id[A](v:A) extends Functor[A]{
  override def map[B](f: (A) => B): Functor[B] = Id(f(v))
}
