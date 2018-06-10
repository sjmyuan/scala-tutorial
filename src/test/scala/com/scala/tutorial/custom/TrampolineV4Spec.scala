package com.scala.tutorial.custom

import com.scala.tutorial.custom.category.monad.StateV4
import org.scalatest.{FunSpec, Matchers}

class TrampolineV4Spec extends FunSpec with Matchers {

  describe("TrampolineV4") {
    it("should not throw stack overflow for StateV4") {
      Range(0, 1000000)
        .foldLeft[StateV4[Int, List[(Int, Int)]]](StateV4.pure[Int, List[(Int, Int)]](List()))(
        (acc, ele) =>
          for {
            kv <- acc
            index <- StateV4.get
            _ <- StateV4.put(index + 1)
          } yield (index, ele) :: kv
      ).run(0).runT._2.reverse
    }
  }
}
