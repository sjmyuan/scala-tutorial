package com.scala.tutorial.custom.category

import com.scala.tutorial.custom.category.data.MList

/**
  * Created by jiaming.shang on 9/16/17.
  */
package object monoid {

  class MonoidOps[A:Monoid](v:A){
    val monoid = implicitly[Monoid[A]]
    def id = monoid.id
    def |+|(l:A) = monoid.add(v,l)
  }

  implicit def listMonoid[A] = new ListMonoid[A]

  implicit def monoidOps[A:Monoid](v:A) = new MonoidOps(v)
}
