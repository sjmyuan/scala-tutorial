package com.scala.tutorial.custom.category.applicative

import com.scala.tutorial.custom.category.functor.Functor

/**
  * Created by jiaming.shang on 10/24/17.
  */
trait Applicative[F[_]] extends Functor[F] {
  def ap[A, B](v: F[A])(f: F[A => B]): F[B]

  def point[A](a: A): F[A]

  override final def map[A, B](v: F[A])(f: A => B) =
    ap(v)(point(f))
}

object Applicative {
  def compose[M[_] : Applicative, N[_] : Applicative]() = new Applicative[({type l[A] = M[N[A]]})#l] {
    val ma = implicitly[Applicative[M]]
    val na = implicitly[Applicative[N]]

    override def point[A](a: A): M[N[A]] = ma.point(na.point(a))

    override def ap[A, B](v: M[N[A]])(f: M[N[(A) => B]]): M[N[B]] = {
      def liftA2[X, Y, Z](f: X => Y => Z, a: M[X], b: M[Y]): M[Z] =
        ma.ap(b)(ma.map(a)(f))
      liftA2((ff: N[A => B]) => (aa: N[A]) => na.ap(aa)(ff), f, v)
    }
  }
}
