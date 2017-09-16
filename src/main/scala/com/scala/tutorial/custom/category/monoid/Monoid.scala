package com.scala.tutorial.custom.category.monoid

import com.scala.tutorial.custom.category.data._

/**
  * Created by jiaming.shang on 8/31/17.
  */
trait Monoid[A] {
  def id:A
  def |+|(v1:A,v2:A):A
}

class ListMonoid[A] extends Monoid[MList[A]] {
  override def id: MList[A] = END

  override def |+|(v1: MList[A], v2: MList[A]): MList[A] = {
     v1.append(v2)
  }
}
