package com.scala.tutorial

object MonadComposeLimitation {

  def readContent(file: String): Either[Throwable, String] = ???

  def parseStringToInt(s: String): Option[Int] = ???

  def allocateMem(n: Int): Either[Throwable, Array[Byte]] = ???

  def getMem(file: String): Either[Throwable, Option[Int]] = {
    for {
      s <- readContent(file)
      i = parseStringToInt(s)
    } yield i
  }

}
