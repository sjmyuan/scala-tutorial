package com.scala.tutorial.custom.category.monad

/**
  * Created by jiaming.shang on 9/16/17.
  */
case class State[S,A](run:S=>(S,A)) {
 def map[B](f:((S,A))=>(S,B)):State[S,B] ={
   State(s=>f(run(s)))
 }

  def flatMap[B](f:((S,A))=>State[S,B]):State[S,B] ={
    State(s=>{
      val oldState = run(s)
      f(oldState).run(oldState._1)
    })
  }
}

object State{
}
