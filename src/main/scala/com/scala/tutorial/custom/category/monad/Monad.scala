package com.scala.tutorial.custom.category.monad

import com.scala.tutorial.custom.category.functor.Functor

/**
  * Created by jiaming.shang on 8/31/17.
  */
trait Monad[+A] extends Functor[A]{
  def flatMap[B](f:A=>Monad[B]):Monad[B]
}
