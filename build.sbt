import Dependencies._

lazy val `scala-way-month-01` = project
  .settings(Common.settings: _*)
  .settings(libraryDependencies ++=
    compileDep(
      typesafeConfig,
      scalaLogging,
      slf4jLog4j,
      scalaz,
      log4j
    ) ++ testDep(
      scalatest,
      scalactic
    )
  )