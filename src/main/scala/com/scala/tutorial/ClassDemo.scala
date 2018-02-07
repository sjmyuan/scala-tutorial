package com.scala.tutorial

import java.util.Date

/**
  * Created by jiaming.shang on 8/11/17.
  */
class ClassDemo {

  println("I'm in Class body")

  lazy val age = "10"

  val name = "Demo"

  def func = {
    println("I'm function")
  }

  def func1(date: =>Date = new Date() ){
    println(date)
  }

  def func2(value: =>Int): Unit ={
    println(value)
  }

}

object ClassDemo{
  def sayHai()={
    println("I'm ClassDemo object")
  }
}
