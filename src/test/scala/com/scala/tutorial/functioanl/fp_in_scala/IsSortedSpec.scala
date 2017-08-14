package com.scala.tutorial.functioanl.fp_in_scala

import org.scalatest._

import scala.annotation.tailrec

/**
  * Created by jiaming.shang on 8/14/17.
  */
class IsSortedSpec extends FunSpec with Matchers {

  def isSorted[A](array:Array[A], ordered:(A,A)=>Boolean):Boolean ={
    @tailrec
    def loop(index:Int,tgt:Array[A],f:(A,A)=>Boolean):Boolean ={
      if(index==(tgt.size-1)) true
      else if(!f(tgt(index),tgt(index+1))) false
      else loop(index+1,tgt,f)
    }

    loop(0,array,ordered)
  }

  it("should return true for sorted list"){
    val sorted = isSorted(Array(0,1,2,3,4),(x:Int,y:Int)=> x<=y)
    sorted should be(true)
  }

  it("should return false for non-sorted list"){
    val sorted = isSorted(Array(1,0,2,3,4),(x:Int,y:Int)=> x<=y)
    sorted should be(false)
  }
}
