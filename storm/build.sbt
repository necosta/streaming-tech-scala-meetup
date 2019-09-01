lazy val root = (project in file("."))
  .settings(
    name := "storm",
    scalaVersion := "2.12.8"
  )

scalafmtOnCompile in ThisBuild := true
scalafmtTestOnCompile in ThisBuild := true
scalafmtFailTest in ThisBuild := true

resolvers += "clojars-packages" at "https://clojars.org/repo/"

// https://mvnrepository.com/artifact/org.apache.storm/storm-core
libraryDependencies ++= Seq(
  "org.apache.storm" % "storm-core" % "2.0.0" % "provided",
  "org.apache.storm" % "storm-kafka-client" % "2.0.0",
  "org.apache.kafka" % "kafka-clients" % "2.3.0",
)

//package foo contains object and package with same name: supervisor
//one of them needs to be removed from classpath
scalacOptions += "-Yresolve-term-conflict:package"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _ *) => MergeStrategy.discard
  case x => MergeStrategy.first
}
