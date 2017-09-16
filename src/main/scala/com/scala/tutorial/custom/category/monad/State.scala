package com.scala.tutorial.custom.category.monad

/**
  * Created by jiaming.shang on 9/16/17.
  */
case class State[S, A](run: S => (S, A)) {

  def apply(s: S): (S, A) = run(s)

  def map[B](f: ((S, A)) => (S, B)): State[S, B] = {
    State(s => f(run(s)))
  }

  def flatMap[B](f: ((S, A)) => State[S, B]): State[S, B] = {
    State(s => {
      val oldState = run(s)
      f(oldState).run(oldState._1)
    })
  }
}

object State {
  def get[S] = State((s: S) => (s, s))

  def gets[S, A](f: S => A) = State((s: S) => (s, f(s)))

  def put[S](s1: S) = State((s: S) => (s1, Unit))

  def state[S, A](v: A) = State[S, A](s => (s, v))

  def modify[S](f: S => S) = State((s: S) => (f(s), Unit))
}
