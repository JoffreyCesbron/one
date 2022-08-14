package onedot.pipeline

import onedot.common.Logging
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{col, regexp_extract, when}

class Extractor(df: DataFrame) extends Logging {

  def
  extract(): DataFrame = {

    logger.info("start to extract data")

    val res = df
      .withColumn("exracted-value-ConsumptionTotalText", regexp_extract(col("ConsumptionTotalText"), "[\\d,]+\\.\\d+", 0))
      .withColumn("extracted-unit-ConsumptionTotalText",
        when(col("ConsumptionTotalText").contains("l/100km"), "l_km_consumption")
          .otherwise(when(col("ConsumptionTotalText").contains("l/100mi"), "l_mi_consumption").otherwise("null")))

    logger.info("end of extracting data")

    res
  }

}
