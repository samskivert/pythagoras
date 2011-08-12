name := "pythagoras"

version := "1.1-SNAPSHOT"

organization := "com.samskivert"

crossPaths := false

javacOptions ++= Seq("-Xlint", "-Xlint:-serial")

autoScalaLibrary := false // no scala-library dependency

libraryDependencies ++= Seq(
  "junit" % "junit" % "4.+" % "test",
  "com.novocode" % "junit-interface" % "0.7" % "test->default"
)

// add our sources to the main jar file
unmanagedResourceDirectories in Compile <+= baseDirectory / "src/main/java"

// work around SBT bug
unmanagedResources in Compile ~= (_.filterNot(_.isDirectory))

// this hackery causes publish-local to install to ~/.m2/repository instead of ~/.ivy
otherResolvers := Seq(Resolver.file("dotM2", file(Path.userHome + "/.m2/repository")))

publishLocalConfiguration <<= (packagedArtifacts, deliverLocal, ivyLoggingLevel) map {
  (arts, _, level) => new PublishConfiguration(None, "dotM2", arts, level)
}
