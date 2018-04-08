package com.scala.tutorial

import org.scalatest.{FunSpec, Matchers}

class EqSpec extends FunSpec with Matchers {

  describe("case class") {
    it("should return false when there is no equals defination") {
      class Demo(val v: Int)

      case class DemoCase(v: Demo)

      DemoCase(new Demo(1)) shouldNot be(DemoCase(new Demo(1)))
    }

    it("should return true when there is  equals defination") {
      class Demo(val v: Int) {
        override def equals(obj: scala.Any): Boolean = {
          Option(obj)
            .filter(_.isInstanceOf[Demo])
            .map(_.asInstanceOf[Demo])
            .map(_.v == v).getOrElse(false)
        }
      }

      case class DemoCase(v: Demo)

      DemoCase(new Demo(1)) should be(DemoCase(new Demo(1)))
    }
  }

}
