name := "kafka-logger"
version := "0.1"

lazy val `kafka-logger` = project.in(file("."))
  .settings(CommonSettings.commonSettings: _*)
  .settings(excludeDependencies ++= Dependencies.globalExcludes)
  .settings(libraryDependencies ++= Dependencies.projectDependencies)