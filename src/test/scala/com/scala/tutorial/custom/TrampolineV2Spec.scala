package com.scala.tutorial.custom

import com.scala.tutorial.custom.category.monad.StateV2
import org.scalatest.{FunSpec, Matchers}

class TrampolineV2Spec extends FunSpec with Matchers {

  describe("TrampolineV1") {
    it("should still throw stack overflow for StateV2") {
      Range(0, 100)
        .foldLeft[StateV2[Int, List[(Int, Int)]]](StateV2.pure[Int, List[(Int, Int)]](List()))(
        (acc, ele) =>
          for {
            kv <- acc
            index <- StateV2.get
            _ <- StateV2.put(index + 1)
          } yield (index, ele) :: kv
      ).run(0).runT._2.reverse
    }
  }
}
