package com.scala.tutorial.eff

import cats.data.Reader
import org.atnos.eff._
import org.scalatest.{FunSpec, Matchers}
import org.atnos.eff.ReaderEffect._
import org.atnos.eff.EitherEffect._
import org.atnos.eff.syntax.all._

class EitherEffectSpec extends FunSpec with Matchers {
  describe("EitherEffect") {
    describe("catchLeft") {
      it("should catch the Left data when there is a either effect") {
        type Stack = Fx.fx2[Reader[Int, ?], Either[String, ?]]
        val program: Eff[Stack, Int] = for {
          number <- ask[Stack, Int]
          result <- fromEither[Stack, String, Int](Either.cond((number === 0), number, "less than zero"))
        } yield result

        val programAfterTrans = program.catchLeft[String](e => Eff.pure[Stack, Int](1))

        programAfterTrans.runReader(2).runEither.run should be(Right(1))
      }

      it("should not catch the Left data when run catchLeft before translate effect") {
        sealed trait MyEffect[A]
        case class transToEither(v: Int) extends MyEffect[Int]
        type HasMyEffect[R] = MemberIn[MyEffect, R]

        def runMyEffect[R, A, U: Member.Aux[MyEffect, R, ?] : MemberIn[Either[String, ?], ?]](eff: Eff[R, A]): Eff[U, A] = Interpret.translate[R, U, MyEffect, A](eff)(
          new Translate[MyEffect, U] {
            override def apply[X](kv: MyEffect[X]): Eff[U, X] = {
              kv match {
                case transToEither(v) => fromEither(Either.cond(v === 0, v, "error"))
              }
            }
          })

        type Stack = Fx.fx3[Reader[Int, ?], MyEffect, Either[String, ?]]
        val program: Eff[Stack, Int] = for {
          number <- ask[Stack, Int]
          result <- Eff.send[MyEffect, Stack, Int](transToEither(number))
        } yield result

        val programAfterTrans = program.catchLeft[String](e => Eff.pure[Stack, Int](1))

        runMyEffect(programAfterTrans.runReader(2)).runEither.run should be(Left("error"))
      }

      it("should catch the Left data when run catchLeft after translate effect") {
        sealed trait MyEffect[A]
        case class transToEither(v: Int) extends MyEffect[Int]
        type HasMyEffect[R] = MemberIn[MyEffect, R]

        def runMyEffect[R, A, U: Member.Aux[MyEffect, R, ?] : MemberIn[Either[String, ?], ?]](eff: Eff[R, A]): Eff[U, A] = Interpret.translate[R, U, MyEffect, A](eff)(
          new Translate[MyEffect, U] {
            override def apply[X](kv: MyEffect[X]): Eff[U, X] = {
              kv match {
                case transToEither(v) => fromEither(Either.cond(v === 0, v, "error"))
              }
            }
          })

        type Stack = Fx.fx3[Reader[Int, ?], MyEffect, Either[String, ?]]
        val program: Eff[Stack, Int] = for {
          number <- ask[Stack, Int]
          result <- Eff.send[MyEffect, Stack, Int](transToEither(number))
        } yield result

        runMyEffect(program.runReader(2)).catchLeft[String](e => Eff.pure[Fx.fx1[Either[String, ?]], Int](1)).runEither.run should be(Right(1))
      }
    }
    describe("runEither") {
      it("should return the Either type when there is no either effect in program") {
        type Stack = Fx.fx2[Reader[Int, ?], Either[String, ?]]
        val program: Eff[Stack, Int] = for {
          number <- ask[Stack, Int]
        } yield number

        program.runReader(2).runEither.run should be(Right(2))
      }
    }
  }
}
