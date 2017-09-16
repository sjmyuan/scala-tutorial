package com.scala.tutorial.custom.category

/**
  * Created by jiaming.shang on 9/16/17.
  */
package object monoid {

  implicit def listMonoid[A] = new ListMonoid[A]

}
