package com.scala.tutorial.codechef

import org.scalatest.{FunSpec, Matchers}

import scala.util.Random

/**
  * Created by jiaming.shang on 9/29/17.
  */
class LittleChefandSumsSpec extends FunSpec with Matchers {

  describe("LittleChefandSumsSpec") {
    it("should return the answer 1 ") {
      LittleChefandSums.miniIndex(Vector(1, 2, 3)) should be(1)
    }

    it("should return the answer 1 when list length is 1") {
      LittleChefandSums.miniIndex(Vector(1)) should be(1)
    }

    it("should return the answer 2") {
      LittleChefandSums.miniIndex(Vector(2, 1, 3, 1)) should be(2)
    }

//    it("should complete in 1 sec") {
//      val random = new Random(System.currentTimeMillis())
//      val startTime = System.currentTimeMillis()
//      Range(0,10).foreach(x=>{
//        LittleChefandSums.miniIndex(Range(0, 100000).map(x => random.nextInt(100000)).toVector)
//      })
//      val endTime = System.currentTimeMillis()
//
//      (endTime - startTime < 1000) should be(true)
//    }
  }

}
