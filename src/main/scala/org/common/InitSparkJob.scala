package org.common

import org.apache.spark.sql.SparkSession


abstract class InitSparkJob extends App {

  val spark = SparkSession.builder()
    .master("local[1]")
    .appName("lemmatization")
    .getOrCreate();

  implicit val _spark = spark

}
