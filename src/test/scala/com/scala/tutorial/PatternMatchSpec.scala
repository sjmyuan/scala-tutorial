package com.scala.tutorial

import org.scalatest._

/**
  * Created by jiaming.shang on 8/13/17.
  */
class PatternMatchSpec extends FunSpec with Matchers {

  describe("Pattern Match") {
    describe("Match Type") {
      it("can match literal value") {

      }
      it("can match variable") {

      }
      it("can match variable argument lists") {

      }
      it("can not match type parameter") {

      }
      it("can match case class") {

      }
      it("can match nested type") {

      }
      it("can match sequence") {

      }
      it("can match regex") {

      }
      it("can match custom class") {

      }
    }
    describe("Match Operation") {
      it("can add guard") {
        val odd = 3 match {
          case x if x % 2 == 0 => 0
          case y if y % 2 == 1 => 1
          case _ => -1
        }
        odd should be(1)
      }
      it("can binding variable") {
        val list = List(List(1, 2), 2)
        val head = list match {
          case List(h@List(_, _), _) => h
          case _ => null
        }

        head should be(List(1, 2))
      }

      describe("Variable Assignment") {
        describe("for Sequence") {
          val list = List(1, 2)
          it("can use +:") {
            val head +: tail = list
            head should be(1)
            tail should be(List(2))
          }

          it("can use :+") {
            val head :+ tail = list
            head should be(List(1))
            tail should be(2)
          }

          it("can extract nested content") {
            val List(head) +: tail = List(List(1), 2)
            head should be(1)
            tail should be(List(2))
          }

          describe("using constructor format") {
            it("can assign content to variable") {
              val List(head, tail) = list
              head should be(1)
              tail should be(2)
            }

            it("can use the _ wildchar") {
              val List(head, second, _) = List(1, 2, 3)
              head should be(1)
              second should be(2)
            }

            it("should contains the exact number of content") {
              an[MatchError] should be thrownBy {
                val List(head, tail) = List(1, 2, 3)
              }
            }
          }

        }
        describe("for Tuple") {
          val tuple = (1, 2, 3)
          it("can extract nested content") {
            val ((first, second), third) = ((1, 2), 3)
            first should be(1)
            second should be(2)
            third should be(3)
          }

          it("can assign content to variable") {
            val (head, second, third) = tuple
            head should be(1)
            second should be(2)
            third should be(3)
          }

          it("can use the _ wildchar") {
            val (head, second, _) = tuple
            head should be(1)
            second should be(2)
          }
        }
        describe("for Case Class") {
          case class Address(info: String)
          case class Person(name: String, address: Address)

          val person = Person("Joe", Address("RichMond"))

          it("can extract nested content") {
            val Person(name, Address(info)) = person
            name should be("Joe")
            info should be("RichMond")
          }

          it("can use the _ wildchar") {
            val Person(name, _) = person
            name should be("Joe")
          }
        }
      }

      describe("for Expression") {
        it("can use _ wildchar") {
          val result = for {
            List(x: Int, _) <- List(List(1, 2), 3)
          } yield {
            x
          }
          result should be(List(1))
        }
      }

      describe("if expression") {
        it("can not use _ wildchar") {
          //          val result = if ((1,_:Int) == (1,2)) 1 else 0
        }
      }
    }
  }

}
