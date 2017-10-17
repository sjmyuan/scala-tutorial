package com.scala.tutorial

import org.scalatest.{FunSpec, Matchers}
import doobie.imports._
import doobie.imports._
import cats._, cats.data._, cats.implicits._
import fs2.interop.cats._
/**
  * Created by jiaming.shang on 10/11/17.
  */
class DoobieSpec extends FunSpec with Matchers {

  describe("doobie") {
    it("can connect to postgresql") {
      val xa = DriverManagerTransactor[IOLite](
        "org.postgresql.Driver", "jdbc:postgresql://localhost/postgres", "postgres", "test"
      )

      val program:ConnectionIO[Int] = sql"select 10".query[Int].unique
      val result = program.transact(xa).unsafePerformIO

      println(result)

    }
  }

}
