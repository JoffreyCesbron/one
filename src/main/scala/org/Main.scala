package org

import org.apache.spark.sql.functions.{col, udf}
import org.common.Utils.get_args
import org.common.{InitSparkJob, Logging}
import org.lemmatization.{DictionaryUtils, Lemmatize}

object Main extends InitSparkJob with Logging {

  logger.info("application started")
  val startTimeMillis = System.currentTimeMillis()

  val parsedArgs = get_args(args)
  val confDictionary = DictionaryUtils.getConfDictionary(parsedArgs.language, parsedArgs.dictionariesPath)

  val colWords = "words"
  val df = spark.read.csv(parsedArgs.inputDirPath).toDF(colWords)
  val countDf = df.count
  logger.info(s"the input file contains <${countDf}> words")

  val dfNoDuplicates = df.distinct()
  val countDfNoDuplicates = dfNoDuplicates.count()
  logger.info(s"after deduplication the input file contain <${countDfNoDuplicates}> words")

  val lemmatize = new Lemmatize(confDictionary.dicFile, confDictionary.affFile)
  val convertUDF = udf(lemmatize.getLemmas _)
  logger.info(s"end of lemmatization")

  val colOutput = "output"
  val dfWithLemmas = dfNoDuplicates.select(col(colWords), convertUDF(col(colWords)).as(colOutput))

  val nonExistingWord = dfWithLemmas.filter(dfWithLemmas(colOutput).contains("->")).count()
  val wordWithLema = dfWithLemmas.filter(dfWithLemmas(colOutput).contains("+")).count()

  logger.info(s"non existing word is <${nonExistingWord}>, words with at least one lemma is <${wordWithLema}>, existing word with no lema is <${countDfNoDuplicates - wordWithLema - nonExistingWord}>, <${countDfNoDuplicates}> total words with no duplicates")

  dfWithLemmas.coalesce(1).select(colOutput).write.mode("overwrite").csv(parsedArgs.outputDir)

  val endTimeMillis = System.currentTimeMillis()
  logger.info(s"application finished in ${(endTimeMillis - startTimeMillis) / 1000} seconds")

}
