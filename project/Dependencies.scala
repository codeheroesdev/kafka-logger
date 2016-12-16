import DependencyVersions._
import sbt._

object Dependencies {

  val globalExcludes = Seq(
    SbtExclusionRule("log4j"),
    SbtExclusionRule("log4j2"),
    SbtExclusionRule("commons-logging")
  )

  val loggingDependencies = Seq(
    "ch.qos.logback" % "logback-classic" % logbackVersion,
    "ch.qos.logback" % "logback-core" % logbackVersion,
    "org.slf4j" % "jcl-over-slf4j" % slf4jVersion,
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    "com.github.danielwegener" % "logback-kafka-appender" % "0.1.0"
  )

  val jsonDependencies = Seq(
    "org.json4s" %% "json4s-native" % json4sVersion
  )

  val projectDependencies = Seq(
    loggingDependencies,
    jsonDependencies
  ).reduce(_ ++ _)

  val additionalResolvers = Seq(
    "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"
  )

}