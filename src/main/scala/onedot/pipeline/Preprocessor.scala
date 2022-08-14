package onedot.pipeline


import onedot.common.Logging
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._

class Preprocessor(df: DataFrame) extends Logging {

  def preprocess(): DataFrame = {

    logger.info("start to preprocess data")
    val window = Window.partitionBy(col("ID"))
    val windowNames = df.withColumn("names", collect_list(col("Attribute Names")).over(window))
    val windowValues = windowNames.withColumn("values", collect_list(col("Attribute Values")).over(window))
    val windowDistinct = windowValues.drop("Attribute Values", "Attribute Names", "entity_id").distinct()
    val dfmapZip = windowDistinct.withColumn("map", map_from_arrays(col("names"), col("values")))
    val dfmapZipDistinct = dfmapZip
    val keysDF = dfmapZipDistinct.select(explode(map_keys(col("map")))).distinct()
    val keys = keysDF.collect().map(f => f.get(0))
    val keyCols = keys.map(f => col("map").getItem(f).as(f.toString))

    logger.info("end of preprocessing data")


    dfmapZipDistinct.select(col("ID") +: col("MakeText") +: col("TypeName") +: col("TypeNameFull") +: col("ModelText") +: col("ModelTypeText") +: keyCols: _*)
      .withColumn("Properties", regexp_replace(col("Properties"), "\"", ""))
  }


}
