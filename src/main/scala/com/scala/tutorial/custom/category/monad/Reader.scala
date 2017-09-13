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

case class Reader[A,B](f:A=>B){
  def flatMap[C](g: (B) => Reader[A,C]): Reader[A,C] = {
    Reader[A,C]((x:A) => g(f(x)).run(x))
  }

  def map[C](g: (B) => C): Reader[A,C] = {
    Reader[A,C]((x:A)=>g(f(x)))
  }

  def run(v:A):B = f(v)
}

