package com.scala.tutorial.codechef

import org.scalatest.{FunSpec, Matchers}

/**
  * Created by jiaming.shang on 9/30/17.
  */
class SubsequenceEqualitySpec extends FunSpec with Matchers {

  describe("SubsequenceEquality"){
    it("should return no for likecs"){
      SubsequenceEquality.check("likecs") should be(false)
    }
    it("should return no for venivedivici"){
      SubsequenceEquality.check("venivedivici") should be(true)
    }
    it("should return no for bhuvan"){
      SubsequenceEquality.check("bhuvan") should be(false)
    }
    it("should return no for codechef"){
      SubsequenceEquality.check("codechef") should be(true)
    }
  }

}
