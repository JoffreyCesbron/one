package onedot.pipeline

import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.apache.spark.sql.types.StructField
import org.scalatest.FunSuite

class ExtractorTest extends FunSuite with DataFrameSuiteBase {

  import sqlContext.implicits._

  test("should extract the value of the consumption") {

    // Given
    val df = Seq("9.1 l/100km", "11.1 l/100km", "error").toDF("ConsumptionTotalText")

    // When
    val actual = new Extractor(df).extract()

    // Then
    val expected = Seq("9.1", "11.1", "").toDF("exracted-value-ConsumptionTotalText")
    assertDataFrameEquals(actual.select("exracted-value-ConsumptionTotalText"), expected)
  }

  test("should extract the unit of the consumption") {

    // Given
    val df = Seq("9.1 l/100km", "11.1 l/100mi").toDF("ConsumptionTotalText")

    // When
    val actual = new Extractor(df).extract()
    actual.show()

    // Then
    val expected = Seq("l_km_consumption", "l_mi_consumption").toDF("extracted-unit-ConsumptionTotalText")
    assertDataFrameDataEquals(actual.select("extracted-unit-ConsumptionTotalText"), expected)
  }

}
