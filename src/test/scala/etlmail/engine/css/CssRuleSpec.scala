package etlmail.engine.css

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.junit.runner.RunWith

@RunWith(classOf[JUnitRunner])
class CssRuleTest extends FlatSpec with ShouldMatchers {
  "getSpecificity" should "find a html type" in {
    val rule = new CssRule("div", null)

    rule.specificity should equal(new SelectorSpecificity(0, 0, 1))
  }

  "getSpecificity" should "find a class" in {
    val rule = new CssRule(".toutBeau", null)

    rule.specificity should equal(new SelectorSpecificity(0, 1, 0))
  }

  "getSpecificity" should "find an id" in {
    val rule = new CssRule("#bidule", null)

    rule.specificity should equal(new SelectorSpecificity(1, 0, 0))
  }

  "getSpecificity" should "find an id, two classes and one type" in {
    val rule = new CssRule("div#bidule > .toutBeau .truc", null)

    rule.specificity should equal(new SelectorSpecificity(1, 2, 1))
  }
}
