package com.scala.tutorial.custom.category.monad

/**
  * Created by jiaming.shang on 9/4/17.
  */
//case class Reader[A,B](f:A=>B) extends Monad[B]{
//  override def flatMap[C](g: (B) => Reader[A,C]): Reader[A,C] = {
//    Reader[A,C]((x:A) => g(f(x)).run(x))
//  }
//
//  override def map[C](g: (B) => C): Reader[A,C] = {
//    Reader[A,C]((x:A)=>g(f(x)))
//  }
//
//  def run(v:A):B = f(v)
//
//}

case class Reader[A, B](run: A => B) {
  def flatMap[C](g: (B) => Reader[A, C]): Reader[A, C] = {
    Reader[A, C]((x: A) => g(run(x)).run(x))
  }

  def map[C](g: (B) => C): Reader[A, C] = {
    Reader[A, C]((x: A) => g(run(x)))
  }

  def ap[C](g: Reader[A, B => C]): Reader[A, C] = {
    g.flatMap(f => Reader(x => f(run(x))))

    //    Reader(x => g.run(x)(run(x)))
  }
}

object Reader {
  def pure[A, B](v: B) = Reader[A, B](_ => v)
}

