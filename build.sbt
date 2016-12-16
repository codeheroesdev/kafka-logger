name := "kafka-logger"
version := "0.1"

enablePlugins(SbtNativePackager)
enablePlugins(JavaAppPackaging)

lazy val `kafka-logger` = project.in(file("."))
  .settings(CommonSettings.commonSettings: _*)
  .settings(resolvers ++= Dependencies.additionalResolvers)
  .settings(excludeDependencies ++= Dependencies.globalExcludes)
  .settings(libraryDependencies ++= Dependencies.projectDependencies)