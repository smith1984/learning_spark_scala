package ru.smith.learningspark.ch09

//import org.apache.spark.sql.hive.HiveContext
//import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

object Main extends App{

//  val conf = new SparkConf().setAppName("Main")
//  val sc = new SparkContext(conf)
//
//  val hiveContext = new HiveContext(sc)
//  val inputHive = hiveContext.jsonFile("./files/testweet.json")
//
//  val SQLContext = new SQLContext(sc)
//  val inputSQLCtx = SQLContext.jsonFile("./files/testweet.json")
//  inputSQLCtx.registerTempTable("tweets")
//  val topTweets = SQLContext.sql("SELECT text, retweetCount FROM tweets ORDER BY retweetCount LIMIT 10")
//  topTweets.show()
// sc.stop()

  val sparkSession = SparkSession.builder().appName("Main").getOrCreate()
  import sparkSession.implicits._
  val input = sparkSession.read.json("./files/testweet.json")
  input.show()
  input.createOrReplaceTempView("tweets")
  val topTweets = sparkSession.sql("SELECT text, retweetCount FROM tweets ORDER BY retweetCount LIMIT 10")
  topTweets.show()
  val topTweetText = topTweets.map(row => row.getString(0))
  topTweetText.show()
  sparkSession.udf.register("strLenScala", (_ : String).length)
  val tweetLength = sparkSession.sql("SELECT strLenScala(text) from tweets")
  tweetLength.show()
  sparkSession.close()

}
