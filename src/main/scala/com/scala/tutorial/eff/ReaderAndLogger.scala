package com.scala.tutorial.eff

import cats.data._
import com.scala.tutorial.eff.GlobalType.ErrorOr
import com.scala.tutorial.eff.Step.HasStep
import org.atnos.eff._

object GlobalType{
  type ErrorOr[T] = Either[String,T]
}

trait Step[F]
case class Login(name:String,password:String) extends Step[ErrorOr[String]]
case class Process(session:String) extends Step[ErrorOr[Boolean]]
case class Logout(session:String) extends Step[ErrorOr[Boolean]]

object Step{
  type HasStep[T] = MemberIn[Step,T]

  def login[R:HasStep](name:String, password:String):Eff[R,ErrorOr[String]]={
    Eff.send(Login(name,password))
  }

  def process[R:HasStep](session:String):Eff[R,ErrorOr[Boolean]]={
    Eff.send(Process(session))
  }

  def logout[R:HasStep](session:String):Eff[R,ErrorOr[Boolean]]={
    Eff.send(Logout(session))
  }
}

object StepInterpreter{
   def runStep[R:HasStep,A,U:Member.Aux[HasStep,R,?], B](eff:Eff[R,A]):Eff[U,B] = Interpret.translate(eff)(
    new Translate[Step,U] {
     override def apply[X](kv: Step[X]): Eff[R, X] = {
       kv match {
         case Login(u,p) => Eff.pure(Right("session")).asInstanceOf[Eff[R,X]]
         case Process(s) => Eff.pure(Right(true)).asInstanceOf[Eff[R,X]]
         case Logout(s) => Eff.pure(Right(true)).asInstanceOf[Eff[R,X]]
       }
     }
   })

  implicit class StepInterpreterOps[R:HasStep,A](eff:Eff[R,A]){
    def runStep[U:Member.Aux[HasStep,R,?]]():Eff[U,A]={
      StepInterpreter.runStep(eff)
    }
  }
}
