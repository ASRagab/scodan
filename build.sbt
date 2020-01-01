name := "scodan"

version := "0.1-SNAPSHOT"

scalaVersion := "2.13.1"

scalacOptions ++= Seq("-language:postfixOps")

val sttpClient = "com.softwaremill.sttp.client"
val sttpVersion = "2.0.0-RC5"

val circe = "io.circe"
val circeVersion = "0.12.3"

lazy val circeDependencies = Seq(
  circe %% "circe-core",
  circe %% "circe-generic",
  circe %% "circe-parser"
).map(_ % circeVersion)

lazy val sttpDependencies = Seq(
  sttpClient %% "core",
  sttpClient %% "circe",
  sttpClient %% "async-http-client-backend-fs2",
  sttpClient %% "async-http-client-backend-cats",
  sttpClient %% "async-http-client-backend-future"
).map(_ % sttpVersion)

lazy val otherDependencies = Seq(
  "org.http4s" %% "jawn-fs2" % "0.15.0"
)

lazy val testDependencies = Seq(
  "org.scalatest" %% "scalatest" % "3.2.0-M2" % Test
)

libraryDependencies ++= sttpDependencies ++ circeDependencies ++ otherDependencies ++ testDependencies