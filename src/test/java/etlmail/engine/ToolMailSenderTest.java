package etlmail.engine;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expectLastCall;
import static org.fest.assertions.Assertions.assertThat;

import java.util.*;

import junit.framework.Assert;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.ToolContext;
import org.easymock.EasyMockSupport;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import etlmail.engine.css.CssInliner;
import etlmail.front.cli.PropertyServerConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ToolMailSenderTest.Mockery.class, ToolMailSenderTest.EngineLessToolMailSender.class, PropertyServerConfiguration.class })
@DirtiesContext
public class ToolMailSenderTest {

    static class EngineLessToolMailSender extends ToolMailSender {
	@Override
	protected VelocityEngine velocityEngine(String resourcesDirectory) {
	    return null;
	}
    }

    @Configuration
    @PropertySource({ "classpath:mailTool.properties" })
    static class Mockery extends EasyMockSupport {
	@Bean
	public JavaMailSender javaMailSender() {
	    return createMock(JavaMailSender.class);
	}

	@Bean
	public CssInliner cssInliner() {
	    return createMock(CssInliner.class);
	}

	@Bean
	public ToolContext toolContext() {
	    return createMock(ToolContext.class);
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
	    return new PropertySourcesPlaceholderConfigurer();
	}
    }

    @Autowired JavaMailSender javaMailSenderMock;

    @Autowired ToolMailSender toolMailSender;

    @Autowired Mockery mockery;

    @Test
    public void sendMail_DoitAppelerJavaMailSender() {
	// Setup
	javaMailSenderMock.send(anyObject(MimeMessagePreparator.class));
	expectLastCall();
	mockery.replayAll();

	// Test
	toolMailSender.sendMail(null);

	// Assertions
	mockery.verifyAll();
    }

    @Test
    public void toStringArray_AvecUneListeDeDeuxElements_DoitRenvoyerUnTableauAvecCesElements() {
	// setup
	final List<String> strings = Arrays.asList("string1", "string2");

	// test
	final String[] stringArray = toolMailSender.toStringArray(strings);

	// assertions
	Assert.assertEquals(2, stringArray.length);
	Assert.assertEquals("string1", stringArray[0]);
	Assert.assertEquals("string2", stringArray[1]);
    }

    @Test
    public void toStringArrayWithNullList() {
	// setup
	final List<String> strings = null;

	// test
	final String[] stringArray = toolMailSender.toStringArray(strings);

	// assertions
	assertThat(stringArray.length).isZero();
    }

    @Test
    public void getAddressesFromString() {

	// Setup
	final String addresses = "aaa@aaa.com, BBB@bbb.com,ccc@ccc.com,";

	// Test
	final List<String> addressList = toolMailSender.getAddressesFromString(addresses);

	// Assertions
	Assert.assertEquals(3, addressList.size());
	Assert.assertTrue(addressList.contains("aaa@aaa.com"));
	Assert.assertTrue(addressList.contains("bbb@bbb.com"));
	Assert.assertTrue(addressList.contains("ccc@ccc.com"));

	final String addressesEmpty = "";
	final List<String> addressListEmpty = toolMailSender.getAddressesFromString(addressesEmpty);
	Assert.assertEquals(0, addressListEmpty.size());
    }

    @Test
    public void convertImagesToCid_AvecDeuxFoisLaMemeImage_NeDoitPasLaRepeter() {
	final Document document = new Document("");
	document.appendElement("img").attr("src", "gnu.gif");
	document.appendElement("img").attr("src", "gnu.gif");

	final Collection<String> imageNames = toolMailSender.convertImagesToCid(document);

	assertThat(imageNames).doesNotHaveDuplicates();
    }
}
