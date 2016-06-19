name := "Soranin2"

version := "1.0"

scalaVersion := "2.11.8"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-Ylog-classpath")

libraryDependencies ++= Seq(
  "com.github.scala-incubator.io" %% "scala-io-core" % "0.4.3",
  "com.github.scala-incubator.io" %% "scala-io-file" % "0.4.3",
  "ch.qos.logback" %  "logback-classic" % "1.1.7",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.4.0",
  "net.databinder.dispatch" %% "dispatch-core" % "0.11.2"
)
//"net.debasishg" % "sjson_2.9.1" % "0.15",
//"org.scala-tools.time" % "time_2.9.1" % "0.5",


