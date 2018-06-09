package com.scala.tutorial.custom

import com.scala.tutorial.custom.category.monad.StateV3
import org.scalatest.{FunSpec, Matchers}

class TrampolineV3Spec extends FunSpec with Matchers {

  describe("TrampolineV1") {
    it("should still throw stack overflow for StateV3") {
      Range(0, 100)
        .foldLeft[StateV3[Int, List[(Int, Int)]]](StateV3.pure[Int, List[(Int, Int)]](List()))(
        (acc, ele) =>
          for {
            kv <- acc
            index <- StateV3.get
            _ <- StateV3.put(index + 1)
          } yield (index, ele) :: kv
      ).run(0).runT._2.reverse
    }
  }
}
