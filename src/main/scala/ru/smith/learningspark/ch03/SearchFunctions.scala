package ru.smith.learningspark.ch03

import org.apache.spark.rdd.RDD

class SearchFunctions(val query : String) {

//  def isMatch (s:String): Boolean = {
//    s.contains(query)
//  }
//
//  def getMatchesFunctionReference(rdd : RDD[String]): RDD[String] = rdd.map(isMatch)
//
//  def getMatchesFieldReference(rdd: RDD[String]): RDD[String] = {
//    rdd.map(x => x.split(query))
//  }

  def getMatchesNoReference(rdd: RDD[String]): RDD[String] = {
    val query_ = this.query
    rdd.flatMap(x => x.split(query_))
  }
}

