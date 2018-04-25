scalaVersion := "2.12.4"
name := "aws-ses4s"
organization := "com.jcarver989"
version := "1.0.3"

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0"))



// Repositories

// Java
libraryDependencies += "com.amazonaws" % "aws-java-sdk-ses" % "latest.integration"
libraryDependencies += "com.sun.mail" % "javax.mail" % "latest.integration"
libraryDependencies += "commons-io" % "commons-io" % "latest.integration"

// Tests
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5" % "test"
