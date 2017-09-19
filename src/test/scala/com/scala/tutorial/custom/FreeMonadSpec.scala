package com.scala.tutorial.custom

import com.scala.tutorial.custom.category.monad.Free
import org.scalatest.{FunSpec, Matchers}

/**
  * Created by jiaming.shang on 9/18/17.
  */
class FreeSpec extends FunSpec with Matchers {

  describe("Free"){
    describe("run"){
      it("should return the correct result"){
         val f1:Int=>Free[String,Int] = Free[String,Int](s"1${_}")
         val f2:Int=>Free[String,Int] = Free[String,Int](s"2${_}")

         val interpreter: String=>Int = _.toInt

         val expressions=for{
           x1<-Free("1")
           x2<-f1(x1)
           x3<-f2(x2)
         } yield x3

         expressions.run(interpreter) should be(211)


      }
    }
  }

}
