package com.scala.tutorial.custom.category.monad

case class IndexedState[SA, SB, A](run: SA => (SB, A)) {
  def map[B](f: A => B): IndexedState[SA, SB, B] = {
    IndexedState[SA, SB, B]((sa: SA) => {
      val old = run(sa)
      (old._1, f(old._2))
    })
  }

  def flatMap[B, SC](f: A => IndexedState[SB, SC, B]): IndexedState[SA, SC, B] = {
    IndexedState[SA, SC, B]((sa: SA) => {
      val old = run(sa)
      f(old._2).run(old._1)
    })
  }

  def evalState(s: SA): A = run(s)._2

  def execState(s: SA): SB = run(s)._1

  def get: IndexedState[SA, SB, SB] = IndexedState((sa: SA) => {
    val old = run(sa)
    (old._1, old._1)
  })

  def gets(f: SB => A): IndexedState[SA, SB, A] = IndexedState((sa: SA) => {
    val old = run(sa)
    (old._1, f(old._1))
  })
}

object IndexedState {
  def pure[S, A](a: A): IndexedState[S, S, A] = IndexedState[S, S, A](s => (s, a))

  def get[S]: IndexedState[S, S, S] = IndexedState(s => (s, s))

  def put[SA, SB](s1: SB): IndexedState[SA, SB, Unit] = IndexedState((s: SA) => (s1, ()))

  def modify[SA, SB](f: SA => SB): IndexedState[SA, SB, Unit] = for {
    s <- get[SA]
    a <- put[SA, SB](f(s))
  } yield a
}
