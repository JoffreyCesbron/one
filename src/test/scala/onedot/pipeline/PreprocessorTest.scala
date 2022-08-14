package onedot.pipeline


import org.scalatest.FunSuite
import com.holdenkarau.spark.testing.{DataFrameSuiteBase, SharedSparkContext}
import com.holdenkarau.spark.testing
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{StringType, StructField, StructType}


class PreprocessorTest extends FunSuite with DataFrameSuiteBase {

  import sqlContext.implicits._

  test("should get the same granularity as the target with several attribute names") {

    // Given
    val df = Seq(
      ("1", null,null,null,null,null, "Properties", "value1"),
      ("1", null,null,null,null,null, "Seats", "value2"))
      .toDF("ID", "MakeText", "TypeName", "TypeNameFull", "ModelText", "ModelTypeText", "Attribute Names", "Attribute Values")

    // When
    val actual = new Preprocessor(df).preprocess()

    // Then
    val expected = Seq(("1", null,null,null,null,null, "value2", "value1")).toDF("ID", "MakeText", "TypeName", "TypeNameFull", "ModelText", "ModelTypeText", "Seats", "Properties")
    assertDataFrameEquals(actual, expected)
  }

}
