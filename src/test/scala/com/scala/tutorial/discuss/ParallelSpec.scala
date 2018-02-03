package com.scala.tutorial.discuss

import java.util.Date

import fs2._
import org.scalatest.{FunSpec, Matchers}

import scala.util.Random

class ParallelSpec extends FunSpec with Matchers {
  describe("fs2") {
    describe("0.9.7") {
      implicit val strategy = Strategy.fromFixedDaemonPool(10, "test-pool")

      def Job(): Stream[Task, Int] = Stream.eval(Task[Int] {
        println(s"${System.currentTimeMillis()}: ${Thread.currentThread.getName}")
        Thread.sleep(1000)
        Random.nextInt()
      })

      it("merge should run in parallel") {
        val result = Job().merge(Job()).runLog.unsafeRun()
        println(result)
      }

      it("join should run in parallel") {
        val stream: Stream[Task, Stream[Task, Int]] = Stream.emits[Task, Int](Range(1, 10)).map(x => Job())
        concurrent.join(3)(stream).runLog.unsafeRun()
      }

      it("task should can be run in parallel"){
        Task.parallelTraverse(Range(1,100))(x=> Task {
          println(s"${System.currentTimeMillis()}: ${Thread.currentThread.getName}")
          Thread.sleep(1000)
          Random.nextInt()
        }).unsafeRun()
      }
    }
  }
}
