package com.scala.tutorial.custom.category.monoid

/**
  * Created by jiaming.shang on 8/31/17.
  */
trait Monoid[A] {
  def id:A
  def binary(v1:A,v2:A):A
}
