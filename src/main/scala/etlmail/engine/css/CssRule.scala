package etlmail.engine.css

import org.apache.commons.lang.StringUtils.{ strip, countMatches }
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import scala.collection.JavaConversions._

class CssRule(val simpleSelector: String, val properties: String) extends Ordered[CssRule] {
  val specificity: SelectorSpecificity = {
    val builder = new SelectorSpecificity.Builder
    for {
      rawfragment <- simpleSelector.split("[ +~>]")
      fragment = strip(rawfragment)
      if (!fragment.isEmpty())
    } {
      builder.addClass(countMatches(fragment, "."))
      builder.addId(countMatches(fragment, "#"))
      if (!"#.".contains(fragment.subSequence(0, 1))) {
        builder.addType(1)
      }
    }
    builder.asSpecificity()
  }

  override def compare(o: CssRule): Int = specificity.compare(o.specificity)

  def prependProperties(doc: Document) {
    for (selElem <- doc.select(simpleSelector)) {
      val oldProperties = selElem.attr("style");
      selElem.attr("style", concatenateProperties(oldProperties, properties));
    }
  }

  private def concatenateProperties(oldProp: String, newProp: String): String =
    strip(strip(oldProp), ";") + ";" + strip(newProp)
}
