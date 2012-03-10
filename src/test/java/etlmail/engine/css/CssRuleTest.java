package etlmail.engine.css;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import etlmail.engine.css.CssRule;
import etlmail.engine.css.SelectorSpecificity;

public class CssRuleTest {
	@Test
	public void avecUnType() {
		final CssRule rule = new CssRule("div", null);

		assertThat(rule.getSpecificity()).isEqualTo(
				new SelectorSpecificity(0, 0, 1));
	}

	@Test
	public void avecUneClasse() {
		final CssRule rule = new CssRule(".toutBeau", null);

		assertThat(rule.getSpecificity()).isEqualTo(
				new SelectorSpecificity(0, 1, 0));
	}

	@Test
	public void avecUnId() {
		final CssRule rule = new CssRule("#bidule", null);

		assertThat(rule.getSpecificity()).isEqualTo(
				new SelectorSpecificity(1, 0, 0));
	}

	@Test
	public void combinaison() {
		final CssRule rule = new CssRule("div#bidule > .toutBeau .truc", null);

		assertThat(rule.getSpecificity()).isEqualTo(
				new SelectorSpecificity(1, 2, 1));
	}
}
