package com.scala.tutorial.custom.category.monad

/**
  * Created by jiaming.shang on 9/18/17.
  */
trait Free[S, +V] {
  def flatMap[A](f: Any => Free[S, A]): Free[S, A] = {
    FlatMap[S, A](f, this)
  }

  def map[A](f: Any => A): Free[S, A] = {
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

case class FlatMap[S, A](f: Any => Free[S, A], s: Free[S, Any]) extends Free[S, A]

case class Map[S, A](f: Any => A, s: Free[S, Any]) extends Free[S, A]

case class Pure[S, V](v: V) extends Free[S, V]
case class Mock[S,V](s:S) extends Free[S,V]


object Free {
  def pure[S, V](x: V): Free[S, V] = Pure[S, V](x)
  def lift[S,V](s:S):Free[S,V]= Mock[S,V](s)
}
