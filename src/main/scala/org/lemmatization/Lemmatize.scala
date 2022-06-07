package org.lemmatization

import com.atlascopco.hunspell.Hunspell

import scala.collection.JavaConverters._

class Lemmatize(dictionaryPath: String, affixPath: String) {

  val speller = new Hunspell(dictionaryPath, affixPath)

  def parseResult(spellerResults: List[String]): String = {
    if (spellerResults.length == 0)
      return ""
    val lemmas = spellerResults.map(res =>
      res.slice(4, res.indexOf("po") - 1)
    ).distinct
    lemmas.mkString("+")
  }

  def getLemmas(s: String): String = {
    val sNoSpace = s.trim
    if (speller.spell(sNoSpace))
      return parseResult(speller.analyze(sNoSpace).asScala.toList)
    s"${s} -> does not exist"
  }
}
