import play.Project._

name := """savet4"""

version := "2.2.2"

libraryDependencies ++= Seq(
  // WebJars infrastructure
  "org.webjars" % "webjars-locator" % "0.13"
  ,"org.webjars" %% "webjars-play" % "2.2.1-2"
  // WebJars dependencies
  ,"org.webjars" % "underscorejs" % "1.6.0-1"
  ,"org.webjars" % "jquery" % "1.11.0-1"
  ,"org.webjars" % "bootstrap" % "3.1.1" exclude("org.webjars", "jquery")
  ,"org.webjars" % "angularjs" % "1.2.14" exclude("org.webjars", "jquery")
  ,"org.webjars" % "angular-ui-bootstrap" % "0.11.0-2"
  ,jdbc      // The JDBC connection pool and the play.api.db API
  ,anorm     // Scala RDBMS Library
  ,cache     // Play API cache
  ,"org.json4s" %% "json4s-native" % "3.2.9"
  ,"com.typesafe.slick" %% "slick" % "2.0.0"
)

playScalaSettings

resolvers += "typesafe" at "http://repo.typesafe.com/typesafe/repo"

// This tells Play to optimize this file and its dependencies
requireJs += "main.js"

// The main config file
// See http://requirejs.org/docs/optimization.html#mainConfigFile
requireJsShim := "build.js"

// To completely override the optimization process, use this config option:
//requireNativePath := Some("node r.js -o name=main out=javascript-min/main.min.js")

// Coffeescript works without this for AngularJS, no need to be "bare"
//coffeescriptOptions := Seq("bare")
