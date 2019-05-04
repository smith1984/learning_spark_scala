package ru.smith.learningspark.ch03

import org.apache.spark.{SparkConf, SparkContext}

object Main extends App{

  val conf = new SparkConf().setAppName("Main")
  val sc = new SparkContext(conf)

  val input = sc.textFile(args(0))
  val error = input.filter(s => s.contains("error"))
  val warning = input.filter(s => s.contains("warning"))
  val badLines = error.union(warning)

  println(badLines.count())
  badLines.take(10).foreach(println)

  val badLines_ = new SearchFunctions(" ").getMatchesNoReference(input)
  println("badLines_")
  badLines_.take(10).foreach(println)

  val input_ = sc.parallelize(List(1, 2, 3, 4))
  val result = input_.map(x => x * x)
  println(result.collect().mkString(","))

  val lines = sc.parallelize(List("hello world", "hi"))
  val words = lines.flatMap(l => l.split(" "))
  println(words.first())

  val sum = input_.reduce((x,y) => x + y)
  println(sum)

  val result_ = input_.aggregate((0,0))((acc, value) => (acc._1 + value, acc._2 + 1),
  (acc1, acc2) => (acc1._1 + acc2._1, acc1._2 + acc2._2))
  val avg = result_._1 / result_._2.toDouble
  println(avg)

  sc.stop()
}
