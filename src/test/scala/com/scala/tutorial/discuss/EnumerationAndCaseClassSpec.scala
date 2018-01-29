package com.scala.tutorial.discuss

import io.circe.Decoder
import org.scalatest.{FunSpec, Matchers}
import io.circe.parser._

class EnumerationAndCaseClassSpec extends FunSpec with Matchers {
  describe("CaseClass") {
    describe("Requirement1") {
      sealed trait Animal
      final case object Cat extends Animal
      final case class NotAnimal(value: String) extends Animal
      object Animal {
        def apply(value: String): Animal = value match {
          case "Cat" => Cat
          case _ => NotAnimal(value)
        }
      }

      def process(v: Either[io.circe.Error, Animal]): String = v match {
        case Right(Cat) => "This is cat"
        case Right(NotAnimal(v)) => s"${v} is not a animal"
        case Left(_) => "Error"
      }

      it("should return This is cat when the type is Cat") {
        val json =
          """
            |{
            | "type":"Cat"
            |}
          """.stripMargin

        val result = for {
          root <- parse(json)
          animal <- root.hcursor.get[String]("type")
        } yield Animal(animal)

        process(result) should be("This is cat")
      }

      it("should return Apple is not a animal when the type is Apple") {
        val json =
          """
            |{
            | "type":"Apple"
            |}
          """.stripMargin

        val result = for {
          root <- parse(json)
          animal <- root.hcursor.get[String]("type")
        } yield Animal(animal)

        process(result) should be("Apple is not a animal")
      }
    }

    describe("Requirement2--add dog") {
      sealed trait Animal
      final case object Cat extends Animal
      final case object Dog extends Animal
      final case class NotAnimal(value: String) extends Animal
      object Animal {
        def apply(value: String): Animal = value match {
          case "Cat" => Cat
          case "Dog" => Dog
          case _ => NotAnimal(value)
        }
      }

      def process(v: Either[io.circe.Error, Animal]): String = v match {
        case Right(Cat) => "This is cat"
        case Right(Dog) => "This is dog"
        case Right(NotAnimal(v)) => s"${v} is not a animal"
        case Left(_) => "Error"
      }

      //      def process(v: Either[io.circe.Error, Animal]): String = v match {
      //        case Right(Cat) => "This is cat"
      //        case Right(NotAnimal(v)) => s"${v} is not a animal"
      //        case Left(_) => "Error"
      //      }

      it("should return This is cat when the type is Cat") {
        val json =
          """
            |{
            | "type":"Cat"
            |}
          """.stripMargin

        val result = for {
          root <- parse(json)
          animal <- root.hcursor.get[String]("type")
        } yield Animal(animal)

        process(result) should be("This is cat")
      }

      it("should return This is dog when the type is Dog") {
        val json =
          """
            |{
            | "type":"Dog"
            |}
          """.stripMargin

        val result = for {
          root <- parse(json)
          animal <- root.hcursor.get[String]("type")
        } yield Animal(animal)

        process(result) should be("This is dog")
      }

      it("should return Apple is not a animal when the type is Apple") {
        val json =
          """
            |{
            | "type":"Apple"
            |}
          """.stripMargin

        val result = for {
          root <- parse(json)
          animal <- root.hcursor.get[String]("type")
        } yield Animal(animal)

        process(result) should be("Apple is not a animal")
      }
    }

    describe("Requirement3--add meta data") {
      sealed abstract class Animal(val legNum:Int)
      final case object Cat extends Animal(4)
      final case class NotAnimal(value: String) extends Animal(-1)
      object Animal {
        def apply(value: String): Animal = value match {
          case "Cat" => Cat
          case _ => NotAnimal(value)
        }
      }

      def process(v: Either[io.circe.Error, Animal]): String = v match {
        case Right(Cat) => s"This is cat with ${Cat.legNum} legs"
        case Right(NotAnimal(v)) => s"${v} is not a animal"
        case Left(_) => "Error"
      }

      it("should return This is cat when the type is Cat") {
        val json =
          """
            |{
            | "type":"Cat"
            |}
          """.stripMargin

        val result = for {
          root <- parse(json)
          animal <- root.hcursor.get[String]("type")
        } yield Animal(animal)

        process(result) should be("This is cat with 4 legs")
      }

      it("should return Apple is not a animal when the type is Apple") {
        val json =
          """
            |{
            | "type":"Apple"
            |}
          """.stripMargin

        val result = for {
          root <- parse(json)
          animal <- root.hcursor.get[String]("type")
        } yield Animal(animal)

        process(result) should be("Apple is not a animal")
      }
    }
  }

