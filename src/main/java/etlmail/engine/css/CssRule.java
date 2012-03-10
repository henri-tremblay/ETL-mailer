package etlmail.engine.css;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CssRule implements Comparable<CssRule> {
	private final String simpleSelector;
	private final SelectorSpecificity specificity;
	private final String properties;

	CssRule(String simpleSelector, String properties) {
		this.simpleSelector = simpleSelector;
		this.properties = properties;
		specificity = specificity(simpleSelector);
	}

	static SelectorSpecificity specificity(String simpleSelector) {
		final SelectorSpecificity.Builder builder = new SelectorSpecificity.Builder();
		final String[] fragments = simpleSelector.split("[ +~>]");
		for (String fragment : fragments) {
			fragment = StringUtils.strip(fragment);
			if (fragment.isEmpty()) {
			} else {
				builder.addClass(StringUtils.countMatches(fragment, "."));
				builder.addId(StringUtils.countMatches(fragment, "#"));
				if (!"#.".contains(fragment.subSequence(0, 1))) {
					builder.addType(1);
				}
			}
		}
		return builder.asSpecificity();
	}

	public SelectorSpecificity getSpecificity() {
		return specificity;
	}

	@Override
	public int compareTo(CssRule o) {
		return specificity.compareTo(o.specificity);
	}

	public void prependProperties(Document doc) {
		final Elements selectedElements = doc.select(simpleSelector);
		for (final Element selElem : selectedElements) {
			final String oldProperties = selElem.attr("style");
			selElem.attr("style",
					concatenateProperties(oldProperties, properties));
		}
	}

	private String concatenateProperties(String oldProp, String newProp) {
		oldProp = StringUtils.strip(oldProp);
		newProp = StringUtils.strip(newProp);
		if (!newProp.endsWith(";")) {
			newProp += ";";
		}
		return newProp + oldProp; // The existing (old) properties should take
									// precedence.
	}
}
