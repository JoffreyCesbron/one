package onedot

import onedot.common.Utils.get_args
import onedot.common.{InitSparkJob, Logging}
import onedot.pipeline.Pipeline

object Main extends InitSparkJob with Logging {

  logger.info("application started")
  val startTimeMillis = System.currentTimeMillis()

  val parsedArgs = get_args(args)
  val df = spark.read.option("encoding", "UTF-8").json("/home/joffrey/Desktop/Other/onedot/supplier_car.json")
  val pipepline = new Pipeline(df, parsedArgs.outputDirPath).apply(true)

  val endTimeMillis = System.currentTimeMillis()
  logger.info(s"application finished in ${(endTimeMillis - startTimeMillis) / 1000} seconds")

}
