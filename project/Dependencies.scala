import DependencyVersions._
import sbt._

object Dependencies {

  val globalExcludes = Seq(
    SbtExclusionRule("log4j"),
    SbtExclusionRule("log4j2"),
    SbtExclusionRule("commons-logging")
  )

  val testDependencies = Seq(
    "org.scalatest" %% "scalatest" % "2.2.6" % "test",
    "net.manub" % "scalatest-embedded-kafka_2.11" % "0.10.0" % "test",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0" % "test",
    "org.slf4j" % "log4j-over-slf4j" % "1.7.22" % "test",
    "de.heikoseeberger" %% "akka-http-json4s" % akkaJson4sVersion % "test"
  )

  val projectDependencies = Seq(
    "ch.qos.logback" % "logback-classic" % logbackVersion,
    "com.github.danielwegener" % "logback-kafka-appender" % kafkaAppenderVersion,
    "org.json4s" %% "json4s-native" % json4sVersion
  ) ++ testDependencies

}