package com.scala.tutorial.custom.category.monad

import com.scala.tutorial.custom.category.monad.Free._

/**
  * Created by jiaming.shang on 9/19/17.
  */
sealed trait Free[F[_], +A] {
  def flatMap[B](f: A => Free[F, B]): Free[F, B] = {
    FlatMap[F, A,B](f, this)
  }

  def map[B](f: A => B): Free[F, B] = {
    Map[F, A,B](f, this)
  }

  def run(f: F~>Id): Id[A] = {
    this match {
      case Pure(v) => v
      case Map(f1, s) => f1(s.run(f))
      case FlatMap(f2, s) => f2(s.run(f)).run(f)
      case Mock(s) => f(s)
    }
  }
}


trait ~>[F[_],G[_]] {
  def apply[A](v:F[A]):G[A]
}


object Free {

  type Id[+A] = A

  private case class FlatMap[F[_],A,B](f: A => Free[F, B], s: Free[F, A]) extends Free[F, B]

  private case class Map[F[_], A, B](f: A => B, s: Free[F, A]) extends Free[F, B]

  private case class Pure[F[_], A](v: A) extends Free[F, A]

  private case class Mock[F[_], A](v: F[A]) extends Free[F, A]

  def pure[F[_], A](x: A): Free[F, A] = Pure[F, A](x)

  def lift[F[_], A](x: F[A]): Free[F, A] = Mock[F, A](x)
}

