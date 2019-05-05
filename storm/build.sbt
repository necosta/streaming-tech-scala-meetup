lazy val root = (project in file("."))
  .settings(
    name := "storm",
    scalaVersion := "2.12.8"
  )

scalafmtOnCompile in ThisBuild := true
scalafmtTestOnCompile in ThisBuild := true
scalafmtFailTest in ThisBuild := true
