package com.scala.tutorial.discuss

import org.scalatest.{FunSpec, Matchers}

class PatternMatchBracketSpec extends FunSpec with Matchers {

  describe("PatternMatch") {
    it("don't need bracket in case") {
      1 match {
        case 1 => {
          println("hello")
        }
          println("world")
        case _ =>
          println("opps")

          println("what happen")
      }
    }
  }

}
