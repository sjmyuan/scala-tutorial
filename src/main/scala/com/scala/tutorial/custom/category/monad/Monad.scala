package com.scala.tutorial.custom.category.monad

import com.scala.tutorial.custom.category.functor.Functor

/**
  * Created by jiaming.shang on 8/31/17.
  */
trait Monad[F[_]] {
  def flatMap[A,B](v:F[A])(f:A=>F[B]):F[B]
}
