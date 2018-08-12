package com.scala.tutorial.codechef

/**
  * The direcly solution
  *   1. get the all the possible spell, the spell don't need to care about order
  *   2. check if there is bob
  *
  * we can't use string to stand for the spell, because they have order
  * we can use one pattern, for example
  * "abc" <==> List((1,"a"),(1,"b"),(1,"c"))
  * "aac" <==> List((2,"a"),(1,"c"))
  *
  * now we want "bob", then we just need to find
  * List((2,"b"),(1,"c"))
  * v2:
  *   1. first we can check if it is possible happen 2b,1o, no matter if b,c in one card
  * then we can filter the most case
  *   2. so how do we check the impossible case
  */
object SpellBob {
  def getDataFromConsole(): List[List[(Char, Char)]] = {
    val size = scala.io.StdIn.readLine().toInt
    Range(0, size).map(x => {
      val top = scala.io.StdIn.readLine().toList
      val bottom = scala.io.StdIn.readLine().toList
      top.zip(bottom)
    }).toList
  }

  def canSpellBob(v: List[(Char, Char)]): Boolean = {
    val head :: tail = v
    val allPattern = tail.foldLeft(List[Map[Char, Int]](Map(head._1 -> 1), Map(head._2 -> 1)))((acc, ele) => {
      for {
        p <- acc
        oldTop = p.getOrElse(ele._1, 0)
        oldBottom = p.getOrElse(ele._2, 0)
        newP <- List(p + (ele._1 -> (oldTop + 1)), p + (ele._2 -> (oldBottom + 1)))
      } yield newP
    })
    allPattern.contains(Map[Char, Int]('b' -> 2, 'o' -> 1))
  }

  def main(args: Array[String]) = {
    val input = getDataFromConsole()
    //    val start = System.currentTimeMillis()
    input.map(canSpellBob).map(if (_) "yes" else "no").foreach(println(_))
    //    val end = System.currentTimeMillis()
    //    println(s"Time: ${end-start}")
  }
}
