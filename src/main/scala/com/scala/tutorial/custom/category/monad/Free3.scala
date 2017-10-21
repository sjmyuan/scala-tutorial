package com.scala.tutorial.custom.category.monad

import com.scala.tutorial.custom.category.functor.Functor
import com.scala.tutorial.custom.category.monad.Free3.{Id, ~>}

/**
  * Created by jiaming.shang on 10/17/17.
  */
sealed trait Free3[F[_], A] {
  def map[B](f: A => B): Free3[F, B]

  def flatMap[B](f: A => Free3[F, B]): Free3[F, B]

  def run(f: F ~> Id): Id[A]

}

object Free3 {

  trait ~>[F[_], G[_]] {
    def apply[A](v: F[A]): G[A]
  }

  type Id[A] = A

  final case class Pure[F[_], A](v: A) extends Free3[F, A] {
    override def map[B](f: (A) => B): Free3[F, B] = Pure(f(v))

    override def flatMap[B](f: (A) => Free3[F, B]): Free3[F, B] = f(v)

    override def run(f: F ~> Id): Id[A] = v
  }

  final case class Impure[F[_], A, B](v: F[A], f: A => Free3[F, B]) extends Free3[F, B] {
    override def map[C](f1: (B) => C): Free3[F, C] = Impure[F, A, C](v, (x: A) => f(x).map(f1))

    override def flatMap[C](f1: (B) => Free3[F, C]): Free3[F, C] = Impure[F, A, C](v, (x: A) => f(x).flatMap(f1))

    override def run(f1: F ~> Id): Id[B] = f(f1(v)).run(f1)
  }

  def pure[F[_], A](a: A): Free3[F, A] = Pure(a)

  def lift[F[_], A](fa: F[A]): Free3[F, A]
  = Impure[F, A, A](fa, x => pure(x))
}
