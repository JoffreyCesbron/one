package onedot.common

import org.apache.log4j.{Level, Logger}

trait Logging {
  val logger: Logger = Logger.getLogger("onedot")
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.spark.storage.BlockManager").setLevel(Level.ERROR)
  logger.setLevel(Level.INFO)

}
