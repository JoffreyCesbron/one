package org.common

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession


abstract class InitSparkJob extends App{


  val spark = SparkSession.builder()
    .master("local[1]")
    .appName("SparkByExample")
    .getOrCreate();

  implicit val _spark = spark

}
