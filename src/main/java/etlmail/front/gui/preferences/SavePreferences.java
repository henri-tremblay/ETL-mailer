package etlmail.front.gui.preferences;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.google.common.io.Closeables;

import etlmail.front.gui.application.InvokeAndWait;
import etlmail.front.gui.application.ShutdownEvent;
import etlmail.front.gui.sendmail.NewsletterNotificationBuilder;

@Component
public class SavePreferences implements ApplicationListener<ShutdownEvent> {
	private static final String FILENAME = "mailgui.properties";

	private static final String TO = "to";
	private static final String FROM = "from";
	private static final String CC = "cc";
	private static final String SUBJECT = "subject";
	private static final String TEMPLATE = "template";

	private static final String SERVER = "server";
	private static final String PORT = "port";
	private static final String USER = "user";

	private static final Logger log = LoggerFactory
			.getLogger(SavePreferences.class);

	private @Autowired
	NewsletterNotificationBuilder notificationBuilder;
	private @Autowired
	SwingServerConfiguration serverConfiguration;

	@PostConstruct
	public void init() {
		fromProperties(readProperties());
	}

	private Properties readProperties() {
		final Properties restored = new Properties();
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(FILENAME));
			restored.load(in);
			in.close();
		} catch (final IOException e) {
			log.error("Cannot read preferences", e);
		} finally {
			Closeables.closeQuietly(in);
		}
		return restored;
	}

	@InvokeAndWait
	private void fromProperties(final Properties restored) {
		notificationBuilder.to(restored.getProperty(TO));
		notificationBuilder.from(restored.getProperty(FROM));
		notificationBuilder.cc(restored.getProperty(CC));
		notificationBuilder.subject(restored.getProperty(SUBJECT));
		final String template = restored.getProperty(TEMPLATE);
		if (template != null) {
			notificationBuilder.template(new File(template));
		}

		serverConfiguration.setHost(restored.getProperty(SERVER));
		final String port = restored.getProperty(PORT);
		if (null != port) {
			serverConfiguration.setPort(Integer.parseInt(port));
		}
		serverConfiguration.setUsername(restored.getProperty(USER));
	}

	@Override
	public void onApplicationEvent(ShutdownEvent event) {
		writeProperties(toProperties());
	}

	@InvokeAndWait
	private Properties toProperties() {
		final Properties toSave = new Properties();
		toSave.setProperty(TO, notificationBuilder.to());
		toSave.setProperty(FROM, notificationBuilder.from());
		toSave.setProperty(CC, notificationBuilder.cc());
		toSave.setProperty(SUBJECT, notificationBuilder.subject());
		toSave.setProperty(TEMPLATE, notificationBuilder.template().getPath());

		toSave.setProperty(SERVER, serverConfiguration.getHost());
		toSave.setProperty(PORT,
				Integer.toString(serverConfiguration.getPort()));
		toSave.setProperty(USER, serverConfiguration.getUsername());

		return toSave;
	}

	private void writeProperties(final Properties toSave) {
		OutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(FILENAME));
			toSave.store(out, null);
			out.close();
		} catch (final IOException e) {
			log.error("Cannot save preferences", e);
		} finally {
			Closeables.closeQuietly(out);
		}
	}
}
