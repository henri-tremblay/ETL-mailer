package etlmail.front.gui.preferences;

import org.fest.assertions.Assertions;
import org.junit.Test;

public class SwingServerConfigurationTest {
    @Test
    public void lesChampsDuFormulaireNeSontJamaisNuls() {
	SwingServerConfiguration builder = new SwingServerConfiguration();

	Assertions.assertThat(builder.getHost()).as("host").isNotNull();
	Assertions.assertThat(builder.getPort()).as("port").isNotNull();
	Assertions.assertThat(builder.getPassword()).as("password").isNotNull();
	Assertions.assertThat(builder.getUsername()).as("username").isNotNull();
    }

}
