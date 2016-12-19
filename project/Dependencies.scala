import DependencyVersions._
import sbt._

object Dependencies {

  val globalExcludes = Seq(
    SbtExclusionRule("log4j"),
    SbtExclusionRule("log4j2"),
    SbtExclusionRule("commons-logging")
  )

  val projectDependencies = Seq(
    "ch.qos.logback" % "logback-classic" % logbackVersion,
    "com.github.danielwegener" % "logback-kafka-appender" % kafkaAppenderVersion,
    "org.json4s" %% "json4s-native" % json4sVersion
  )

}