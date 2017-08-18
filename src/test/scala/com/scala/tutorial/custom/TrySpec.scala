package com.scala.tutorial.custom

import org.scalatest._

/**
  * Created by jiaming.shang on 8/18/17.
  */
class TrySpec extends FunSpec with Matchers {

  describe("Try") {
    it("should return Success") {
      Try("1".toInt) should be(Success(1))
    }

    it("should return Failure") {
      Try("r".toInt) shouldBe a[Failure[_]]
    }

    describe("Success") {
      describe("map") {
        it("should return transferred value") {
          Try("1".toInt).map(x => x + 1) should be(Success(2))
        }
      }
      describe("flatMap") {
        it("should return transferred value") {
          Try("1".toInt).flatMap(x => Try(x + "2".toInt)) should be(Success(3))
        }
      }
    }

    describe("Failure") {
      describe("map") {
        it("should return Failure") {
          Try("r".toInt).map(x => x + 1) shouldBe a[Failure[_]]
        }
      }
      describe("flatMap") {
        it("should return Failure") {
          Try("r".toInt).flatMap(x => Try(x + "2".toInt)) shouldBe a[Failure[_]]
        }
      }
    }
  }
}
