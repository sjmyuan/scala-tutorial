package com.scala.tutorial.custom.category.data

/**
  * Created by jiaming.shang on 9/13/17.
  */

sealed trait MList[+A] {
  def map[B](f: A => B): MList[B] = {
    this match {
      case END => END
      case CHAIN(head, tail) => CHAIN(f(head), tail.map(f))
    }
  }

  def flatMap[B](f: A => MList[B]): MList[B] = {
    this match {
      case END => END
      case CHAIN(head, tail) => tail.fold(f(head))((sum, x) => sum.append(f(x)))
    }
  }

  def append[B >: A](l: MList[B]): MList[B] = {
    this match {
      case END => l
      case CHAIN(head, tail) => CHAIN(head, tail.append(l))
    }
  }

  def foldLeft[B](v: B)(f: (B, A) => B): B = {
    this match {
      case END => v
      case CHAIN(head, tail) => tail.foldLeft(f(v, head))(f)
    }
  }

  def foldRight[B](v: B)(f: (A, B) => B): B = {
    this match {
      case END => v
      case CHAIN(head, tail) => f(head, tail.foldRight(v)(f))
    }
  }

  def fold[B](v: B)(f: (B, A) => B): B = {
    foldLeft(v)(f)
  }

  def reduce[B >: A](f: (B, B) => B): B = {
    this match {
      case END => throw new Exception("Empty list can't reduce")
      case CHAIN(head, tail) => tail.foldLeft[B](head)(f)
    }
  }
}

case object END extends MList[Nothing]

case class CHAIN[+A](head: A, tail: MList[A]) extends MList[A]

