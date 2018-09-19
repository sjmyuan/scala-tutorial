package com.scala.tutorial

import org.scalatest.{FunSpec, Matchers}

class SequenceSpec extends FunSpec with Matchers {

  describe("Sequence") {
    it("should convert List[Either] to Either[List]") {
      def sequence[E, A](l: List[Either[E, A]]): Either[E, List[A]] = {
        l.foldLeft[Either[E, List[A]]](Right(List()))((acc, ele) => {
          acc match {
            case Left(_) => acc
            case Right(v) => ele match {
              case Left(e) => Left(e)
              case Right(a) => Right(v ::: List(a))
            }
          }
        })
      }

      val input1: List[Either[String, Int]] = List(Right(1), Right(2))
      sequence(input1) should be(Right(List(1, 2)))

      val input2: List[Either[String, Int]] = List(Right(1), Left("Error"))
      sequence(input2) should be(Left("Error"))
    }
  }

}
