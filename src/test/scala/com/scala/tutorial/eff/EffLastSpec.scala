package com.scala.tutorial.eff

import cats.data.Reader
import cats.effect.IO
import org.atnos.eff.{Eff, Fx}
import org.scalatest.FunSpec
import org.atnos.eff.syntax.all._
import org.atnos.eff.ReaderEffect._
import org.atnos.eff.addon.cats.effect.IOEffect
import org.atnos.eff.syntax.addon.cats.effect._

class EffLastSpec extends FunSpec {
  describe("Eff Last") {
    it("should run at the last of eff") {
      type Stack = Fx.fx2[Reader[Int, ?], IO]
      val program: Eff[Stack, Unit] = for {
        _ <- Eff.pure[Stack,String]("a").addLast(IOEffect.ioDelay[Stack,Unit](println("start............")))
        number <- ask[Stack, Int].addLast(IOEffect.fromIO(IO(println("begin get number"))))
        result <- Eff.pure[Stack,String]("b").addLast({
            println("ooooooo")
            IOEffect.ioDelay[Stack,Unit](println("end get number"))
          })
        _ <- ask[Stack, Int]
      } yield result

      program.runReader(10).unsafeRunSync
    }
  }
}
