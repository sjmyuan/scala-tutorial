package com.scala.tutorial

import org.scalatest._

/**
  * Created by jiaming.shang on 8/10/17.
  */
class ClassSpec extends FunSpec with Matchers {
  describe("Class") {
    it("can define multiple constructor") {
      class Person(val firstName: String, val lastName: String, val age: Int) {

        def this(firstName: String) {
          this(firstName, "", 0);
          println("\nNo last name or age given.")
        }

        def this(firstName: String, lastName: String) {
          this(firstName, lastName, 0);
          println("\nNo age given.")
        }

        override def toString: String = {
          return "%s %s, age %d".format(firstName, lastName, age)
        }

      }

      println(new Person("Shang","Jiaming",20))
      println(new Person("Shang"))
      println(new Person("Shang","Jiaming"))
    }

    it("can define static functions") {
      class Person
      object Person{
        def sayHi(): Unit ={
          println("Hi,I'm a person")
        }
      }

      Person.sayHi()
    }

    it("can define getter and setter") {
      class Person(private var myName:String){
        def name=myName
        def name_=(name:String) ={
          myName=name
        }

        override def toString: String = {
          return "my name is %s".format(myName)
        }
      }

      val person = new Person("Jim")
      println(person.name)
      println(person)

      person.name="Joe"
      println(person.name)
      println(person)

    }

    it("body code execute before constructor"){
      class C1(var state:Int){
        println("I'm in class body")
        def this(){
          this(0)
          println("I'm in constructor")
        }
      }

      new C1()
    }

    it("can implement abstract factory pattern"){
      abstract class Person{
        def sayHi()
      }

      class FirstPerson extends Person{
        def sayHi(): Unit ={
          println("Hi, I'm first person")
        }
      }

      class SecondPerson extends Person{
        def sayHi(): Unit = {
          println("Hi, I'm second person")
        }
      }

      object Person{
        def apply(number:Int):Person = {
          number match {
            case 1 => new FirstPerson
            case 2 => new SecondPerson
            case _ => throw new NotImplementedError()
          }
        }
      }

      Person(1).sayHi()
      Person(2).sayHi()
    }
    it("can implement singleton in traditional way"){
      class Counter private (var state:Int) {
        def count ={
          state+=1
          println(s"Current count is ${state}")
        }
      }

      object Counter{
        private var counter:Counter = null
        def apply()={
          if(counter==null){
            counter=new Counter(0)
          }
          counter
        }
      }

      Counter().count
      Counter().count
      Counter().count
    }

    it("can implement singleton only by object"){
      object Counter{
        private var state = 0
        def count ={
          state+=1
          println(s"Current count is ${state}")
        }
      }

      Counter.count
      Counter.count
      Counter.count
    }

    describe("Using Self-Type Annotations"){
      it("can invoke parent class method in nested class"){
        class C1{ self =>
          def talk(msg:String) = println(s"C1.talk: ${msg}")

          class C2 {
            class C3 {
              def talk(msg:String) = self.talk(s"C3.talk: ${msg}")
            }
            val c3 = new C3
          }

          val c2=new C2
        }

        println(new C1().talk("Hello"))
        println(new C1().c2.c3.talk("Hello"))
      }
    }
  }
}
