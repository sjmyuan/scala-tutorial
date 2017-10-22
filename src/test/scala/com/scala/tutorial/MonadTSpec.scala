package com.scala.tutorial

import org.scalatest.{FunSpec, Matchers}
import cats.implicits._
import cats.data._
import cats._

import scala.util.Try

/**
  * Created by jiaming.shang on 10/22/17.
  */
class MonadTSpec extends FunSpec with Matchers{

  describe("MonadTransformer"){
    it("should transform one monad to another"){
      type AppStack = ReaderT[Option,String,Int]

      def parseInt(s:String):Option[Int] ={
        Try{s.toInt}.toOption
      }

      val parseReader:AppStack=ReaderT(parseInt)

      parseReader.run("2.s") should be(None)
    }
  }

}
