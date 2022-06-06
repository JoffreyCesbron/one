package org.common

object Utils extends Logging {
  case class Arguments(language: String = "", dictionariesPath: String = "", inputDirPath: String = "", outputDir: String = "")

  def get_args(args: Array[String]): Arguments = {
    val parser = new scopt.OptionParser[Arguments]("Parsing application") {

      opt[String]('l', "language").
        required().valueName("").action((value, arguments) => arguments.copy(language = value))

      opt[String]('d', "dictionariesFolderPath").
        required().valueName("").action((value, arguments) => arguments.copy(dictionariesPath = value))

      opt[String]('i', "inputDirPath").
        required().valueName("").action((value, arguments) => arguments.copy(inputDirPath = value))

      opt[String]('o', "outputDirPath").
        required().valueName("").action((value, arguments) => arguments.copy(outputDir = value))

    }
    parser.parse(args, Arguments()) map { config =>
      logger.info(s"language chosen par the user is <${config.language}>")
      logger.info(s"dictionaries folder is <${config.dictionariesPath}>")
      logger.info(s"csv input file to lemmatize is <${config.inputDirPath}>")
      logger.info(s"output path to lemmatize is <${config.outputDir}>")
      return config
    } getOrElse {
      throw new Exception("some arguments are missing")
    }
  }
}
