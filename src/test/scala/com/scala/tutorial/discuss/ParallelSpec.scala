package com.scala.tutorial.discuss

import java.util.Date

import fs2._
import org.scalatest.{FunSpec, Matchers}

import scala.util.Random

class ParallelSpec extends FunSpec with Matchers {
  describe("fs2") {
    describe("0.9.7") {
      it("should run in parallel") {
        implicit val strategy = Strategy.fromFixedDaemonPool(10,"test-pool")
        def Job(): Stream[Task,Int] = Stream.eval(Task[Int] {
          println(s"${new Date}: ${Thread.currentThread.getName}")
          Thread.sleep(10)
          Random.nextInt()
        })

        Job().merge(Job()).run.unsafeRun()
      }
    }
  }
}
