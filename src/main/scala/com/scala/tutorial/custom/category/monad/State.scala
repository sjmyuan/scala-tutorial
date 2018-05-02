package com.scala.tutorial.custom.category.monad

/**
  * Created by jiaming.shang on 9/16/17.
  */
case class State[S, A](run: S => (S, A)) {

  def map[B](f: A => B): State[S, B] = {
    State(s => {
      val old = run(s)
      (old._1, f(old._2))
    })
  }

  def flatMap[B](f: A => State[S, B]): State[S, B] = {
    State(s => {
      val old = run(s)
      f(old._2).run(old._1)
    })
  }

  def evalState(s: S): A = run(s)._2

  def execState(s: S): S = run(s)._1
}

object State {
  def pure[S, A](a: A): State[S, A] = State[S, A](s => (s, a))

  def get[S]: State[S, S] = State((s: S) => (s, s))

  def put[S](s1: S): State[S, Unit] = State((s: S) => (s1, ()))

  def modify[S](f: S => S): State[S, Unit] = for {
    s <- get[S]
    a <- put(f(s))
  } yield a

  def gets[S, A](f: S => A): State[S, A] = for {
    s <- get[S]
  } yield f(s)
}
