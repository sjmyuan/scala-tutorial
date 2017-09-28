package com.scala.tutorial.codechef

import com.sun.net.httpserver.Authenticator.Success

import scala.collection.immutable.{Range, SortedSet}
import scala.util.{Failure, Success, Try}

/**
  * Created by jiaming.shang on 9/27/17.
  * Problem link: https://www.codechef.com/problems/MINIMAX
  */
object MiniMax {
  def transposition(data: Vector[Vector[Int]]): Vector[Vector[Int]] = {
    val row = data.length
    if (row == 0)
      Vector()
    else
      data.flatten.zipWithIndex.groupBy(_._2 % row).toVector.sortWith(_._1 < _._1).map(_._2.map(_._1))
  }

  def isValidMatrix(data: Vector[Vector[Int]]): Boolean = {
    data.map(_.length).toSet.size == 1
  }

  def minimumChanges(size: Int, data: Vector[Vector[Int]]): Try[Int] = {
    Try({
      if (!isValidMatrix(data)) {
        throw new Exception("The matrix is invalid")
      }
      val rowData = data
      val colData = transposition(data)
      val minRows = rowData.map(_.min)
      val maxCols = colData.map(_.max)
      val maxRow = minRows.max
      val minCol = maxCols.min
      if (maxRow == minCol)
        0
      else {
        val minRowChanges = rowData.map(_.filter(_ < minCol).length).min
        val minColChanges = colData.map(_.filter(_ > maxRow).length).min
        if (minRowChanges < minColChanges) minRowChanges else minColChanges
      }
    })
  }

  def main(args: Array[String]) = {
    println("Please type the matrix size:")
    val size = scala.io.StdIn.readLine().toInt
    println("Please type the matrix:")
    val matrix = Range(0, size).map(x => {
      scala.io.StdIn.readLine().split(" ").map(_.toInt).toVector
    }).toVector

    println("The minium changes:")
    minimumChanges(size, matrix) match {
      case scala.util.Success(num) => println(num)
      case scala.util.Failure(e) => println(e)
    }
  }
}
