package com.scala.tutorial.custom

import com.scala.tutorial.custom.category.monad.{FreeSimple}
import org.scalatest.{FunSpec, Matchers}

/**
  * Created by jiaming.shang on 9/18/17.
  */
class FreeSimpleSpec extends FunSpec with Matchers {

  describe("FreeSimple") {
    describe("run") {
      it("should return the correct result") {
        val f1: Any => FreeSimple[String, Int] = (x: Any) => {
          println(x)
          FreeSimple.lift[String, Int](s"1${x}")
        }
        val f2: Any => FreeSimple[String, Int] = (x: Any) => {
          println(x)
          FreeSimple.lift[String, Int](s"2${x}")
        }

        val interpreter: String => Any = _.toInt

        val expressions = for {
          x1 <- FreeSimple.lift[String, Int]("1")
          x2 <- f1(x1)
          x3 <- f2(x2)
        } yield x3

        expressions.run(interpreter) should be(211)
      }
    }
  }

}
