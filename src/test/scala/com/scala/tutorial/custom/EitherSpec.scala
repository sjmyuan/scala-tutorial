package com.scala.tutorial.custom

import org.scalatest._
/**
  * Created by jiaming.shang on 8/17/17.
  */
class EitherSpec extends FunSpec with Matchers {

  describe("Either"){
    describe("Left"){
      describe("isLeft"){
        it("should return true"){
          Left("test").isLeft should be(true)
        }
      }

      describe("isRight"){
        it("should return false"){
          Left("test").isRight should be(false)
        }
      }

      describe("left"){
        describe("toOption"){
          it("should return Some"){
            Left("test").left.toOption should be(Some("test"))
          }
        }

        describe("map"){
          it("should return the transfered value"){
            Left("test").left.map(x=>x.length) should be(Left(4))
          }
        }

        describe("flatmap"){
          it("should return the transfered value"){
          }
        }
      }

      describe("right"){
        describe("toOption"){
          it("should return None"){
            Left("test").right.toOption should be(None)
          }
        }
      }
    }
  }

}
