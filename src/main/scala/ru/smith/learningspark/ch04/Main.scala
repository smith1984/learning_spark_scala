package ru.smith.learningspark.ch04

import org.apache.spark.{SparkConf, SparkContext}

object Main extends App{

  val conf = new SparkConf().setAppName("Main")
  val sc = new SparkContext(conf)

  val lines = sc.parallelize(List("hello world", "hi"))
  val pairs = lines.map (x => (x.split(" ")(0), x))
  pairs.collect().foreach(println)

  pairs.filter{case (key, value) => value.length < 20}

  val input = sc.textFile("test.txt")
  val words = input.flatMap(x => x.split(" "))
  val result = words.map(x => (x, 1)).reduceByKey((x,y) => x + y)
  result.collect().foreach(println)

  val result_ = result.combineByKey((v) => (v,1),
    (acc: (Int,Int), v) => (acc._1 + v, acc._2 + 1),
    (acc1: (Int,Int), acc2: (Int,Int)) => (acc1._1 + acc2._1, acc1._2 + acc2._2))
      .map{case (key,value) => (key, value._1 / value._2.toFloat)}
  result_.collectAsMap().foreach(println(_))

  sc.stop()
}
