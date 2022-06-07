package org.lemmatization

import org.scalatest.FunSuite

class LemmatizeTest extends FunSuite {

  val lemmatize = new Lemmatize(getClass.getResource("/french/french.dic").getPath, getClass.getResource("/french/french.aff").getPath)

  test("should parse the result") {
    // Given
    val resultSpeller = List(" st:livre po:nom is:epi is:sg")
    // When
    val actual = lemmatize.parseResult(resultSpeller)
    // Then
    assert(actual == "livre")
  }

  test("should return all lemmas for a word") {
    // Given
    val mot = "livre"
    // When
    val actual = lemmatize.getLemmas(mot)
    // Then
    assert(actual == "livre+livrer")
  }

  test("should return all lemmas for a word with a space") {
    // Given
    val mot = "livre "
    // When
    val actual = lemmatize.getLemmas(mot)
    // Then
    assert(actual == "livre+livrer")
  }

  test("should return the word when no lemma") {
    // Given
    val mot = "auteur"
    // When
    val actual = lemmatize.getLemmas(mot)
    // Then
    assert(actual == "auteur")
  }

  test("should return the word if does not exist") {
    // Given
    val mot = "aaaaa"
    // When
    val actual = lemmatize.getLemmas(mot)
    // Then
    assert(actual == "aaaaa -> does not exist")
  }
}
