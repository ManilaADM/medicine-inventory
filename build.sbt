name := "medicine"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "org.mockito" % "mockito-core" % "1.9.5",
  "org.mongodb" % "mongo-java-driver" % "2.11.3",
  "uk.co.panaxiom" %% "play-jongo" % "0.6.0-jongo0.4",
  "de.undercouch" % "bson4jackson" % "2.1.0" force(),
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.1.0" force(),
  "com.fasterxml.jackson.core" % "jackson-annotations" % "2.1.0" force(),
  "com.fasterxml.jackson.core" % "jackson-core" % "2.1.0" force(),
  "org.mongodb" % "mongo-java-driver" % "2.11.3",
  "org.jongo" % "jongo" % "0.4",
  "uk.co.panaxiom" %% "play-jongo" % "0.6.0-jongo0.4",
  "se.radley" %% "play-plugins-salat" % "1.3.0"
)     

play.Project.playJavaSettings

scalacOptions += "-language:reflectiveCalls"

routesImport += "se.radley.plugin.salat.Binders._"

templatesImport += "org.bson.types.ObjectId"
