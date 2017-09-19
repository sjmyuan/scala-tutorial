package com.scala.tutorial.custom.category.monad

/**
  * Created by jiaming.shang on 9/18/17.
  */
trait Free[S, V] {
  def flatMap[A](f: V => Free[S, A]): Free[S, A] = {
    FlatMap(f, this)
  }

  def map[A](f: V => A): Free[S, A] = {
    Map(f, this)
  }

  def run(f: S => V): V = {
    this match {
      case Pure(v) => v
      case Map(mapf, s) => mapf(s.run(f))
      case FlatMap(flatMapf, s) => flatMapf(s).run(f)
    }
  }
}

case class FlatMap[S, V, A](f: V => Free[S, A], s: Free[S, V]) extends Free[S, A]

case class Map[S, V, A](f: V => A, s: Free[S, V]) extends Free[S, A]

case class Pure[S, V](v: V) extends Free[S, V]


object Free {
  def pure[S, V](x: V): Free[S, V] = Pure[S, V](x)
}
