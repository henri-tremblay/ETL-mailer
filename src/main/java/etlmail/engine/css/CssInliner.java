package etlmail.engine.css;

import static java.util.Collections.reverseOrder;
import static java.util.Collections.sort;

import java.util.*;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
public class CssInliner {
    public void inlineStyles(Document doc) {
	for (final Element style : doc.select("style")) {
	    final String styleRules = style.getAllElements().get(0).data().replaceAll("\n", "").trim();
	    final StringTokenizer st = new StringTokenizer(styleRules, "{}");
	    final List<CssRule> cssRules = new ArrayList<CssRule>();
	    while (st.countTokens() > 1) {
		final String selector = st.nextToken();
		final String properties = st.nextToken();
		for (final String simpleSelector : selector.split(",")) {
		    cssRules.add(new CssRule(simpleSelector, properties));
		}
	    }
	    sort(cssRules, reverseOrder());
	    for (final CssRule rule : cssRules) {
		rule.prependProperties(doc);
	    }
	    style.remove();
	}
    }
}
