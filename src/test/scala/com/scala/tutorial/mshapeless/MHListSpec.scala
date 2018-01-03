package com.scala.tutorial.mshapeless

import utest._
import MHListOps._

object MHListSpec extends TestSuite {
  def tests = Tests{
    'MHNil - {
      "should print MHNil" - {
        MHList().toString ==> "MHNil"
        MHNil.toString ==> "MHNil"
      }
    }

    ':: - {
      "should print 1::MHNil"-{
        MHList(1).toString ==> "1::MHNil"
      }
      "should print 1::test::MHNil" -{
        val hlist:Int::String::MHNil = 1::"test"::MHNil
        hlist.toString() ==> "1::test::MHNil"
      }
    }
  }
}
