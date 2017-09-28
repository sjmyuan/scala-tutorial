package com.scala.tutorial.codechef

import org.scalatest.{FunSpec, Matchers}

import scala.util.{Failure, Random, Success}

/**
  * Created by jiaming.shang on 9/27/17.
  */
class MiniMaxSpec extends FunSpec with Matchers{
  describe("MiniMax"){
    describe("transposition"){
      it("should return the transposition matrix"){
        val matrix = Vector(
          Vector(10,20,10),
          Vector(20,10,5),
          Vector(30,30,35))

        val matrixT = Vector(
          Vector(10,20,30),
          Vector(20,10,30),
          Vector(10,5,35)
        )

        MiniMax.transposition(matrix) should be(matrixT)
      }
    }
    it("should return 0 when matrix is stable"){
      val matrix = Vector(
        Vector(10,20,10),
        Vector(20,10,5),
        Vector(30,30,35))
      val num = MiniMax.minimumChanges(matrix)
      num should be(Success(0))
    }

    it("should return 1 when minimun changes is 1"){
      val matrix = Vector(
        Vector(10,20,30),
        Vector(20,10,30),
        Vector(10,5,35))
      val num = MiniMax.minimumChanges(matrix)
      num should be(Success(1))
    }

    it("should return 2 when minimun changes is 2"){
      val matrix = Vector(
        Vector(1,1,3,4),
        Vector(5,1,1,8),
        Vector(9,10,1,1),
        Vector(1,14,15,1))
      val num = MiniMax.minimumChanges(matrix)
      num should be(Success(2))
    }

    it("should run in 2 seconds"){
      val random=new Random(System.currentTimeMillis())
      val startTime=System.currentTimeMillis()
      val matrix=Range(0,1000).map(x=>Range(0,1000).map(x=>random.nextInt(1000000)).toVector).toVector
      val num = MiniMax.minimumChanges(matrix)
      val endTime = System.currentTimeMillis()
      (endTime-startTime < 2000) should be(true)
    }

    it("should return Failure when matrix is invalid"){
      val matrix = Vector(
        Vector(1,1,3,4),
        Vector(5,1,1,8),
        Vector(9,10,1),
        Vector(1,14,15,1))
      val num = MiniMax.minimumChanges(matrix)
      num shouldBe a[Failure[_]]
    }
  }
}
