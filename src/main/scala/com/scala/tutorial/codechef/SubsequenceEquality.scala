package com.scala.tutorial.codechef

/**
  * Created by jiaming.shang on 9/30/17.
  * Problem links: https://www.codechef.com/problems/LIKECS01
  */
object SubsequenceEquality {
  def check(data:String):Boolean={
    data.zipWithIndex.groupBy(_._1).map(x=>x._2.map(_._2)).filter(x=>{
      x.length>1
    }).size>0
  }

  def getDataFromConsole(): List[String] = {
    val size = scala.io.StdIn.readLine().toInt

    var data = List[String]()

    Range(0, size).foreach(x => {
      val caseData = scala.io.StdIn.readLine()
      data = data ::: List(caseData)
    })

    data
  }

  def main(args: Array[String]) = {
    val data = getDataFromConsole()
    data.map(check(_)).foreach(x=> if (x) println("yes") else println("no"))
  }
}
