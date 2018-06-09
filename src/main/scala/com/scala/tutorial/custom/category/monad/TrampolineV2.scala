package com.scala.tutorial.custom.category.monad

import com.scala.tutorial.custom.category.monad.TrampolineV2.{Done, More}

trait TrampolineV2[A] {
  final def runT: A = {
    this match {
      case Done(v) => v
      case More(f) => f().runT
    }
  }

  def flatMap[B](f: A => TrampolineV2[B]): TrampolineV2[B] = {
    More(() => f(runT))
  }
}

object TrampolineV2 {

  case class Done[A](v: A) extends TrampolineV2[A]

  case class More[A](f: () => TrampolineV2[A]) extends TrampolineV2[A]

}


case class StateV2[S, A](run: S => TrampolineV2[(S, A)]) {

  def map[B](f: A => B): StateV2[S, B] = {
    StateV2(s => More(() => {
      run(s).flatMap { case (s1, a) => Done((s1, f(a))) }
    }))
  }

  def flatMap[B](f: A => StateV2[S, B]): StateV2[S, B] = {
    StateV2(s => More(() => {
      run(s).flatMap { case (s1, a) => f(a).run(s1) }
    }))
  }

  def evalStateV2(s: S): A = run(s).runT._2

  def execStateV2(s: S): S = run(s).runT._1
}

object StateV2 {
  def pure[S, A](a: A): StateV2[S, A] = StateV2[S, A](s => Done((s, a)))

  def get[S]: StateV2[S, S] = StateV2((s: S) => Done((s, s)))

  def put[S](s1: S): StateV2[S, Unit] = StateV2((s: S) => Done((s1, ())))

  def modify[S](f: S => S): StateV2[S, Unit] = for {
    s <- get[S]
    a <- put(f(s))
  } yield a

  def gets[S, A](f: S => A): StateV2[S, A] = for {
    s <- get[S]
  } yield f(s)
}