  describe("Enumeration") {

    describe("Requirement1") {
      object Animal extends Enumeration {
        val Cat = Value("Cat")
      }

      def process(v: Either[io.circe.Error, Animal.Value]): String = v match {
        case Right(Animal.Cat) => "This is cat"
        case Right(v@_) => s"${v} is not a animal"
        case Left(v@_) => s"Error:${v}"
      }

      it("should return This is cat when the type is Cat") {
        val json =
          """
            |{
            | "type":"Cat"
            |}
          """.stripMargin

        implicit val decoder: Decoder[Animal.Value] = Decoder.enumDecoder(Animal)

        val result = for {
          root <- parse(json)
          animal <- root.hcursor.get[Animal.Value]("type")
        } yield animal

        process(result) should be("This is cat")
      }

      ignore("should return Apple is not a animal when the type is Apple") {
        val json =
          """
            |{
            | "type":"Apple"
            |}
          """.stripMargin

        implicit val decoder: Decoder[Animal.Value] = Decoder.enumDecoder(Animal)

        val result = for {
          root <- parse(json)
          animal <- root.hcursor.get[Animal.Value]("type")
        } yield animal

        process(result) should be("This is not a animal")
      }
    }

    describe("Requirement2--add dog") {
      object Animal extends Enumeration {
        val Cat = Value("Cat")
        val Dog = Value("Dog")
      }

//      def process(v: Either[io.circe.Error, Animal.Value]): String = v match {
//        case Right(Animal.Cat) => "This is cat"
//        case Right(v@_) => s"${v} is not a animal"
//        case Left(v@_) => s"Error:${v}"
//      }

      def process(v: Either[io.circe.Error, Animal.Value]): String = v match {
        case Right(Animal.Cat) => "This is cat"
        case Right(Animal.Dog) => "This is dog"
        case Right(v@_) => s"${v} is not a animal"
        case Left(v@_) => s"Error:${v}"
      }

      it("should return This is cat when the type is Cat") {
        val json =
          """
            |{
            | "type":"Cat"
            |}
          """.stripMargin

        implicit val decoder: Decoder[Animal.Value] = Decoder.enumDecoder(Animal)

        val result = for {
          root <- parse(json)
          animal <- root.hcursor.get[Animal.Value]("type")
        } yield animal

        process(result) should be("This is cat")
      }

      it("should return This is dog when the type is Dog") {
        val json =
          """
            |{
            | "type":"Dog"
            |}
          """.stripMargin

        implicit val decoder: Decoder[Animal.Value] = Decoder.enumDecoder(Animal)

        val result = for {
          root <- parse(json)
          animal <- root.hcursor.get[Animal.Value]("type")
        } yield animal

        process(result) should be("This is dog")
      }

      ignore("should return Apple is not a animal when the type is Apple") {
        val json =
          """
            |{
            | "type":"Apple"
            |}
          """.stripMargin

        implicit val decoder: Decoder[Animal.Value] = Decoder.enumDecoder(Animal)

        val result = for {
          root <- parse(json)
          animal <- root.hcursor.get[Animal.Value]("type")
        } yield animal

        process(result) should be("This is not a animal")
      }
    }
  }
}
