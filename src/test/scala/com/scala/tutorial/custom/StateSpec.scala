package com.scala.tutorial.custom

import com.scala.tutorial.custom.category.monad.State
import org.scalatest.{FunSpec, Matchers}

/**
  * Created by jiaming.shang on 9/16/17.
  */
class StateSpec extends FunSpec with Matchers {

  describe("State") {
    describe("get") {
      it("should return the State(s=>(s,s))") {
        State.get[Int].run(1) should be((1, 1))
      }
    }

    describe("gets") {
      it("should return the State(s=>(s,f(s)))") {
        State.gets[Int, Int](x => x + 1).run(1) should be((1, 2))
      }
    }

    describe("put") {
      it("should return the State(s=>(s,_))") {
        State.put[Int](1).run(2) should be((1, ()))
      }
    }

    describe("modify") {
      it("should return the State(s=>(f(s),_))") {
        State.modify[List[Int]](x => x.tail).run(List(1, 2, 3)) should be((List(2, 3), ()))
      }
    }

    describe("pure") {
      it("should return the State(s=>(s,a))") {
        State.pure[List[Int], Int](2).run(List(1, 2, 3)) should be((List(1, 2, 3), 2))
      }
    }

    describe("Demos") {
      it("list can act as a stack") {

        type Stack = List[Int]

        def pop: State[Stack, Int] = for {
          a <- State.gets[Stack, Int]({ case head :: tail => head })
          _ <- State.modify[Stack]({ case head :: tail => tail })
        } yield a

        def push(v: Int): State[Stack, Unit] = State.modify[Stack](s => v :: s)

        def tos: State[Stack, Int] = State.gets({ case head :: tail => head })

        val result = for {
          _ <- push(10)
          _ <- push(20)
          _ <- push(40)
          a <- pop
          b <- pop
          _ <- push(a + b)
          v <- tos
        } yield v

        result.run(List()) shouldBe ((List(60, 10), 60))

      }

      it("too many action will throw exception") {
                Range(0, 100)
                  .foldLeft[State[Int, List[(Int, Int)]]](State.pure[Int, List[(Int, Int)]](List()))(
                  (acc, ele) =>
                    for {
                      kv <- acc
                      index <- State.get
                      _ <- State.put(index + 1)
                    } yield (index, ele) :: kv
                ).run(0)._2.reverse

        //        val data = Range(0, 1000000).toVector
        //        var initialState = State.pure[Int, List[(Int, Int)]](List())
        //        var i = 0
        //        data.foreach { ele =>
        //          initialState = for {
        //            kv <- initialState
        //            index <- State.get
        //            _ <- State.put(index + 1)
        //          } yield (index, ele) :: kv
        //        }
        //
        //        initialState.run(0)._2
      }
    }
  }

}
