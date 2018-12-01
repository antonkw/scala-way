import sbt.Keys._
import sbt._
import sbtassembly.AssemblyKeys._
import sbtassembly.AssemblyPlugin.autoImport.assembly
import sbtassembly._

object Common {

  val settings: Seq[Def.Setting[_]] = Seq(
    organization := "io.antonkw",
    scalaVersion := "2.11.11",
    assemblyMergeStrategy in assembly := {
      case PathList("META-INF", _*) => MergeStrategy.discard
      case "reference.conf" => MergeStrategy.concat
      case x => MergeStrategy.first
    }
  )

}
