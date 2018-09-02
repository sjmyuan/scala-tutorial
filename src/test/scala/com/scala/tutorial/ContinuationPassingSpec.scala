package com.scala.tutorial

import org.scalatest.{FunSpec, Matchers}

import scala.annotation.tailrec

class ContinuationPassingSpec extends FunSpec with Matchers {

  sealed trait MList[+A] {

    final def ++[B >: A](other: MList[B]): MList[B] = {
      this match {
        case MNil => other
        case Connect(head, tail) => Connect[B](head, (tail ++ other))
      }
    }

    final def +++[B >: A](other: MList[B], f: MList[B] => MList[B]): MList[B] = {
      this match {
        case MNil => f(other)
        case Connect(head, tail) => tail.+++[B](other, x => f(Connect(head, x)))
      }

    }
  }

  object MList {
    def apply[A](args: A*): MList[A] = {
      def construct(v: Seq[A], curr: MList[A]): MList[A] = {
        if (v.isEmpty) curr
        else {
          construct(v.tail, Connect[A](v.head, curr))
        }
      }

      construct(args.reverse, MNil)
    }
  }

  case object MNil extends MList[Nothing]

  case class Connect[A](head: A, tail: MList[A]) extends MList[A]

  describe("List Contatenation") {
    it("should be able to construct a MList") {
      MList(1, 2, 3) shouldBe (Connect(1, Connect(2, Connect(3, MNil))))
    }

    describe("++") {
      it("should connect two list") {
        val list1 = MList(1, 2, 3, 4)
        val list2 = MList(5, 6)

        list1 ++ list2 shouldBe (MList(1, 2, 3, 4, 5, 6))
      }

      it("should throw stack overflow") {
        Range(1, 100000).foldLeft[MList[Int]](MNil)((acc, e) => {
          acc.++(Connect[Int](e, MNil))
        })
      }
    }
    describe("+++") {
      it("should connect two list") {
        val list1 = MList(1, 2, 3, 4)
        val list2 = MList(5, 6)

        list1.+++[Int](list2, identity) shouldBe (MList(1, 2, 3, 4, 5, 6))
      }

      it("should not trow stack overflow") {
        Range(1, 100000).foldLeft[MList[Int]](MNil)((acc, e) => {
          acc.+++[Int](Connect(e, MNil), identity)
        })
      }
    }

  }
}
