package com.scala.tutorial.eff

import cats.data._
import com.scala.tutorial.eff.GlobalType.ErrorOr
import com.scala.tutorial.eff.Step.HasStep
import org.atnos.eff._

object GlobalType {
  type ErrorOr[T] = Either[String, T]
}

trait Step[F]

case class Login(name: String, password: String) extends Step[ErrorOr[String]]

case class Process(session: String) extends Step[ErrorOr[Boolean]]

case class Logout(session: String) extends Step[ErrorOr[Boolean]]

object Step {
  type HasStep[T] = MemberIn[Step, T]

  def login[R: HasStep](name: String, password: String): Eff[R, ErrorOr[String]] = {
    Eff.send(Login(name, password))
  }

  def process[R: HasStep](session: String): Eff[R, ErrorOr[Boolean]] = {
    Eff.send(Process(session))
  }

  def logout[R: HasStep](session: String): Eff[R, ErrorOr[Boolean]] = {
    Eff.send(Logout(session))
  }
}

object StepInterpreter {
  def runStep[R, A, U: Member.Aux[Step, R, ?]](eff: Eff[R, A]): Eff[U, A] = Interpret.translate[R, U, Step, A](eff)(
    new Translate[Step, U] {
      override def apply[X](kv: Step[X]): Eff[U, X] = {
        kv match {
          case Login(u, p) => Eff.pure(Right("session"))
          case Process(s) => Eff.pure(Right(true))
          case Logout(s) => Eff.pure(Right(true))
        }
      }
    })

  implicit class StepInterpreterOps[R, A](eff: Eff[R, A]) {
    def runStep[U: Member.Aux[Step, R, ?]](): Eff[U, A] = {
      StepInterpreter.runStep(eff)
    }
  }

}
