package etlmail.context

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FlatSpec

@RunWith(classOf[JUnitRunner])
class PropertyBuilderTest extends FlatSpec with ShouldMatchers {
  "one key-value pair" should "result in a properties object with one entry" in {
    // given
    val resultat = new PropertyBuilder() //
      .key("clef").yields("valeur") //
      .asProperties

    // then
    resultat should have size (1)
    resultat.getProperty("clef") should be("valeur")
  }
}
