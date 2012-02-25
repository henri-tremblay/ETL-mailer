package etlmail.context;

import java.util.Properties;

import org.fest.assertions.Assertions;
import org.junit.Test;

import etlmail.context.PropertyBuilder;

public class PropertyBuilderTest {
    @Test
    public void avecUnePaireClefValeur() {
	final Properties resultat = new PropertyBuilder() //
		.key("clef").yields("valeur") //
		.asProperties();

	Assertions.assertThat(resultat.getProperty("clef")).isEqualTo("valeur");
    }
}
