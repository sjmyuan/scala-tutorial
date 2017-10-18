package com.scala.tutorial.custom.category.monad

import com.scala.tutorial.custom.category.functor.Functor
import com.scala.tutorial.custom.category.monad.Free3.{Id, ~>}

/**
  * Created by jiaming.shang on 10/17/17.
  */
sealed trait Free3[F[_], A] {
  def map[B](f: A => B): Free3[F, B] = {
    this match {
      case Free3.Pure(v) => Free3.Pure(f(v))
      case Free3.Impure(v,f1) => Free3.Impure(v,f1.andThen(_.map(f))).asInstanceOf[Free3[F,B]]
    }
  }

  def flatMap[B](f: A => Free3[F, B]): Free3[F, B] = {
    this match {
      case Free3.Pure(v) => f(v)
      case Free3.Impure(v,f1) =>  Free3.Impure(v,f1.andThen(_.flatMap(f))).asInstanceOf[Free3[F,B]]
    }
  }

  def run(f: F ~> Id): Id[A] = {
    this match {
      case Free3.Pure(v) => v
      case Free3.Impure(v,f1) => f1(f(v)).run(f)
    }
  }
}

object Free3 {

  trait ~>[F[_], G[_]] {
    def apply[A](v: F[A]): G[A]
  }

  type Id[+A] = A

  final case class Pure[F[_], A](v: A) extends Free3[F, A]

  final case class Impure[F[_], A, B](v: F[A], f: A => Free3[F, B]) extends Free3[F, B]

  def pure[F[_], A](a: A): Free3[F, A] = Pure(a)

  def lift[F[_], A](fa: F[A]): Free3[F, A]
  = Impure[F, A, A](fa, x => pure(x))
}
