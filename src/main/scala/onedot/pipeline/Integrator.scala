package onedot.pipeline

import onedot.common.Logging
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.col

class Integrator(df: DataFrame) extends Logging {

  val colsToKeep = Seq("")

  def integrate(): DataFrame = {

    logger.info("start to integrate data")

    val res = df.withColumnRenamed("BodyColorText", "color")
      .withColumnRenamed("MakeText", "make")
      .withColumnRenamed("ModelText", "model")
      .withColumnRenamed("TypeName", "model_variant")
      .withColumnRenamed("City", "city")
      .withColumnRenamed("ConditionTypeText", "condition")
      .withColumnRenamed("extracted-unit-ConsumptionTotalText", "fuel_consumption_unit")
      .select(col("color"), col("condition"), col("make"), col("model"), col("model_variant"), col("city"), col("fuel_consumption_unit"))

    logger.info("end of integrating data")

    res
  }

}
