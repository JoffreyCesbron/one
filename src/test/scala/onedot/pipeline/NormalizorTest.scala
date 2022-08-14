package onedot.pipeline

import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.scalatest.FunSuite

class NormalizorTest extends FunSuite with DataFrameSuiteBase {

  import sqlContext.implicits._

  test("should extract the value of the consumption") {

    // Given
    val df = Seq(("schwarz", "BMW", "Neu")).toDF("BodyColorText", "MakeText", "ConditionTypeText")
    val dfMappingColors = spark.read.option("delimiter", ";").option("header", "true").csv(getClass.getResource("/mappingColors.csv").toString)
    val dfMappingCars = spark.read.option("delimiter", ";").option("header", "true").csv(getClass.getResource("/mappingCars.csv").toString)
    val dfMappingConditions = spark.read.option("delimiter", ";").option("header", "true").csv(getClass.getResource("/mappingConditions.csv").toString)

    // When
    val actual = new Normalizor(df).normalize(dfMappingColors, dfMappingCars, dfMappingConditions)

    // Then
    val expected = Seq(("Black", "Bmw", "New")).toDF("BodyColorText", "MakeText", "ConditionTypeText")
    assertDataFrameEquals(actual, expected)
  }

}
