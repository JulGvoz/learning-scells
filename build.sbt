ThisBuild / scalaVersion := "2.13.3"

lazy val scells = (project in file("."))
  .settings(
    name := "SCells (Programming in Scala 4th edition)",
    libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.3.0",
  )

scalacOptions := Seq(
  "-unchecked",
  "-deprecation",
  "-feature",
)

cancelable in Global := true