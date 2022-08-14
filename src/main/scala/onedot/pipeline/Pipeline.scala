package onedot.pipeline

import onedot.Main.spark
import onedot.common.Logging
import org.apache.spark.sql.{DataFrame, SaveMode}

class Pipeline(df: DataFrame, outputDir: String) extends Logging {

  def savePipelineStep(df: DataFrame, name: String) = {
    df.coalesce(1).write.option("delimiter", ";").mode(SaveMode.Overwrite).option("header", true).csv(s"${outputDir}/${name}")
  }

  def apply(saveMode: Boolean) = {

    logger.info("starting pipeline")
    val dfMappingColors = spark.read.option("delimiter", ";").option("header", "true").csv(getClass.getResource("/mappingColors.csv").toString)
    val dfMappingCars = spark.read.option("delimiter", ";").option("header", "true").csv(getClass.getResource("/mappingCars.csv").toString)
    val dfMappingConditions = spark.read.option("delimiter", ";").option("header", "true").csv(getClass.getResource("/mappingConditions.csv").toString)

    val preprocessing = new Preprocessor(df).preprocess()
    val normalizing = new Normalizor(preprocessing).normalize(dfMappingColors, dfMappingCars, dfMappingConditions)
    val extracting = new Extractor(normalizing).extract()
    val integrating = new Integrator(extracting).integrate()

    if (saveMode) {
      logger.info("start saving files")
      savePipelineStep(preprocessing, s"pre-processing")
      savePipelineStep(normalizing, s"normalisation")
      savePipelineStep(extracting, s"extraction")
      savePipelineStep(integrating, s"integration")
      logger.info("end saving files")
    }
    logger.info("ending pipeline")
  }

}

