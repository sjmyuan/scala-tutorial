package com.scala.tutorial.custom

/**
  * Created by jiaming.shang on 8/16/17.
  */
trait Option[+A]
case class Some[A](d:A) extends Option[A]
case object None extends Option[Nothing]

class OptionOp[A](d:Option[A]){
  def map[B](f:A=>B):Option[B]={
    Option.map(d)(f)
  }

  def flatMap[B](f:A=>Option[B]):Option[B]={
    Option.flatMap(d)(f)
  }

  def withFilter(f:A=>Boolean):Option[A]={
    Option.withFilter(d)(f)
  }
}

object Option{
  def map[A,B](o:Option[A])(f:A=>B):Option[B]={
    o match {
      case Some(x) => Some(f(x))
      case y => y.asInstanceOf[Option[B]]
    }
  }

  def flatMap[A,B](o:Option[A])(f:A=>Option[B]):Option[B]={
    o match {
      case Some(x) => f(x)
      case y => y.asInstanceOf[Option[B]]
    }
  }

  def withFilter[A](o:Option[A])(f:A=>Boolean):Option[A]={
    o match {
      case p@Some(x) if f(x) => p
      case _ => None
    }
  }

  implicit def toOp[A](o:Option[A]):OptionOp[A] = new OptionOp[A](o)
}
