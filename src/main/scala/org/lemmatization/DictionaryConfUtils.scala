package org.lemmatization

import org.common.Logging

import scala.reflect.io.File

case class DictionaryConf(language: String, dicFile: String, affFile: String)

final case class DirectoryMissingException(private val message: String = "", private val cause: Throwable = None.orNull) extends Exception(message, cause)

final case class DirectoryLanguageMissingException(private val message: String = "", private val cause: Throwable = None.orNull) extends Exception(message, cause)

final case class FileLanguageException(private val message: String = "", private val cause: Throwable = None.orNull) extends Exception(message, cause)

object DictionaryUtils extends Logging {

  def getConfDictionary(language: String, dictionaryPath: String): DictionaryConf = {
    logger.info(s"starting getting all files conf for <${language}>")

    if (!File(dictionaryPath).exists) {
      throw DirectoryMissingException(s"the directory <${dictionaryPath}> does not exist")
    }

    val specificDictionaryPath = f"${dictionaryPath}${language}"
    if (!File(specificDictionaryPath).exists) {
      throw DirectoryLanguageMissingException(s"the directory <${specificDictionaryPath}> does not exist")
    }

    val absoluteDicPath = s"${specificDictionaryPath}/${language}.dic"
    if (!File(absoluteDicPath).exists) {
      throw FileLanguageException(s"the file <${language}.dic> does not exist in ${specificDictionaryPath}")
    }

    val absoluteAffPath = s"${specificDictionaryPath}/${language}.aff"
    if (!File(absoluteAffPath).exists) {
      throw FileLanguageException(s"the file <${language}.aff> does not exist in ${specificDictionaryPath}")
    }
    logger.info(s"all files are present for <${language}>")

    DictionaryConf(language, absoluteDicPath, absoluteAffPath)
  }
}


