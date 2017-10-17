package com.scala.tutorial.custom.category.monad

import com.scala.tutorial.custom.category.functor.Functor
import com.scala.tutorial.custom.category.monad.Free2.{Id, ~>}

/**
  * Created by jiaming.shang on 10/17/17.
  */
sealed trait Free2[F[_], A] {
  def map[B](f: A => B)(implicit functor: Functor[F]): Free2[F, B] = {
    this match {
      case Free2.Pure(v) => Free2.Pure(f(v))
      case Free2.Impure(v) => Free2.Impure[F, B](functor.map(v)(_.map(f)))
    }
  }

  def flatMap[B](f: A => Free2[F, B])(implicit functor: Functor[F]): Free2[F, B] = {
    this match {
      case Free2.Pure(v) => f(v)
      case Free2.Impure(v) => Free2.Impure[F, B](
        functor.map[Free2[F, A], Free2[F, B]](v)(x => x.flatMap(f)))
    }
  }

  def run(f: F ~> Id): Id[A] = {
    this match {
      case Free2.Pure(v) => v
      case Free2.Impure(v) => f(v).run(f)
    }
  }
}

object Free2 {

  trait ~>[F[_], G[_]] {
    def apply[A](v: F[A]): G[A]
  }

  type Id[+A] = A

  final case class Pure[F[_], A](v: A) extends Free2[F, A]

  final case class Impure[F[_], A](v: F[Free2[F, A]]) extends Free2[F, A]

  def pure[F[_], A](a: A): Free2[F, A] = Pure(a)

  def lift[F[_], A](fa: F[A])(implicit functor: Functor[F]): Free2[F, A]
  = Impure[F, A](functor.map[A, Free2[F, A]](fa)(pure(_)))

}
