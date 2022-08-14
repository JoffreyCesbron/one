package onedot.common

object Utils extends Logging {
  case class Arguments(inputFilePath: String = "", outputDirPath: String = "")

  def get_args(args: Array[String]): Arguments = {
    val parser = new scopt.OptionParser[Arguments]("Parsing application") {

      opt[String]('i', "inputFilePath").
        required().valueName("").action((value, arguments) => arguments.copy(inputFilePath = value))

      opt[String]('o', "outputDirPath").
        required().valueName("").action((value, arguments) => arguments.copy(outputDirPath = value))

    }
    parser.parse(args, Arguments()) map { config =>
      logger.info(s"the input path file is <${config.inputFilePath}>")
      logger.info(s"output directory is <${config.outputDirPath}>")
      return config
    } getOrElse {
      throw new Exception("some arguments are missing")
    }
  }
}
