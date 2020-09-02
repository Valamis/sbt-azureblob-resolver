sbtPlugin := true

name         := "sbt-azureblob-resolver"
organization := "com.valamis"
version      := "0.10.0"

scalaVersion := "2.12.10"
sbtVersion   := "1.3.13"

libraryDependencies += "com.microsoft.azure" % "azure-storage-blob" % "10.5.0"
