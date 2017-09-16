package com.scala.tutorial.custom

import com.scala.tutorial.custom.category.data.MList
import com.scala.tutorial.custom.category.monad.Writer
import org.scalatest.{FunSpec, Matchers}
import com.scala.tutorial.custom.category.monoid._

/**
  * Created by jiaming.shang on 9/16/17.
  */
class WriterSpec extends FunSpec with Matchers{

  describe("Writer"){
    describe("flatMap"){
      it("should concat the log list"){
        val result = for{
          v1<-Writer(MList("v1"),1)
          v2<-Writer(MList("v2"),1)
        } yield v1+v2

        result should be(Writer(MList("v1","v2"),2))

      }
    }
  }

}
