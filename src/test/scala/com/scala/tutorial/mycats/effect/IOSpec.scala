package com.scala.tutorial.mycats.effect

import cats.effect.IO
import org.scalatest.{FunSpec, Matchers}

class IOSpec extends FunSpec with Matchers {
  describe("IO") {
    it("should return Either when there is an exception") {
      IO(1 / 0).unsafeRunAsync(cb => {
        cb should matchPattern { case Left(_) => }
      })

      IO(1 / 0).runAsync(cb => {
        cb should matchPattern { case Left(_) => }
        IO.pure(())
      }).unsafeRunSync()

      IO(1 / 0).attempt.unsafeRunSync() should matchPattern { case Left(_) => }
    }
  }
}
