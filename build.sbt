scalaVersion := "2.12.3"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "1.0.0",
  "org.scalactic" %% "scalactic" % "3.0.8",
  "org.scalatest" %% "scalatest" % "3.0.8" % "test"
)

scalacOptions ++= Seq(
  "-Xfatal-warnings",
  "-Ypartial-unification",
  "-language:higherKinds",
  "-feature"
)
