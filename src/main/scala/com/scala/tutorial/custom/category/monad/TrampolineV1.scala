package com.scala.tutorial.custom.category.monad

import com.scala.tutorial.custom.category.monad.TrampolineV1.{Done, More}

trait TrampolineV1[A] {
  final def runT: A = {
    this match {
      case Done(v) => v
      case More(f) => f().runT
    }
  }
}

object TrampolineV1 {

  case class Done[A](v: A) extends TrampolineV1[A]

  case class More[A](f: () => TrampolineV1[A]) extends TrampolineV1[A]

}


case class StateV1[S, A](run: S => TrampolineV1[(S, A)]) {

  def map[B](f: A => B): StateV1[S, B] = {
    StateV1(s => More(() => {
      val old = run(s).runT
      Done((old._1, f(old._2)))
    }))
  }

  def flatMap[B](f: A => StateV1[S, B]): StateV1[S, B] = {
    StateV1(s => More(() => {
      val old = run(s).runT
      f(old._2).run(old._1)
    }))
  }

  def evalStateV1(s: S): A = run(s).runT._2

  def execStateV1(s: S): S = run(s).runT._1
}

object StateV1 {
  def pure[S, A](a: A): StateV1[S, A] = StateV1[S, A](s => Done((s, a)))

  def get[S]: StateV1[S, S] = StateV1((s: S) => Done((s, s)))

  def put[S](s1: S): StateV1[S, Unit] = StateV1((s: S) => Done((s1, ())))

  def modify[S](f: S => S): StateV1[S, Unit] = for {
    s <- get[S]
    a <- put(f(s))
  } yield a

  def gets[S, A](f: S => A): StateV1[S, A] = for {
    s <- get[S]
  } yield f(s)
}

