package com.scala.tutorial.custom

import com.scala.tutorial.custom.category.monad.{Done, More, StateV1, TrampolineV1}
import org.scalatest.{FunSpec, Matchers}

class TrampolineV1Spec extends FunSpec with Matchers {

  describe("TrampolineV1") {
    it("should throw stack overflow when invoke different function") {
      def odd[A](list: List[A]): Boolean = list match {
        case Nil => false
        case head :: tail => even(tail)
      }

      def even[A](list: List[A]): Boolean = list match {
        case Nil => true
        case head :: tail => odd(tail)
      }

      even(Range(0, 100).toList) shouldBe (true)
      //      even(Range(0,1000000).toList) shouldBe(true)
    }

    it("should not throw stack overflow when using Trampoline") {
      def odd[A](list: List[A]): TrampolineV1[Boolean] = list match {
        case Nil => Done(false)
        case head :: tail => More(() => even(tail))
      }

      def even[A](list: List[A]): TrampolineV1[Boolean] = list match {
        case Nil => Done(true)
        case head :: tail => More(() => odd(tail))
      }

      even(Range(0, 1000000).toList).runT shouldBe (true)
    }

    it("should still throw stack overflow for StateV1") {
      Range(0, 100)
        .foldLeft[StateV1[Int, List[(Int, Int)]]](StateV1.pure[Int, List[(Int, Int)]](List()))(
        (acc, ele) =>
          for {
            kv <- acc
            index <- StateV1.get
            _ <- StateV1.put(index + 1)
          } yield (index, ele) :: kv
      ).run(0).runT._2.reverse
    }
  }
}
