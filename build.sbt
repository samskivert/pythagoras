name := "pythagoras"

version := "1.1-SNAPSHOT"

organization := "com.samskivert"

crossPaths := false

javacOptions ++= Seq(
  "-Xlint", "-Xlint:-serial", "-source", "1.6", "-target", "1.6"
)

autoScalaLibrary := false // no scala-library dependency

libraryDependencies ++= Seq(
  "junit" % "junit" % "4.+" % "test",
  "com.novocode" % "junit-interface" % "0.7" % "test->default"
)

// filter the super-source directory from the build
unmanagedSources in Compile ~= (_.filterNot(_.getPath.indexOf("pythagoras/gwt") != -1))

// add our sources to the main jar file (including super-sources)
unmanagedResourceDirectories in Compile <+= baseDirectory / "src/main/java"

// work around SBT bug
unmanagedResources in Compile ~= (_.filterNot(_.isDirectory))

// this hackery causes publish-local to install to ~/.m2/repository instead of ~/.ivy
otherResolvers := Seq(Resolver.file("dotM2", file(Path.userHome + "/.m2/repository")))

publishLocalConfiguration <<= (packagedArtifacts, deliverLocal, ivyLoggingLevel) map {
  (arts, _, level) => new PublishConfiguration(None, "dotM2", arts, level)
}
