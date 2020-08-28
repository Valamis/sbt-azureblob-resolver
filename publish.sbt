organization := "com.valamis"
organizationName := "Valamis Oy"
organizationHomepage := Some(url("https://github.com/Valamis"))

scmInfo := Some(
  ScmInfo(
    url("https://github.com/Valamis/sbt-azureblob-resolver"),
        "scm:git@github.com:Valamis/sbt-azureblob-resolver.git"
  )
)

developers := List(
  Developer(
    id    = "lukaci",
    name  = "lukaci",
    email = "lukaci@gmail.com",
    url   = url("https://github.com/lukaci")
  ),
  Developer(
    id    = "pavel-kornilov",
    name  = "pavel-kornilov",
    email = "pavel.kornilov@valamis.com",
    url   = url("https://github.com/pavel-kornilov")
  )
)

description := "Plugin to ease resolving dependencies from and publish to Azure BlobStorage containers, " +
  "using custom url syntax blob:// (default). " +
  "This plugin is a fork of https://github.com/lukaci/sbt-azureblob-resolver with added support for all types of " +
  "credentials - original plugin supports only shared key credentials"
licenses := List("GNU General Public License, Version 3.0" -> new URL("https://www.gnu.org/licenses/gpl.txt"))
homepage := Some(url("https://github.com/Valamis/sbt-azureblob-resolver"))

pomIncludeRepository := { _ => false }
publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
publishMavenStyle := true