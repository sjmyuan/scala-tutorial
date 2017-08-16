package com.scala.tutorial.option

/**
  * Created by jiaming.shang on 8/16/17.
  */
trait Option[A]
case class Some[A](d:A) extends Option[A]
case class None[A]() extends Option[A]

class OptionOp[A](d:Option[A]){
  def map[B](f:A=>B):Option[B]={
    Option.map(d)(f)
  }
}

object Option{
  def map[A,B](o:Option[A])(f:A=>B):Option[B]={
    o match {
      case Some(x) => Some(f(x))
      case None() => None[B]()
    }
  }
  implicit def toOp[A](o:Option[A]):OptionOp[A] = new OptionOp[A](o)
}
