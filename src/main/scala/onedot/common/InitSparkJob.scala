package onedot.common

import org.apache.spark.sql.SparkSession


abstract class InitSparkJob extends App {

  val spark = SparkSession.builder()
    .master("local")
    .appName("onedot")
    .getOrCreate();

  implicit val _spark = spark

}
