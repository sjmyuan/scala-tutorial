package com.scala.tutorial.custom

import com.scala.tutorial.custom.category.monad.IndexedState
import org.scalatest.{FunSpec, Matchers}

class IndexStateSpec extends FunSpec with Matchers {

  describe("IndexState") {
    describe("demo") {
      it("should ensure the order in compile time") {
        sealed trait DoorOps
        case class OPEN() extends DoorOps
        case class CLOSE() extends DoorOps
        case class ENTER() extends DoorOps

        def openDoor: IndexedState[CLOSE, OPEN, Unit] =
          IndexedState.put[CLOSE, OPEN](OPEN())

        def closeDoorBeforeEnter: IndexedState[OPEN, CLOSE, Unit] =
          IndexedState.put[OPEN, CLOSE](CLOSE())

        def closeDoorAfterEnter: IndexedState[ENTER, CLOSE, Unit] =
          IndexedState.put[ENTER, CLOSE](CLOSE())

        def enterDoor: IndexedState[OPEN, ENTER, Unit] =
          IndexedState.put[OPEN, ENTER](ENTER())

        val openClose = for {
          _ <- openDoor
          _ <- closeDoorBeforeEnter
        } yield ()

        val openEnterClose = for {
          _ <- openDoor
          _ <- enterDoor
          _ <- closeDoorAfterEnter
        } yield ()

      }
    }
  }

}
