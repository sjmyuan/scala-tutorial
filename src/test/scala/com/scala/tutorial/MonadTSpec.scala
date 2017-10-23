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
      type WriterOption[A]=WriterT[Option,String,A]
      type AppStack[A] = ReaderT[WriterOption,String,A]

      def parseInt(s:String):WriterOption[Int] ={
        for{
          v<-WriterT.valueT[Option,String,Int](Try{s.toInt}.toOption)
          _<-WriterT.tell[Option,String](s"the value is ${v}")
        } yield v
      }



      val parseReader:AppStack[Int]=ReaderT[WriterOption,String,Int](parseInt)

      parseReader.run("2").run should be(Some(("the value is 2",2)) )
    }
  }

}
