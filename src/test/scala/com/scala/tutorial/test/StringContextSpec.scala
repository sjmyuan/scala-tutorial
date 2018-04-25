package com.scala.tutorial.test

import org.scalatest.{FunSpec, Matchers}


object StringContextSpec {

  // sql"ListingId=${ListingId} And CampaignActiveDate = ${activeDate}"
  implicit class SqlHelper(private val sc: StringContext) extends AnyVal {
    def sql(args: Any*): String = sc.parts.toString() + args.toString()
  }

}

class StringContextSpec extends FunSpec with Matchers {

  import StringContextSpec._

  describe("StringContext") {
    it("should be able to handle all the part in string") {

      println(sql"ListingId = ${"hello"} And CampaignActiveDate = ${"world"}")
    }
  }

}
