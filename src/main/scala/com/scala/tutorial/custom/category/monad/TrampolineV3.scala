package com.scala.tutorial.custom.category.monad

import com.scala.tutorial.custom.category.monad.TrampolineV3.{Done, FlatMap, More}

trait TrampolineV3[A] {
  final def runT: A = {
    this match {
      case Done(v) => v
      case More(f) => f().runT
      case FlatMap(a, f) => a match {
        case Done(v) => f(v).runT
        case More(f1) => FlatMap(f1(), f).runT
        case FlatMap(a1, f1) => FlatMap(FlatMap(Done(a1.runT), f1), f).runT
      }
    }
  }
}

object TrampolineV3 {

  case class Done[A](v: A) extends TrampolineV3[A]

  case class More[A](f: () => TrampolineV3[A]) extends TrampolineV3[A]

  case class FlatMap[A, B](v: TrampolineV3[A], f: A => TrampolineV3[B]) extends TrampolineV3[B]

}


case class StateV3[S, A](run: S => TrampolineV3[(S, A)]) {

  def map[B](f: A => B): StateV3[S, B] = {
    StateV3[S, B](s => FlatMap[(S, A), (S, B)](run(s), { case (s1, a) => Done((s1, f(a))) }))
  }

  def flatMap[B](f: A => StateV3[S, B]): StateV3[S, B] = {
    StateV3[S, B](s => FlatMap[(S, A), (S, B)](run(s), { case (s1, a) => f(a).run(s1) })
    )
  }

  def evalStateV3(s: S): A = run(s).runT._2

  def execStateV3(s: S): S = run(s).runT._1
}

object StateV3 {
  def pure[S, A](a: A): StateV3[S, A] = StateV3[S, A](s => Done((s, a)))

  def get[S]: StateV3[S, S] = StateV3((s: S) => Done((s, s)))

  def put[S](s1: S): StateV3[S, Unit] = StateV3((s: S) => Done((s1, ())))

  def modify[S](f: S => S): StateV3[S, Unit] = for {
    s <- get[S]
    a <- put(f(s))
  } yield a

  def gets[S, A](f: S => A): StateV3[S, A] = for {
    s <- get[S]
  } yield f(s)
}

