package etlmail.front.gui.preferences;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.google.common.io.Closeables;

import etlmail.front.gui.NewsletterNotificationBuilder;
import etlmail.front.gui.application.ShutdownEvent;

@Component
public class SavePrefences implements ApplicationListener<ShutdownEvent> {
    private static final String FILENAME = "mailgui.properties";

    private static final String TO = "to";
    private static final String FROM = "from";
    private static final String SUBJECT = "subject";
    private static final String TEMPLATE = "template";

    private static final String SERVER = "server";
    private static final String PORT = "port";
    private static final String USER = "user";

    private static final Logger log = LoggerFactory.getLogger(SavePrefences.class);

    private @Autowired NewsletterNotificationBuilder notificationBuilder;
    private @Autowired SwingServerConfiguration serverConfiguration;

    @PostConstruct
    public void init() {
	final Properties restored = readProperties();
	builderFromProperties(restored);
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

    private void builderFromProperties(final Properties restored) {
	if (SwingUtilities.isEventDispatchThread()) {
	    restoreProperties(restored);
	} else {
	    SwingUtilities.invokeLater(new Runnable() {
		@Override
		public void run() {
		    restoreProperties(restored);
		}
	    });
	}
    }

    private void restoreProperties(final Properties restored) {
	notificationBuilder.to(restored.getProperty(TO));
	notificationBuilder.from(restored.getProperty(FROM));
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
	final Properties toSave = propertiesFromBuilder();
	writeProperties(toSave);
    }

    private Properties propertiesFromBuilder() {
	final Properties toSave = new Properties();
	if (SwingUtilities.isEventDispatchThread()) {
	    setProperties(toSave);
	} else {
	    try {
		SwingUtilities.invokeAndWait(new Runnable() {
		    @Override
		    public void run() {
			setProperties(toSave);
		    }
		});
	    } catch (final InterruptedException e) {
		throw new IllegalStateException(e);
	    } catch (final InvocationTargetException e) {
		throw new IllegalStateException(e);
	    }
	}
	return toSave;
    }

    private void setProperties(final Properties toSave) {
	toSave.setProperty(TO, notificationBuilder.to());
	toSave.setProperty(FROM, notificationBuilder.from());
	toSave.setProperty(SUBJECT, notificationBuilder.subject());
	toSave.setProperty(TEMPLATE, notificationBuilder.template().getPath());

	toSave.setProperty(SERVER, serverConfiguration.getHost());
	toSave.setProperty(PORT, Integer.toString(serverConfiguration.getPort()));
	toSave.setProperty(USER, serverConfiguration.getUsername());
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
