package com.scala.tutorial.custom

/**
  * Created by jiaming.shang on 8/17/17.
  */
sealed trait Try[+T]

final case class Success[T](v: T) extends Try[T]

final case class Failure[T](e: Exception) extends Try[T]

class TryOp[T](t: Try[T]) {
  def map[A](f: T => A) = {
    Try.map(t)(f)
  }

  def flatMap[A](f: T => Try[A]) = {
    Try.flatMap(t)(f)
  }
}

object Try {
  def apply[T](f: => T): Try[T] = {
    try {
      Success(f)
    } catch {
      case e: Exception => Failure(e)
    }
  }

  def map[T, A](t: Try[T])(f: T => A) = {
    t match {
      case Success(v) => Success(f(v))
      case Failure(e) => Failure(e)
    }
  }

  def flatMap[T, A](t: Try[T])(f: T => Try[A]) = {
    t match {
      case Success(v) => f(v)
      case Failure(e) => Failure(e)
    }
  }

  implicit def toOp[T](t: Try[T]) = new TryOp(t)
}
