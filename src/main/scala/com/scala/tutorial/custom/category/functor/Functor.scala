package com.scala.tutorial.custom.category.functor

/**
  * Created by jiaming.shang on 8/31/17.
  */
trait Functor[F[_]] {
  def map[A, B](v: F[A])(f: A => B): F[B]
}

object Functor {
  def compose[M[_] : Functor, N[_] : Functor]() = new Functor[({type l[A] = M[N[A]]})#l] {
    val mf = implicitly[Functor[M]]
    val nf = implicitly[Functor[N]]

    def map[A, B](v: M[N[A]])(f: A => B): M[N[B]] = {
      mf.map(v)((x: N[A]) => nf.map(x)(f))
    }
  }
}