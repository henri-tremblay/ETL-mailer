package etlmail.engine.css

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.junit.runner.RunWith

@RunWith(classOf[JUnitRunner])
class SelectorSpecificitySpec extends FlatSpec with ShouldMatchers {
  "a selector with more IDs" should "be greater than the same selector with fewer IDs" in {
    // given
    val fort = new SelectorSpecificity(2, 0, 0)
    val faible = new SelectorSpecificity(1, 0, 0)

    // then
    fort should be > faible
  }

  "a selector with more classes" should "be greater than the same selector with fewer classes" in {
    // given
    val fort = new SelectorSpecificity(2, 4, 0)
    val faible = new SelectorSpecificity(2, 1, 0)

    // then
    fort should be > faible
  }

  "a selector with more types" should "be greater than the same selector with fewer types" in {
    // given
    val fort = new SelectorSpecificity(2, 0, 1)
    val faible = new SelectorSpecificity(2, 0, 0)

    // then
    fort should be > faible
  }

  "IDs" should "trump classes and types" in {
    // given
    val fort = new SelectorSpecificity(2, 1, 1)
    val faible = new SelectorSpecificity(1, 2, 2)

    // then
    fort should be > faible
  }

  "classes" should "trump types" in {
    // given
    val fort = new SelectorSpecificity(1, 2, 1)
    val faible = new SelectorSpecificity(1, 1, 2)

    // then
    fort should be > faible
  }

  "selectors with the same value" should "be equal" in {
    // given
    val one = new SelectorSpecificity(1, 2, 1)
    val same = new SelectorSpecificity(1, 2, 1)

    // then
    one should be === same
  }
}
