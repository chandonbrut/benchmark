name := "Benchmark"
version := "1.0"
scalaVersion := "2.12.2"
lazy val root = (project in file(".")).enablePlugins(PlayScala)

herokuAppName in Compile := "lrit-rdc-benchmark"

libraryDependencies += guice
