package org.lemmatization

import org.lemmatization.DictionaryUtils.getConfDictionary
import org.scalatest.FunSuite

class DictionaryUtilsTest extends FunSuite {

  test("should get the config for the french dictionary") {
    // Given
    val language = "french"
    val dictionariesPath = getClass.getResource("/").getPath
    // When
    val actual = getConfDictionary(language, dictionariesPath)
    // Then
    assert(actual.language == language)
    assert(actual.dicFile == s"${dictionariesPath}french/french.dic")
    assert(actual.affFile == s"${dictionariesPath}french/french.aff")
  }

  test("should get an error when the main directory of the dictionaries does not exist") {
    // Given
    val language = ""
    val dictionariesPath = "doesNotExist"
    // When Then
    assertThrows[DirectoryMissingException] {
      getConfDictionary(language, dictionariesPath)
    }
  }

  test("should get an error when the directory of the chosen language does not exist") {
    // Given
    val language = "italian"
    val dictionariesPath = getClass.getResource("/").getPath
    // When Then
    assertThrows[DirectoryLanguageMissingException] {
      getConfDictionary(language, dictionariesPath)
    }
  }

  test("should get an error when a conf file for a dictionary does not exist") {
    // Given
    val language = "english"
    val dictionariesPath = getClass.getResource("/").getPath
    // When Then
    assertThrows[FileLanguageException] {
      getConfDictionary(language, dictionariesPath)
    }
  }


}