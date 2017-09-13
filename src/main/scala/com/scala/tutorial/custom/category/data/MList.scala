package com.scala.tutorial.custom.category.data

/**
  * Created by jiaming.shang on 9/13/17.
  */

sealed trait MList[+A]
case object END extends MList[Nothing]
case class CHAIN[+A](head:A,tail:MList[A]) extends MList[A]

object MList{
  def map[A,B](l:MList[A])(f:A=>B): MList[B] ={
    l match {
      case END => END
      case CHAIN(head,tail) => CHAIN(f(head),map(tail)(f))
    }
  }

  def flatMap[A,B](l:MList[A])(f:A=>MList[B]):MList[B] ={
    l match {
      case END => END
      case CHAIN(head,tail) => join(f(head),flatMap(tail)(f))
    }
  }

  def join[A](l1:MList[A],l2:MList[A]):MList[A] ={
    l1 match {
      case END => l2
      case CHAIN(head,tail) => CHAIN(head,join(tail,l2))
    }
  }

  def foldLeft[A,B](v:B)(l:MList[A])(f:(B,A)=>B):B={
    l match {
      case END => v
      case CHAIN(head,tail) => foldLeft(f(v,head))(tail)(f)
    }
  }

  def foldRight[A,B](v:B)(l:MList[A])(f:(A,B)=>B):B={
    l match {
      case END => v
      case CHAIN(head,tail) => f(head,foldRight(v)(tail)(f))
    }
  }

  def fold[A,B](v:B)(l:MList[A])(f:(B,A)=>B):B={
    foldLeft(v)(l)(f)
  }

  def reduce[A](l:MList[A])(f:(A,A)=>A):A={
    l match {
      case None => throw new Exception("Empty list can't reduce")
      case CHAIN(head,tail) => foldLeft(head)(tail)(f)
    }
  }
}

