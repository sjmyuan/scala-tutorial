package com.scala.tutorial.discuss

import java.util.Date

import fs2._
import org.scalatest.{FunSpec, Matchers}

import scala.util.Random

class ParallelSpec extends FunSpec with Matchers {
  describe("fs2") {
    describe("0.9.7") {
      implicit val strategy = Strategy.fromFixedDaemonPool(10, "test-pool")

      def Job(): Int = {
        println(s"${System.currentTimeMillis()}: ${Thread.currentThread.getName}")
        Thread.sleep(1000)
        Random.nextInt()
      }

      it("merge should run in parallel") {
        val result = Stream.eval(Task {
          Job()
        }).merge(Stream.eval(Task {
          Job()
        })).runLog.unsafeRun()
        println(result)
      }

      it("join should run in parallel") {
        val stream: Stream[Task, Stream[Task, Int]] = Stream.emits[Task, Int](Range(1, 10)).map(x => Stream.eval(Task {
          Job()
        }))
        concurrent.join(3)(stream).runLog.unsafeRun()
      }

      it("task.parallelTraverse should can be run in parallel") {
        Task.parallelTraverse(Range(1, 100))(x => {
          println(s"task ${x}")
          Task {
            Job()
          }
        }).unsafeRun()
      }

      it("task.traverse should can be run in seralization") {
         Task.traverse(Range(1, 10))(x => {
           println(s"task ${x}")
           Task {
             Job()
           }
         }).unsafeRun()
      }

      it("Task() should use the thread pool") {
        Task {
          Job()
        }.unsafeRun()
      }

      it("Task.delay should use the main thread") {
        Task.delay {
          Job()
        }.unsafeRun()
      }

      it("Stream.emit will return job one by one") {
        val collect = Stream.emits(Range(1, 10)).map(x => Stream.eval(Task {
          Job()
        }))
        val result = concurrent.join(3)(collect).take(4).runLog.unsafeRun()
        println(result)
      }

      it("Task.flatMap will return to current thread"){
        Task(Job()).flatMap(x=>Task.delay({
          println("haha")
          Job()
        })).unsafeRun()
      }

      it("join will fail if there is one stream fail") {
        val collect = Stream.emits(List(
          Stream.eval(Task {
            Job()
          }),
          Stream.eval(Task[Int] {
            assert(1 != 1)
            Job()
          })
        )
        )
        val result = concurrent.join(3)(collect).runLog.attempt.unsafeRun()
        println(result)
      }

      it("Task.parallelTravers won't block when the inner Task is async"){

        val seeds = Range(1,30)

        Task.parallelTraverse(seeds)(x=>Task{Job()})(Strategy.fromFixedDaemonPool(1)).unsafeRun()
      }

      it("Task.parallelTravers will block when the inner Task is not async"){

        val seeds = Range(1,30)

        Task.parallelTraverse(seeds)(x=>Task.delay(Job()))(Strategy.fromFixedDaemonPool(1)).unsafeRun()
      }
    }
  }
}
