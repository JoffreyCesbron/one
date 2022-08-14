package onedot.pipeline

import onedot.common.Logging
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{col, when}

class Normalizor(df: DataFrame) extends Logging {

  def normalize(dfMappingColors: DataFrame, dfMappingCars: DataFrame, dfMappingConditions: DataFrame): DataFrame = {

    logger.info("start to normalize data")

    val joinColors = df.join(dfMappingColors, df("BodyColorText") === dfMappingColors("supplierColor"), "left")

    val mappingColors = joinColors.withColumn("BodyColorText", when(col("targetColor").isNull, "Other").otherwise(joinColors("targetColor")))
      .drop("supplierColor", "targetColor")

    val mappingCars = mappingColors.join(dfMappingCars, mappingColors("MakeText") === dfMappingCars("supplierCar"), "left")
      .withColumn("MakeText", col("targetCar"))
      .drop("supplierCar", "targetCar")

    val mappingConditions = mappingCars.join(dfMappingConditions, mappingCars("ConditionTypeText") === dfMappingConditions("supplierCondition"), "left")
      .withColumn("ConditionTypeText", col("targetCondition"))
      .drop("supplierCondition", "targetCondition")

    logger.info("end of normalizing data")

    mappingConditions
  }

}
