name := "RecursionSchemeSandbox"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies := Seq(
  "io.higherkindness"    %% "droste-core"     % "0.6.0",
  "io.higherkindness"    %% "droste-macros"   % "0.6.0",
  compilerPlugin("org.spire-math"  %% "kind-projector" % "0.9.9"),

)

