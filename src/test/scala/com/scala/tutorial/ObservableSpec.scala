package com.scala.tutorial

import cats.Eval
import cats.data.{Reader, Writer}
import monix.eval.Task
import scala.concurrent.Await
import org.atnos.eff.syntax.all._
import org.atnos.eff.all._
import org.atnos.eff._
import org.atnos.eff.syntax.addon.monix.task._
import org.scalatest.{FunSpec, Matchers}
import monix.execution.Scheduler.Implicits.global
import monix.reactive._
import scala.concurrent.duration._

/**
  * Created by jiaming.shang on 11/2/17.
  */
class ObservalbleSpec extends FunSpec with Matchers {

  describe("Observable") {
    it("should run with fix interval") {

      type Stack = Fx.fx5[Either[Throwable, ?], Writer[String, ?], Reader[String, ?], Eval, Task]

      val program: Eff[Stack, Int] = for {
        _ <- tell[Stack, String](s"Begin program ${System.currentTimeMillis()}")
        str <- ask[Stack, String]
        _ <- tell[Stack, String](s"Get config ${System.currentTimeMillis()}")
        int <- catchNonFatalThrowable[Stack, Int](str.toInt)
        _ <- tell[Stack, String](s"Done ${System.currentTimeMillis()}")
      } yield int


      val task: Task[Either[Throwable, Int]] = program
        .runEither
        //        .runWriterUnsafe[String](println(_))
        .runWriterEval((x: String) => Eval.later(println(x)))
        .runEval
        .runReader("11")
        .runAsync

      val handler = Observable.interval(1.second).flatMap(x => Observable.fromTask(task)).dump("0").take(10).runAsyncGetLast
      Await.result(handler, Duration.Inf)
    }
  }

}

