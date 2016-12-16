import sbt.Keys._


object CommonSettings {
  val scalaVer = "2.11.7"

  val commonSettings = Seq(
    javaOptions ++= Seq(
      "-Xms128M",
      "-Xmx256M",
      "-XX:MaxPermSize=128M",
      "-XX:PermSize=128M",
      "-server",

      "-XX:+UseConcMarkSweepGC",
      "-XX:+UseCMSInitiatingOccupancyOnly",
      "-XX:CMSInitiatingOccupancyFraction=70",
      "-XX:+CMSParallelRemarkEnabled",

      "-verbose:gc",
      "-XX:+PrintGCDetails",
      "-Xloggc:/var/log/kafka-logger/gc.log",
      "-XX:+UseGCLogFileRotation",
      "-XX:NumberOfGCLogFiles=10",
      "-XX:GCLogFileSize=100M",

      "-XX:+HeapDumpOnOutOfMemoryError",
      "-XX:HeapDumpPath=/var/log/kafka-logger/gc-dump-`date`.hprof"
    ),
    scalaVersion := scalaVer,
    scalacOptions ++= Seq(
      "-encoding", "UTF-8"
    ),
    fork := true
  )
}