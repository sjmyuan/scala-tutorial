package com.scala.tutorial.codechef

/**
  * Created by jiaming.shang on 9/30/17.
  * Problem links: https://www.codechef.com/problems/UNIVERSE
  * Dijkstra algorithm: https://zh.wikipedia.org/wiki/%E6%88%B4%E5%85%8B%E6%96%AF%E7%89%B9%E6%8B%89%E7%AE%97%E6%B3%95
  */
object Year3017 {

//  case class Tunnel(planet1: Int, planet2: Int)
//
//  case class Teleport(planet1: Planet, planet2: Planet)
//
//  case class Planet(id: Int, universe: Int, dis: Long = Long.MaxValue, prev: Option[Planet] = None)
//
//  def dijkstra(planetInUniverse: Int,
//               tunnels: Vector[Tunnel],
//               teleports: Vector[Teleport],
//               start: Planet,
//               end: Planet): Some[Long] = {
//    val begin = start.copy(dis = 0)
//    val planets = constructPlanetSet(start,
//      calculateUniverseNum(teleports, start, end),
//      planetInUniverse)
//    while (planets.nonEmpty){
//
//    }
//  }
//
//  def calculateUniverseNum(teleports: Vector[Teleport],
//                           start: Planet,
//                           end: Planet): Int = {
//    val universeId = teleports.map(x => Set(x.planet1.universe, x.planet2.universe))
//    (universeId ++ Vector(Set(start.universe, end.universe)).flatten).size
//  }
//
//  def constructPlanetSet(start: Planet, universe: Int, planetInUniverse: Int): Vector[Planet] = {
//    val planets = for {
//      uid <- 1 to universe
//      pid <- 1 to planetInUniverse
//      planet = Planet(pid, uid)
//      if planet != start
//    } yield planet
//
//    planets.toVector
//  }
}
