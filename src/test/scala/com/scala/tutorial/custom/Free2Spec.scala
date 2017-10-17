package com.scala.tutorial.custom

import com.scala.tutorial.custom.category.functor.Functor
import com.scala.tutorial.custom.category.monad.Free2.{Id, ~>}
import org.scalatest.{FunSpec, Matchers}


/**
  * Created by jiaming.shang on 9/18/17.
  */
class Free2Spec extends FunSpec with Matchers {

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

        implicit val functor = new Functor[Request]{
          override def map[A, B](v: Request[A])(f: (A) => B): Request[B] = {
            v.asInstanceOf[Request[B]]
          }
        }

//        val expressions = for {
//          x1 <- Free2.lift[Request,String](GetName())
//          x2 <- Free2.lift[Request,String](GetInfoByName(x1))
//          x3 <- Free2.lift[Request,String](GetAddressByInfo(x2))
//        } yield x3
//
//        expressions.run(new interpreter) should be("address")
      }
    }
  }

}
