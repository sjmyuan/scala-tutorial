package com.scala.tutorial

/**
  * Created by jiaming.shang on 8/13/17.
  */
class SubcalssDemo extends ClassDemo with TraitDemo{

  override def sayHi()={
    println("I'm in SubclassDemo")
  }

}
