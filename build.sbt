import Dependencies._

organization := "com.scala.tutorial"
scalaVersion in ThisBuild := "2.12.3"
version := "0.1.0-SNAPSHOT"
name := "scala-tutorial"
libraryDependencies += scalaTest % Test
libraryDependencies += scalaCheck % Test

val doobieVersion = "0.4.4"
libraryDependencies ++= Seq(
  "org.tpolecat" %% "doobie-core-cats" % doobieVersion,
  "org.tpolecat" %% "doobie-postgres-cats" % doobieVersion,
  "org.tpolecat" %% "doobie-scalatest-cats" % doobieVersion,
  "org.typelevel" %% "cats-effect" % "0.3",
  "org.typelevel" %% "cats-core" % "0.9.0",
  "org.typelevel" %% "cats" % "0.9.0",
  "org.atnos" %% "eff" % "5.3.0",
  "org.atnos" %% "eff-monix" % "5.3.0",
  "org.atnos" %% "eff-cats-effect" % "5.3.0",
  "io.monix" %% "monix" % "2.3.0",
  "io.monix" %% "monix-cats" % "2.3.0",
  "io.circe" %% "circe-parser" % "0.8.0",
  "com.lihaoyi" %% "utest" % "0.6.0" % "test",
  "co.fs2" %% "fs2-core" % "0.9.7",
  "co.fs2" %% "fs2-io" % "0.9.7",
  "com.github.pureconfig" %% "pureconfig" % "0.9.1"
)

testFrameworks += new TestFramework("utest.runner.Framework")

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.4")

initialCommands in console := "import doobie.imports._"

libraryDependencies += "com.lihaoyi" % "ammonite" % "1.0.2" % "test" cross CrossVersion.full

sourceGenerators in Test += Def.task {
  val file = (sourceManaged in Test).value / "amm.scala"
  IO.write(file, """object amm extends App { ammonite.Main().run() }""")
  Seq(file)
}.taskValue

scalacOptions ++= Seq(
  "-language:implicitConversions",
  "-Ypartial-unification"
)
