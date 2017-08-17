package com.scala.tutorial.custom

/**
  * Created by jiaming.shang on 8/17/17.
  */
sealed trait Either[A,B]
final case class Left[A,B](d:A) extends Either[A,B]
final case class Right[A,B](d:B) extends Either[A,B]
