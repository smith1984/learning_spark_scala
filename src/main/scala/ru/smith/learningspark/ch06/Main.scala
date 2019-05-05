package ru.smith.learningspark.ch06

import org.apache.spark.{SparkConf, SparkContext}

object Main extends App{

  val conf = new SparkConf().setAppName("Main")
  val sc = new SparkContext(conf)

  val file = sc.textFile("callsigns")
  val blankLines = sc.accumulator(0);
  val callSigns = file.flatMap(line => {
    if (line == "")
      blankLines +=1
    line.split(" ")
  })

  callSigns.saveAsTextFile("output")
  println("Blank lines: " +blankLines.value)

  sc.stop()
}
