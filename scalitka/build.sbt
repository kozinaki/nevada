name := "scalitka"

//version := "0.1"

scalaVersion := "2.13.1"

resolvers += JavaNet2Repository
resolvers += Resolver.jcenterRepo

libraryDependencies += "net.dv8tion" % "JDA" % "4.1.1_138"
libraryDependencies += "com.github.docker-java" % "docker-java" % "3.2.1"
libraryDependencies += "org.glassfish.jersey.inject" % "jersey-hk2" % "2.28"

retrieveManaged := true

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.singleOrError
  case x => MergeStrategy.first
}


/*lazy val app = (project in file("app")).
  settings(commonSettings: _*).
  settings(
    mainClass in assembly := Some("com.example.Main"),
    // more settings here ...
  )

lazy val utils = (project in file("utils")).
  settings(commonSettings: _*).
  settings(
    assemblyJarName in assembly := "utils.jar",
    // more settings here ...
  )*/