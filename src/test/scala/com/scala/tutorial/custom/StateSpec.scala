package com.scala.tutorial.custom

import com.scala.tutorial.custom.category.monad.State
import org.scalatest.{FunSpec, Matchers}

/**
  * Created by jiaming.shang on 9/16/17.
  */
class StateSpec extends FunSpec with Matchers{

  describe("State"){
    describe("get"){
      it("should return the State(s=>(s,s))"){
        State.get[Int](1) should be((1,1))
      }
    }

    describe("gets"){
      it("should return the State(s=>(s,f(s)))"){
        State.gets[Int,Int](x=>x+1)(1) should be((1,2))
      }
    }

    describe("put"){
      it("should return the State(s=>(s,_))"){
        State.put[Int](1)(2) should be((1,Unit))
      }
    }

    describe("state"){
      it("should return the State(s=>(_,v))"){
        State.state(1)(3) should be((3,1))
      }
    }

    describe("modify"){
      it("should return the State(s=>(f(s),_))"){
        State.modify[Int](x=>x+1)(1) should be((2,Unit))
      }
    }
  }

}
