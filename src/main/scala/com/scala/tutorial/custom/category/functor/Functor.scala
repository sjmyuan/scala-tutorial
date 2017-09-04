package com.scala.tutorial.custom.category.functor

/**
  * Created by jiaming.shang on 8/31/17.
  */
trait Functor[+A] {
  def map[B](f:A=>B):Functor[B]
}
