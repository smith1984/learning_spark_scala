package ru.smith.learningspark.find_avro

import org.apache.spark.{SparkConf, SparkContext}


object Main extends App {
  val conf = new SparkConf().setAppName("wordCount")
  val sc = new SparkContext(conf)
  val src = "/home/smith/test/dml"
  val trg = "/home/smith/test/dml1"
  val tmpTableName = "tmpTable"


  val sqlContext = new org.apache.spark.sql.SQLContext(sc)

  var df = sqlContext.read.format("avro").load(src)
  df.count()

  df.registerTempTable(tmpTableName)
  val c = df.columns.toList

  val sqlQuery = "SELECT * FROM " + tmpTableName.toString + " WHERE athelete NOT IN  ('Michael Phelps') UNION ALL SELECT " + c.mkString(", ").replace("age", "99 as age")+ " FROM " + tmpTableName.toString + " WHERE athelete = 'Michael Phelps' and age = 19"

  val res = sqlContext.sql(sqlQuery)
  res.show()
  res.coalesce(4).write.format("avro").save(trg)

  df = sqlContext.read.format("avro").load(trg)
  df.count()

  sc.stop()
}