package com.scala.tutorial.discuss

import org.scalatest.{FunSpec, Matchers}

class ObjectVSCaseObject extends FunSpec with Matchers {

  describe("object VS case object") {
    it("case object can trigger an warning in compile") {
      sealed trait Demo1
      case class Demo1Child1(v: String) extends Demo1
      case object Demo1Child2 extends Demo1

      val a: Demo1 = Demo1Child1("1")

      val b: Int = a match {
        case Demo1Child1(v) => 1
      }

    }

    it("object also can trigger an warning in compile") {
      sealed trait Demo2
      case class Demo2Child1(v: String) extends Demo2
      object Demo2Child2 extends Demo2

      val a: Demo2 = Demo2Child1("1")

      val b: Int = a match {
        case Demo2Child1(v) => 1
      }

      val c: Int = a match {
        case Demo2Child2 => 2
        case Demo2Child1(v) => 1
      }

    }
  }

}
