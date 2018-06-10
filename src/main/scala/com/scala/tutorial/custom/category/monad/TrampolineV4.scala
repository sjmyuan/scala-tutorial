package com.scala.tutorial.custom.category.monad

import com.scala.tutorial.custom.category.monad.TrampolineV4.{Done, FlatMap, More}

trait TrampolineV4[A] {
  final def runT: A = {
    this match {
      case Done(v) => v
      case More(f) => f().runT
      case FlatMap(a, f) => a match {
        case Done(v) => f(v).runT
        case More(f1) => f1().flatMap(f).runT
        case FlatMap(a1, f1) =>
          a1.flatMap((x: Any) => f1(x).flatMap(f)).runT
      }
    }
  }

  def flatMap[B](f: A => TrampolineV4[B]): TrampolineV4[B] = this match {
    case FlatMap(a, f1) => FlatMap(a, (x: Any) => FlatMap(f1(x), f))
    case _ => FlatMap(this, f)
  }
}

object TrampolineV4 {

  case class Done[A](v: A) extends TrampolineV4[A]

  case class More[A](f: () => TrampolineV4[A]) extends TrampolineV4[A]

  case class FlatMap[A, B](v: TrampolineV4[A], f: A => TrampolineV4[B]) extends TrampolineV4[B]

}


case class StateV4[S, A](run: S => TrampolineV4[(S, A)]) {

  def map[B](f: A => B): StateV4[S, B] = {
    StateV4[S, B](s => More(() => run(s).flatMap({ case (s1, a) => Done((s1, f(a))) })))
  }

  def flatMap[B](f: A => StateV4[S, B]): StateV4[S, B] = {
    StateV4[S, B](s => More(() => run(s).flatMap({ case (s1, a) => More(() => f(a).run(s1)) })))
  }

  def evalStateV4(s: S): A = run(s).runT._2

  def execStateV4(s: S): S = run(s).runT._1
}

object StateV4 {
  def pure[S, A](a: A): StateV4[S, A] = StateV4[S, A](s => Done((s, a)))

  def get[S]: StateV4[S, S] = StateV4((s: S) => Done((s, s)))

  def put[S](s1: S): StateV4[S, Unit] = StateV4((s: S) => Done((s1, ())))

  def modify[S](f: S => S): StateV4[S, Unit] = for {
    s <- get[S]
    a <- put(f(s))
  } yield a

  def gets[S, A](f: S => A): StateV4[S, A] = for {
    s <- get[S]
  } yield f(s)
}

