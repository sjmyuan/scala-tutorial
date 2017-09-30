package com.scala.tutorial.codechef

import scala.util.Try

/**
  * Created by jiaming.shang on 9/29/17.
  * Problem links: https://www.codechef.com/problems/CHEFSUM
  */
object LittleChefandSums {
  def miniIndex(data: Vector[Int]): Int = {
    val allSum = data.sum
    Range(2, data.length + 1).foldLeft((data(0), allSum, data(0) + allSum, 1))(
      (sum, x) => {
        val (leftSum, rightSum, currSum, currIndex) = sum
        val newLeftSum = leftSum + data(x - 1)
        val newRightSum = rightSum - data(x - 2)
        val newAllSum = newLeftSum + newRightSum

        if (newAllSum < currSum)
          (newLeftSum, newRightSum, newAllSum, x)
        else
          (newLeftSum, newRightSum, currSum, currIndex)
      }
    )._4
  }

  def getDataFromConsole(): List[Vector[Int]] = {
    val size = scala.io.StdIn.readLine().toInt

    var data = List[Vector[Int]]()

    Range(0, size).foreach(x => {
      scala.io.StdIn.readLine().toInt
      data = scala.io.StdIn.readLine().split(" ").map(_.toInt).toVector :: data
    })

    data.reverse
  }

  def main(args: Array[String]) = {
    val data = getDataFromConsole()
    data.foreach(x => {
      println(miniIndex(x))
    })
  }
}
