lazy val root = (project in file("."))
  .settings(
    name := "spark-streaming",
    scalaVersion := "2.12.8"
  )

scalafmtOnCompile in ThisBuild := true
scalafmtTestOnCompile in ThisBuild := true
scalafmtFailTest in ThisBuild := true

val sparkVersion = "2.4.3"

// https://mvnrepository.com/artifact/org.apache.spark/spark-streaming
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-sql-kafka-0-10" % sparkVersion)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _ *) => MergeStrategy.discard
  case x => MergeStrategy.first
}
