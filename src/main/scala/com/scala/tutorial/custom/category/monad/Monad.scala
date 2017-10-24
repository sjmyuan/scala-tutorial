package com.scala.tutorial.custom.category.monad

import com.scala.tutorial.custom.category.applicative.Applicative

/**
  * Created by jiaming.shang on 8/31/17.
  */
trait Monad[F[_]] extends Applicative[F] {
  def flatMap[A, B](v: F[A])(f: A => F[B]): F[B]

  override final def ap[A, B](v: F[A])(f: F[A => B]) =
    flatMap(f)((x: A => B) => map(v)(x))
}

object Monad {
  def compose[M[_] : Monad, N[_] : Monad]() = new Monad[({type l[A] = M[N[A]]})#l] {
    val mm = implicitly[Monad[M]]
    val nm = implicitly[Monad[N]]

    override def point[A](a: A): M[N[A]] = mm.point(nm.point(a))

    override def flatMap[A, B](v: M[N[A]])(f: (A) => M[N[B]]): M[N[B]] = ???
  }
}

