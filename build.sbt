import Dependencies._

lazy val `scala-way-month-01` = project
  .settings(Common.settings: _*)
  .settings(libraryDependencies ++=
    compileDep(
      typesafeConfig,
      scalaLogging,
      slf4jLog4j,
      log4j
    ) ++ testDep(
      scalatest,
      scalactic
    ))
  .settings(scalastyleSettings)

lazy val testScalastyle: TaskKey[Unit] = taskKey[Unit]("testScalastyle")
lazy val compileScalastyle: TaskKey[Unit] = taskKey[Unit]("verifyScalastyle")

lazy val scalastyleSettings = Seq(
  compileScalastyle := scalastyle.in(Compile).toTask("").value,
  (compile in Compile) := ((compile in Compile) dependsOn compileScalastyle).value,
  testScalastyle := scalastyle.in(Test).toTask("").value,
  (test in Test) := ((test in Test) dependsOn testScalastyle).value
)