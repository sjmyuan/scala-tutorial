package com.scala.tutorial.codechef

import scala.util.Try

/**
  * Created by jiaming.shang on 9/29/17.
  * Problem links: https://www.codechef.com/problems/CHEFSUM
  */
object LittleChefandSums {
  def miniIndex(data: Vector[Int]): Int = {
    val allSum = data.sum
    Range(2, data.length + 1).foldLeft((data(0) + allSum, 1, data(0) + allSum))(
      (acc, x) => {
        val (minSum, minIndex, currSum) = acc
        val newSum = currSum + data(x - 1) - data(x - 2)

        if (newSum < minSum)
          (newSum, x, newSum)
        else
          (minSum, minIndex, newSum)
      }
    )._2
  }

  def getDataFromConsole(): List[Vector[Int]] = {
    val size = scala.io.StdIn.readLine().toInt

    var data = List[Vector[Int]]()

    Range(0, size).foreach(x => {
      val size = scala.io.StdIn.readLine().toInt
      val caseData = scala.io.StdIn.readLine().split(" ").map(_.toInt).toVector
      assert(size == caseData.length)
      data = data ::: List(caseData)
    })

    data
  }

  def main(args: Array[String]) = {
    val data = getDataFromConsole()
    data.foreach(x => {
      println(miniIndex(x))
    })
  }
}
