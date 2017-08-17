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
            Left("test").left.flatMap(x=>Right(4)) should be(Right(4))
          }
        }
      }

      describe("right"){
        describe("toOption"){
          it("should return None"){
            Left("test").right.toOption should be(None)
          }
        }

        describe("map"){
          it("should return the original value"){
            Left("test").right.map((x:Int)=>1) should be(Left("test"))
          }
        }

        describe("flatmap"){
          it("should return the original value"){
            Left("test").right.flatMap((x:Int)=>Right(4)) should be(Left("test"))
          }
        }
      }
    }

    describe("Right"){
      describe("isLeft"){
        it("should return false"){
          Right("test").isLeft should be(false)
        }
      }

      describe("isRight"){
        it("should return false"){
          Right("test").isRight should be(true)
        }
      }

      describe("left"){
        describe("toOption"){
          it("should return None"){
            Right("test").left.toOption should be(None)
          }
        }

        describe("map"){
          it("should return the original value"){
            Right("test").left.map((x:String)=>x.length) should be(Right("test"))
          }
        }

        describe("flatmap"){
          it("should return the original value"){
            Right("test").left.flatMap((x:String)=>Left(4)) should be(Right("test"))
          }
        }
      }

      describe("right"){
        describe("toOption"){
          it("should return Some"){
            Right("test").right.toOption should be(Some("test"))
          }
        }

        describe("map"){
          it("should return the transfered value"){
            Right("test").right.map(x=>x.length) should be(Right(4))
          }
        }

        describe("flatmap"){
          it("should return the transfered value"){
            Right("test").right.flatMap(x=>Left(1)) should be(Left(1))
          }
        }
      }
    }
  }

}
