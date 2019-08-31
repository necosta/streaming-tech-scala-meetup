lazy val root = (project in file("."))
  .settings(
    name := "kafka-streams",
    scalaVersion := "2.12.8"
  )

scalafmtOnCompile in ThisBuild := true
scalafmtTestOnCompile in ThisBuild := true
scalafmtFailTest in ThisBuild := true

// https://mvnrepository.com/artifact/org.apache.kafka/kafka-streams
libraryDependencies ++= Seq(
  "org.apache.kafka" %% "kafka" % "2.3.0",
  "org.apache.kafka" % "kafka-streams" % "2.3.0",
  "org.slf4j" % "slf4j-api" % "1.7.25",
  "org.slf4j" % "slf4j-log4j12" % "1.7.25"
)

val testDependencies = Seq(
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "org.apache.kafka" % "kafka-streams-test-utils" % "2.3.0" % "test")

libraryDependencies ++= testDependencies
