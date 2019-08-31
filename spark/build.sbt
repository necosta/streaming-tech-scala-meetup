lazy val root = (project in file("."))
  .settings(
    name := "spark-streaming",
    scalaVersion := "2.12.8"
  )

scalafmtOnCompile in ThisBuild := true
scalafmtTestOnCompile in ThisBuild := true
scalafmtFailTest in ThisBuild := true

// https://mvnrepository.com/artifact/org.apache.spark/spark-streaming
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-streaming" % "2.4.4",
  "org.apache.spark" %% "spark-sql" % "2.4.4",
  "org.apache.spark" %% "spark-sql-kafka-0-10" % "2.4.4")
