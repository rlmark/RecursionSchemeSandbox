name := "RecursionSchemeSandbox"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies := Seq(
  "io.higherkindness"    %% "droste-core"     % "0.6.0",
  "io.higherkindness"    %% "droste-macros"   % "0.6.0",
  compilerPlugin("org.spire-math"  %% "kind-projector" % "0.9.9"),

)
scalacOptions ++= Seq(
  "-encoding", "utf8",
  "-Xfatal-warnings",
  "-Ypartial-unification",
  "-deprecation",
  "-unchecked",
  "-language:implicitConversions",
  "-language:experimental.macros",
  "-language:higherKinds",
  "-language:existentials",
  "-language:postfixOps",
  "-Xplugin-require:macroparadise",
)


resolvers += Resolver.sonatypeRepo("releases")
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)
