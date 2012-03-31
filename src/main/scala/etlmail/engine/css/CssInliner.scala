package etlmail.engine.css

import java.util.Collections.reverseOrder
import java.util.Collections.sort

import java.util._

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component

import scala.collection.JavaConversions._

@Component
class CssInliner {
  def inlineStyles(doc: Document) {
    for (style <- doc.select("style")) {
      val styleRules = style.getAllElements().get(0).data().replaceAll("\n", "").trim()
      val st = new StringTokenizer(styleRules, "{}")
      val cssRules = new ArrayList[CssRule]()
      while (st.countTokens() > 1) {
        val selector = st.nextToken()
        val properties = st.nextToken()
        for (simpleSelector <- selector.split(",")) {
          cssRules.add(new CssRule(simpleSelector, properties))
        }
      }
      sort(cssRules, reverseOrder[CssRule]())
      for (rule <- cssRules) {
        rule.prependProperties(doc)
      }
      style.remove()
    }
  }
}
