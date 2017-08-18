package com.scala.tutorial.custom

import org.scalatest._

/**
  * Created by jiaming.shang on 8/16/17.
  */
class OptionSpec extends FunSpec with Matchers {

  describe("Option") {
    describe("map") {
      it("should process Some") {
        val some = Some("Test")
        val result = some.map(x => x.length)
        result should be(Some(4))
      }

      it("should ignore None") {
        val none = None
        val result = none.map((x: String) => "hello world")
        result should be(None)
      }
    }

    describe("flatMap") {
      it("should process Some") {
        Some("Test").flatMap(x => Some(x.length)) should be(Some(4))
      }

      it("should ignore None") {
        None.flatMap((x: String) => Some(x.length)) should be(None)
      }
    }

    describe("for") {
      it("should support for expression") {
        def getLength(x: String): Option[Int] = Some(x.length)
        def isOdd(x: Int): Option[Boolean] = Some(x % 2 == 1)

        val odd = for {
          x <- Some("test")
          l <- getLength(x)
          o <- isOdd(l)
        } yield o

        odd should be(Some(false))
      }

      it("should support filter") {
        def getLength(x: String): Option[Int] = Some(x.length)
        def isOdd(x: Int): Option[Boolean] = Some(x % 2 == 1)

        val test: Option[String] = Some("test")

        val odd = for {
          x <- test if x.length < 0
          l <- getLength(x)
          o <- isOdd(l)
        } yield o

        odd should be(None)
      }
    }
  }

}
