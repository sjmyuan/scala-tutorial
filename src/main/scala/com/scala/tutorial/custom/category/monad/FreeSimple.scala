package com.scala.tutorial.custom.category.monad

import com.scala.tutorial.custom.category.monad.FreeSimple.{FlatMap, Map, Mock, Pure}

/**
  * Created by jiaming.shang on 9/18/17.
  */
sealed trait FreeSimple[S, +V] {
  def flatMap[A](f: Any => FreeSimple[S, A]): FreeSimple[S, A] = {
    FlatMap[S, A](f, this)
  }

  def map[A](f: Any => A): FreeSimple[S, A] = {
    Map[S, A](f, this)
  }

  def run(f: S => Any): Any = {
    this match {
      case Pure(v) => v
      case Map(f1, s) => f1(s.run(f))
      case FlatMap(f2, s) => f2(s.run(f)).run(f)
      case Mock(s) => f(s)
    }
  }
}


object FreeSimple {

  private case class FlatMap[S, A](f: Any => FreeSimple[S, A], s: FreeSimple[S, Any]) extends FreeSimple[S, A]

  private case class Map[S, A](f: Any => A, s: FreeSimple[S, Any]) extends FreeSimple[S, A]

  private case class Pure[S, V](v: V) extends FreeSimple[S, V]

  private case class Mock[S, V](s: S) extends FreeSimple[S, V]

  def pure[S, V](x: V): FreeSimple[S, V] = Pure[S, V](x)

  def lift[S, V](s: S): FreeSimple[S, V] = Mock[S, V](s)
}
