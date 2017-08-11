package com.scala.tutorial

import org.scalatest._

/**
  * Created by jiaming.shang on 8/10/17.
  */
class TraitSpec extends FunSpec with Matchers {
  describe("Trait"){

    it("construct from left to right"){
      trait T1{
        println("I'm in T1")
      }
      trait T2{
        println("I'm in T2")
      }

      class C1 extends T1 with T2{
        println("I'm in C1")
      }

      new C1
    }

    it("override left trait or class defination"){
      trait T1{
        def sayHi
      }
      trait T2{
        def sayHi = println("I'm in T2")
      }

      class C1 extends T2 with T1{
        override def sayHi = super.sayHi
      }

      new C1().sayHi
    }

    it("can implment cake pattern"){

      trait Login{
        def login()={
          println("I'm login")
        }
      }

      trait Logout{
        def logout()={
          println("I'm logout")
        }
      }

      trait App { self:Login with Logout=>
        def session()={
          login()
          logout()
        }
      }

      class App1 extends App with Login with Logout{

      }

      new App1().session()

    }

  }
}
