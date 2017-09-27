package com.scala.tutorial.codechef

import scala.collection.immutable.SortedSet

/**
  * Created by jiaming.shang on 9/27/17.
  * Problem link: https://www.codechef.com/problems/MINIMAX
  */
object MiniMax {
  def transposition(data: Vector[Vector[Int]]): Vector[Vector[Int]] = {
    val row = data.length
    val col = data(0).length
    data.flatten.zipWithIndex.groupBy(_._2 % row).toVector.sortWith(_._1<_._1).map(_._2.map(_._1))
  }

  def minimumChanges(size: Int, data: Vector[Vector[Int]]): Int = {
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
  }
}
