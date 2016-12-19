name := "kafka-logger"
version := "0.2"

bintrayOrganization := Some("codeheroes")
bintrayPackage := "kafka-logger"
licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

lazy val `kafka-logger` = project.in(file("."))
  .settings(CommonSettings.commonSettings: _*)
  .settings(excludeDependencies ++= Dependencies.globalExcludes)
  .settings(libraryDependencies ++= Dependencies.projectDependencies)
