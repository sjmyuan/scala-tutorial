package com.scala.tutorial

import org.scalatest.{FunSpec, Matchers}

class SymbolSpec extends FunSpec with Matchers {

  describe("Symbol") {
    it("should be constructed by a single quote") {
      val a = 'a

      a.isInstanceOf[Symbol] should be(true)
    }

    it("can be constructed from a string") {
      val a = 'a
      val b = Symbol("a")

      b == a should be(true)
    }

    it("can get the string value") {
      val a = 'a

      a.name should be("a")
    }

    it("can extract the name in pattern match") {
      val a = 'a
      val b = a match {
        case Symbol(v) => v
        case _ => ""
      }

      b should be("a")
    }
  }

}
