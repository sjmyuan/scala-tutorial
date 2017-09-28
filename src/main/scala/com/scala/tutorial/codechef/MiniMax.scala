package com.scala.tutorial.codechef

import com.sun.org.apache.bcel.internal.classfile.SourceFile

import scala.collection.immutable.{Range, SortedSet}
import scala.io.Source
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

  def minimumChanges(data: Vector[Vector[Int]]): Try[Int] = {
    Try {
      if (!isValidMatrix(data)) {
        throw new Exception("The matrix is invalid")
      }

      val rowData = data
      val colData = transposition(data)
      val maxRow = rowData.map(_.min).max
      val minCol = colData.map(_.max).min

      if (maxRow == minCol)
        0
      else {
        val minRowChanges = rowData.map(_.filter(_ < minCol).length).min
        val minColChanges = colData.map(_.filter(_ > maxRow).length).min
        if (minRowChanges < minColChanges) minRowChanges else minColChanges
      }
    }
  }

  def getDataFromConsole(): Try[Vector[Vector[Int]]] = {
    Try {
      println("Please type the matrix size:")
      val size = scala.io.StdIn.readLine().toInt

      println("Please type the matrix:")
      Range(0, size).map(x => {
        scala.io.StdIn.readLine().split(" ").map(_.toInt).toVector
      }).toVector
    }
  }

  def getDataFromFile(file: String): Try[Vector[Vector[Int]]] = {
    Try {
      val lines = Source.fromFile(file).getLines()
      val size = lines.next().trim.toInt
      lines.slice(0, size).map(_.split(" ").map(_.toInt).toVector).toVector
    }
  }

  def main(args: Array[String]) = {
    val data = if (args.length > 0) getDataFromFile(args(0)) else getDataFromConsole()

    data.flatMap(minimumChanges(_)) match {
      case Success(num) => {
        println("The minimum changes is:")
        println(num)
      }
      case Failure(e) => println(e)
    }
  }
}
