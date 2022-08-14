package onedot.pipeline

import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.scalatest.FunSuite

class IntegratorTest extends FunSuite with DataFrameSuiteBase {

  import sqlContext.implicits._

  test("should extract the value of the consumption") {

    // Given
    val df = Seq((null, null, null, null, null, null, null, null)).toDF("BodyColorText", "MakeText", "ModelText", "TypeName", "City", "extracted-unit-ConsumptionTotalText", "condition", "test")

    // When
    val actual = new Integrator(df).integrate()

    // Then
    val expected = Seq((null, null, null, null, null, null, null)).toDF("color", "condition", "make", "model", "model_variant", "city", "fuel_consumption_unit")
    assertDataFrameEquals(actual, expected)
  }

}
