package org.lemmatization
import scala.collection.JavaConverters._
import com.atlascopco.hunspell.Hunspell

class Lemmatize(dictionaryPath: String, affixPath: String) {

  val speller = new Hunspell(dictionaryPath, affixPath)

  def parseResult(spellerResults: List[String]): String = {
    if (spellerResults.length == 0)
      return ""
    val lemmas = spellerResults.map( res =>
      res.slice(4, res.indexOf("po") -1)
    ).distinct
    lemmas.mkString("+")
  }

  def getLemmas  (s: String): String = {
    if (speller.spell(s))
      return parseResult(speller.analyze(s).asScala.toList)
    ""
  }
}