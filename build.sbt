ThisBuild / scalaVersion := "2.13.3"

lazy val scells = (project in file("."))
  .settings(
    name := "SCells (Programming in Scala 4th edition)",
    libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.3.0",
    libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "2.1.1",
    libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
  )

scalacOptions := Seq(
  "-unchecked",
  "-deprecation",
  "-feature",
)

cancelable in Global := true