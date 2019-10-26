name := "learning_spark_scala"

version := "0.1"

scalaVersion := "2.11.12"
libraryDependencies ++=Seq("org.apache.spark" %% "spark-core" % "2.4.3" % "provided"
  , "org.apache.spark" %% "spark-sql" % "2.4.3" % "provided"
//  , "org.apache.spark" %% "spark-hive" % "2.4.3",
//  , "org.apache.spark" %% "spark-avro" % "2.4.3" % "provided"
)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
