package etlmail.front.gui.sendmail;

import org.fest.assertions.Assertions;
import org.junit.Test;

public class NewsletterNotificationBuilderTest {
    @Test
    public void lesChampsDuFormulaireNeSontJamaisNuls() {
	NewsletterNotificationBuilder builder = new NewsletterNotificationBuilder();

	Assertions.assertThat(builder.to()).as("to").isNotNull();
	Assertions.assertThat(builder.cc()).as("cc").isNotNull();
	Assertions.assertThat(builder.from()).as("from").isNotNull();
	Assertions.assertThat(builder.subject()).as("subject").isNotNull();
	Assertions.assertThat(builder.template()).as("template").isNotNull();
    }
}
