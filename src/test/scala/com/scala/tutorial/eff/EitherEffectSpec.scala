package com.scala.tutorial.eff

import cats.data.Reader
import org.atnos.eff.{Eff, Fx}
import org.scalatest.{FunSpec, Matchers}
import org.atnos.eff.ReaderEffect._
import org.atnos.eff.EitherEffect._
import org.atnos.eff.syntax.all._

class EitherEffectSpec extends FunSpec with Matchers{
   describe("EitherEffect") {
     describe("catchLeft") {
       it("should catch the Left data") {
         type Stack = Fx.fx2[Reader[Int,?],Either[String,?]]
         val program:Eff[Stack,Int] = for {
           number <- ask[Stack,Int]
           result <- fromEither[Stack,String,Int](Either.cond((number === 0),number,"less than zero"))
         } yield result

         val programAfterTrans = program.catchLeft[String](e=>Eff.pure[Stack,Int](1))

         programAfterTrans.runReader(2).runEither.run should be(Right(1))
       }
     }
   }
}
