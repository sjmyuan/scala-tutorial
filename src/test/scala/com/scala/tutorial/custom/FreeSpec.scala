package com.scala.tutorial.custom

import com.scala.tutorial.custom.category.monad.{Free, FreeSimple}
import com.scala.tutorial.custom.category.monad.Free.Id
import com.scala.tutorial.custom.category.monad.~>
import org.scalatest.{FunSpec, Matchers}

/**
  * Created by jiaming.shang on 9/18/17.
  */
class FreeSpec extends FunSpec with Matchers {

  describe("Free") {
    describe("run") {
      it("should return the correct result") {

        trait Request[A]
        case class GetName() extends Request[String]
        case class GetInfoByName(name:String) extends Request[String]
        case class GetAddressByInfo(info:String) extends Request[String]

        class interpreter extends (Request ~> Id) {
          override def apply[A](v: Request[A]): Id[A] = {
            v match {
              case GetName() => "test"
              case GetInfoByName(name) => "Info"
              case GetAddressByInfo(info) => "address"
            }
          }
        }

        val expressions = for {
          x1 <- Free.lift[Request,String](GetName())
          x2 <- Free.lift[Request,String](GetInfoByName(x1))
          x3 <- Free.lift[Request,String](GetAddressByInfo(x2))
        } yield x3

        expressions.run(new interpreter) should be("address")
      }
    }
  }

}
