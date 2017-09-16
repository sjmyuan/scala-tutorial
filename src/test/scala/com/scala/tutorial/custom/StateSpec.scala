package com.scala.tutorial.custom

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
        State.gets(x=>x+1)(1) should be((1,2))
      }
    }

    describe("put"){
      it("should return the State(s=>(f(s),_))"){
        State.put(x=>x+1)(1) should be((2,_))
      }
    }

    describe("state"){
      it("should return the State(s=>(_,v))"){
        State.state(1)(3) should be((_,1))
      }
    }
  }

}
