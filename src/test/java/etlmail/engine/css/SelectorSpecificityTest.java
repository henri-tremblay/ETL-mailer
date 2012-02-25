package etlmail.engine.css;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import etlmail.engine.css.SelectorSpecificity;

public class SelectorSpecificityTest {
    @Test
    public void plusOnADIdsPlusOnEstFort() {
	final SelectorSpecificity fort = new SelectorSpecificity(2, 0, 0);
	final SelectorSpecificity faible = new SelectorSpecificity(1, 0, 0);
	assertThat(fort.compareTo(faible)).isPositive();
	assertThat(faible.compareTo(fort)).isNegative();
    }

    @Test
    public void plusOnADeClassesPlusOnEstFort() {
	final SelectorSpecificity fort = new SelectorSpecificity(2, 4, 0);
	final SelectorSpecificity faible = new SelectorSpecificity(2, 1, 0);
	assertThat(fort.compareTo(faible)).isPositive();
	assertThat(faible.compareTo(fort)).isNegative();
    }

    @Test
    public void plusOnADeTypesPlusOnEstFort() {
	final SelectorSpecificity fort = new SelectorSpecificity(2, 0, 1);
	final SelectorSpecificity faible = new SelectorSpecificity(2, 0, 0);
	assertThat(fort.compareTo(faible)).isPositive();
	assertThat(faible.compareTo(fort)).isNegative();
    }

    @Test
    public void lesIdsPlusFortsQueLeReste() {
	final SelectorSpecificity fort = new SelectorSpecificity(2, 1, 1);
	final SelectorSpecificity faible = new SelectorSpecificity(1, 2, 2);
	assertThat(fort.compareTo(faible)).isPositive();
	assertThat(faible.compareTo(fort)).isNegative();
    }

    @Test
    public void lesClassesPlusFortesQueLesTypes() {
	final SelectorSpecificity fort = new SelectorSpecificity(1, 2, 1);
	final SelectorSpecificity faible = new SelectorSpecificity(1, 1, 2);
	assertThat(fort.compareTo(faible)).isPositive();
	assertThat(faible.compareTo(fort)).isNegative();
    }

    @Test
    public void quandCEstLeMemeCEstLeMeme() {
	final SelectorSpecificity meme = new SelectorSpecificity(1, 2, 1);
	assertThat(meme.compareTo(meme)).isZero();
    }
}
