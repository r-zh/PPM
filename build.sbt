// enable improved (experimental) incremental compilation algorithm called "name hashing"
// https://groups.google.com/forum/#!searchin/play-framework/compile$20slow/play-framework/S_-wYW5Tcvw/NcyZZ1xI_g4J
incOptions := incOptions.value.withNameHashing(true)

organization := "cn.edu.sdu.qlsc.sc"

name := "spepms"

version := "0.5.0-SNAPSHOT"

libraryDependencies ++= Seq(
  cache,
  javaJpa,
  "org.hibernate" % "hibernate-entitymanager" % "4.2.5.Final",
  "com.googlecode.genericdao" % "dao" % "1.2.0",
  "com.googlecode.genericdao" % "search-jpa-hibernate" % "1.2.0",
  "mysql" % "mysql-connector-java" % "5.1.19",
    // workaround of https://github.com/playframework/playframework/issues/1966
    "org.javassist" % "javassist" % "3.18.2-GA"
)

def customLessEntryPoints(base: File): PathFinder = (
   (base / "app" / "assets" / "libs" / "bootstrap" / "css" * "bootstrap.less") +++
   (base / "app" / "assets" / "stylesheets" / "main.less")
)

play.Project.playJavaSettings ++ lesscSettings

lessEntryPoints := Nil

lesscEntryPoints in Compile <<= baseDirectory(customLessEntryPoints)
