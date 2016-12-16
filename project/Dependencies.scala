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

  val akkaDependencies = Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
  )

  val akkaHttpDependencies = Seq(
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-http-core" % akkaVersion,
    "com.typesafe.akka" %% "akka-http-experimental" % akkaVersion,
    "de.heikoseeberger" %% "akka-http-json4s" % akkaJson4sVersion,
    "org.json4s" %% "json4s-core" % json4sVersion,
    "org.json4s" %% "json4s-jackson" % json4sVersion
  )

  val testDependencies = Seq(
    "org.scalactic" %% "scalactic" % "2.2.6",
    "org.scalatest" %% "scalatest" % "2.2.6" % "test",
    "com.typesafe.akka" %% "akka-http-testkit" % akkaVersion % "test"
  )

  val devopsDependencies = Seq(
    "akka-http-lb" %% "akka-http-lb" % "0.1",
    "microhero" %% "microhero" % "0.1"
  )

  val projectDependencies = Seq(
    loggingDependencies,
    akkaDependencies,
    akkaHttpDependencies,
    testDependencies,
    devopsDependencies
  ).reduce(_ ++ _)

  val additionalResolvers = Seq(
    "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/",
    "RoundEights" at "http://maven.spikemark.net/roundeights",
    Resolver.bintrayRepo("codeheroes", "maven")
  )

}