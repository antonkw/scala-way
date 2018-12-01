import sbt._

object Dependencies {

  def compileDep(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "compile")

  def providedDep(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "provided")

  def testDep(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "test")

  def runtimeDep(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "runtime")

  def containerDep(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "container")

  val SlickVersion = "3.2.1"
  val slick: ModuleID = "com.typesafe.slick" %% "slick" % SlickVersion
  val slickHikariCp: ModuleID = "com.typesafe.slick" %% "slick-hikaricp" % SlickVersion

  val typesafeConfigVersion = "1.3.2"
  val typesafeConfig: ModuleID = "com.typesafe" % "config" % typesafeConfigVersion

  val avroVersion = "1.8.2"
  val avro = "org.apache.avro" % "avro" % avroVersion

  val ficusVersion = "1.4.3"
  val ficus = "com.iheart" %% "ficus" % ficusVersion

  val scalazVersion = "7.2.20"
  val scalaz = "org.scalaz" %% "scalaz-core" % scalazVersion

  val ScalaLoggingVersion = "3.7.2"
  val scalaLogging: ModuleID = "com.typesafe.scala-logging" %% "scala-logging" % ScalaLoggingVersion

  val ScalatestVersion = "3.0.3"
  val scalatest: ModuleID = "org.scalatest" %% "scalatest" % ScalatestVersion
  val scalactic: ModuleID = "org.scalactic" %% "scalactic" % ScalatestVersion

  val Slf4jVersion = "1.7.25"
  val slf4jApi: ModuleID = "org.slf4j" % "slf4j-api" % Slf4jVersion
  val slf4jNop: ModuleID = "org.slf4j" % "slf4j-nop" % Slf4jVersion
  val slf4jLog4j: ModuleID = "org.slf4j" % "slf4j-log4j12" % Slf4jVersion

  val Log4jVersion = "1.2.17"
  val log4j: ModuleID = "log4j" % "log4j" % Log4jVersion
}
