package com.scala.tutorial.custom.category.functor

/**
  * Created by jiaming.shang on 8/31/17.
  */
trait Functor[F[_]] {
  def map[A,B](v:F[A])(f:A=>B):F[B]
}
