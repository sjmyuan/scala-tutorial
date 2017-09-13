package com.scala.tutorial.custom

import com.scala.tutorial.custom.category.monad.Reader
import org.scalatest.{FunSpec, Matchers}

/**
  * Created by jiaming.shang on 9/13/17.
  */
class ReaderSpec extends FunSpec with Matchers {

  describe("Reader Monad"){
    describe("run"){
      it("should return the function result"){
        val result = Reader((x:Int)=>x+1).run(2)
        result should be(3)
      }
    }

    describe("map"){
      it("should return the result of composed function"){
        val reader = Reader((x:Int)=>x+1)
        val result = reader.map(x=>x+1).run(2)
        result should be(4)
      }
    }

    describe("flatMap"){
      it("should return the result of composed function"){
        val reader1 = Reader((x:Int)=>x+1)
        val result2 = reader1.flatMap((x:Int)=>Reader((y:Int)=>x+y))
        result2.run(1) should be(3)
        result2.run(2) should be(5)
      }
    }

    describe("for") {
      it("should support for expression"){
        val reader = for{
          y <- Reader((x:Int)=>x+1)
          z <- Reader((x:Int)=>x+y)
        } yield z

        reader.run(1) should be(3)
        reader.run(2) should be(5)
      }
    }

  }

}
