import sbt.Keys._


object CommonSettings {
  val scalaVer = "2.11.7"

  val commonSettings = Seq(
    scalaVersion := scalaVer,
    scalacOptions ++= Seq(
      "-encoding", "UTF-8"
    ),
    fork := true
  )
}