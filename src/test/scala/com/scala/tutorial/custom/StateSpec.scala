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
  }

}
