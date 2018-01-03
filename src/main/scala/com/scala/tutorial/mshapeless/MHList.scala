package com.scala.tutorial.mshapeless

sealed trait MHList

final case class ::[+T, +A <: MHList](head: T, tail: A) extends MHList {
  override def toString() = {
    s"${head}::${tail}"
  }
}

sealed trait MHNil extends MHList {
  def ::[T](head: T): T::MHNil = com.scala.tutorial.mshapeless.::(head, this)

  override def toString() = {
    "MHNil"
  }
}

object MHNil extends MHNil

object MHList {
  def apply(): MHList = MHNil

  def apply[T](e: T): MHList = e :: MHNil
}

final class MHListOps[L <: MHList](l: L) {
  def ::[T](head: T): T :: L = com.scala.tutorial.mshapeless.::(head, l)
}

object MHListOps {
  implicit def toOps[L <: MHList](l: L) = new MHListOps(l)
}

