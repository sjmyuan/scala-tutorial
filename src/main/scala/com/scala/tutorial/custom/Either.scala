package com.scala.tutorial.custom

/**
  * Created by jiaming.shang on 8/17/17.
  */
sealed trait Either[A, B]

final case class Left[A, B](a: A) extends Either[A, B]

final case class Right[A, B](b: B) extends Either[A, B]

class LeftProjection[A, B](either: Either[A, B]) {
  def map[C](f: A => C): Either[C, B] = {
    Either.map(either)(f)((x:B)=>x)
  }

  def flatMap[C](f: A => Either[C, B]): Either[C, B] = {
    either match {
      case Left(x) => f(x)
      case Right(y) => Right(y)
    }
  }

  def toOption():Option[A] ={
    either match {
      case Left(x) => Some(x)
      case Right(_) => None
    }
  }
}

class RightProjection[A, B](either: Either[A, B]) {
  def map[D](f: B => D): Either[A, D] = {
    Either.map(either)((x:A)=>x)(f)
  }

  def flatMap[D](f: B => Either[A, D]): Either[A, D] = {
    either match {
      case Left(x) => Left(x)
      case Right(y) => f(y)
    }
  }

  def toOption():Option[B] ={
    either match {
      case Left(_) => None
      case Right(y) => Some(y)
    }
  }
}

class EitherOp[A, B](either: Either[A, B]) {
  def isLeft: Boolean = Either.isLeft(either)

  def isRight: Boolean = Either.isRight(either)

  def fold[C, D](f: A => C)(g: B => D) = Either.map(either)(f)(g)

  def left: LeftProjection[A, B] = new LeftProjection(either)

  def right: RightProjection[A, B] = new RightProjection(either)
}

object Either {
  def isLeft[A, B](either: Either[A, B]): Boolean = {
    either match {
      case Left(_) => true
      case Right(_) => false
    }
  }

  def isRight[A, B](either: Either[A, B]): Boolean = {
    either match {
      case Left(_) => false
      case Right(_) => true
    }
  }

  def map[A, B, C, D](either: Either[A, B])(f: A => C)(g: B => D): Either[C, D] = {
    either match {
      case Left(left) => Left(f(left))
      case Right(right) => Right(g(right))
    }
  }

  implicit def toOp[A, B](either: Either[A, B]) = new EitherOp(either)
}
