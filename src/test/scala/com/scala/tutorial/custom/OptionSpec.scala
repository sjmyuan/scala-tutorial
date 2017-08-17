package com.scala.tutorial.custom

import org.scalatest._

/**
  * Created by jiaming.shang on 8/16/17.
  */
class OptionSpec extends FunSpec with Matchers{

  describe("Option"){
    describe("map"){
      it("should process Some"){
        val some = Some("Test")
        val result = some.map(x=>x.length)
        result should be(Some(4))
      }

      it("should ignore None"){
        val none = None
        val result = none.map((x:String)=>"hello world")
        result should be(None)
      }
    }
  }

}
