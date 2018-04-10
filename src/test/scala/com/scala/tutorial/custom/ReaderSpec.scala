package com.scala.tutorial.custom

import cats.syntax.compose
import com.scala.tutorial.custom.category.monad.Reader
import org.scalacheck.{Arbitrary, Cogen, Gen}
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FunSpec, Matchers}
import Arbitrary.arbitrary

/**
  * Created by jiaming.shang on 9/13/17.
  */
class ReaderSpec extends FunSpec with Matchers with GeneratorDrivenPropertyChecks {

  implicit val readerArbitrary: Arbitrary[Reader[Int, Int]] = Arbitrary(arbitrary[Int => Int].map(Reader(_)))
  implicit val readerFuncArbitrary: Arbitrary[Reader[Int, Int => Int]] = Arbitrary(arbitrary[Int => Int => Int].map(Reader(_)))

  describe("Reader Monad") {
    describe("run") {
      it("should return the function result") {
        val result = Reader((x: Int) => x + 1).run(2)
        result should be(3)
      }
    }

    describe("map") {
      it("should return the result of composed function") {
        val reader = Reader((x: Int) => x + 1)
        val result = reader.map(x => x + 1).run(2)
        result should be(4)
      }

      it("should obey the identity law") {

        forAll { (seed: Int, r: Reader[Int, Int]) =>
          r.map(identity).run(seed) should be(r.run(seed))
        }

      }

      it("should obey the composition law") {

        forAll { (seed: Int, r: Reader[Int, Int], f: Int => Int, g: Int => Int) =>
          r.map(f).map(g).run(seed) should be(r.map(f andThen g).run(seed))
        }

        //        forAll{(f:Int=>Int)=>{
        //          println("-----------")
        //          println(s"${1}-${f(1)}")
        //          println(s"${1}-${f(1)}")
        //          println(s"${2}-${f(2)}")
        //          println(s"${3}-${f(3)}")
        //        }}

      }
    }

    describe("flatMap") {
      it("should return the result of composed function") {
        val reader1 = Reader((x: Int) => x + 1)
        val result2 = reader1.flatMap((x: Int) => Reader((y: Int) => x + y))
        result2.run(1) should be(3)
        result2.run(2) should be(5)
      }

      it("should obey the associativity law") {

        forAll { (seed: Int, r: Reader[Int, Int], f: Int => Reader[Int, Int], g: Int => Reader[Int, Int]) =>
          r.flatMap(f).flatMap(g).run(seed) should be(r.flatMap(x => f(x).flatMap(g)).run(seed))
        }

      }

      it("should obey the left identity law") {

        forAll { (seed: Int, a: Int, f: Int => Reader[Int, Int]) =>
          Reader.pure(a).flatMap(f).run(seed) should be(f(a).run(seed))
        }

      }

      it("should obey the right identity law") {

        forAll { (seed: Int, r: Reader[Int, Int]) =>
          r.flatMap(Reader.pure).run(seed) should be(r.run(seed))
        }
      }
    }

    describe("ap") {
      it("should obey the compose law") {

        //        forAll { (seed: Int, r: Reader[Int, Int], f: Reader[Int, Int => Int], g: Reader[Int, Int => Int]) =>
        //          g.ap(f.ap(r)).run(seed) should be(g.map(compose).ap(f).ap(r).run(seed))
        //        }
      }
    }

    describe("for") {
      it("should support for expression") {
        val reader = for {
          y <- Reader((x: Int) => x + 1)
          z <- Reader((x: Int) => x + y)
        } yield z

        reader.run(1) should be(3)
        reader.run(2) should be(5)
      }

      it("should read the same dependency") {
        val reader = for {
          x1 <- Reader((x: (Int, Int)) => x._1)
          x2 <- Reader((x: (Int, Int)) => x._2)
        } yield (x1, x2)

        forAll { (x: (Int, Int)) =>
          reader.run(x) should be(x)
        }

      }
    }

  }

}
