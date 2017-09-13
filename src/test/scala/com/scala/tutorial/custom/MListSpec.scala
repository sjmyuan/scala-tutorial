package com.scala.tutorial.custom

import com.scala.tutorial.custom.category.data.{CHAIN, END, MList}
import org.scalatest.{FunSpec, Matchers}

/**
  * Created by jiaming.shang on 9/13/17.
  */
class MListSpec extends FunSpec with Matchers {

  describe("MList"){
    describe("instance"){
      it("can be construct by MList(.....)"){
        val result = MList(1,2,3)

        result should be(CHAIN(1,CHAIN(2,CHAIN(3,END))))
      }

//      it("can be used in match expression"){
//        val result = MList(1) match {
//          case MList(x) => x
//          case _ => 0
//        }
//
//        result should be(1)
//      }
    }

    describe("map"){
      it("can apply function to element"){
        MList(1,2,3).map(x=>x+1) should be(MList(2,3,4))
      }
    }

    describe("flatMap"){
      it("can apply function to element and flatten result list"){
        MList(1,2,3).flatMap(x=>MList(x+1)) should be(MList(2,3,4))
      }
    }

    describe("append"){
      it("can append another list to the tail"){
        MList(1,2,3).append(MList(1,2,3)) should be(MList(1,2,3,1,2,3))
      }
    }

    describe("foldLeft"){
      it("can collect element from left to right"){
        MList("1","2","3").foldLeft("")((sum,x)=>sum+x) should be("123")
      }
    }

    describe("fold"){
      it("can collect element from left to right"){
        MList("1","2","3").fold("")((sum,x)=>sum+x) should be("123")
      }
    }

    describe("foldRight"){
      it("can collect element from right to left"){
        MList("1","2","3").foldRight("")((x,sum)=>sum+x) should be("321")
      }
    }

    describe("reduce"){
      it("can collect element from left to right and initial value is first element"){
        MList("1","2","3").reduce((sum,x)=>sum+x) should be("123")
      }
    }

  }

}
