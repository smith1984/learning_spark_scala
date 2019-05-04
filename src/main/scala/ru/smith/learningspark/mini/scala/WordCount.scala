package ru.smith.learningspark.mini.scala

import org.apache.spark.{SparkConf, SparkContext}

object WordCount extends App {
  val conf = new SparkConf().setAppName("wordCount")
  val sc = new SparkContext(conf)
  val input = sc.textFile(args(0))
  val words = input.flatMap(line => line.split(" "))
  val counts = words.map(x => (x,1)).reduceByKey{case (x,y) => x+y}
  counts.saveAsTextFile(args(1))
}
