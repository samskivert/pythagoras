name := "pythagoras"

version := "1.1-SNAPSHOT"

organization := "com.samskivert"

crossPaths := false

javacOptions ++= Seq("-Xlint", "-Xlint:-serial")

libraryDependencies ++= Seq(
  "junit" % "junit" % "4.+" % "test",
  "com.novocode" % "junit-interface" % "0.7" % "test->default"
)
