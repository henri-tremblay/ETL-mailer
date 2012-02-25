package etlmail.front.gui.send;

import javax.swing.SwingWorker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import etlmail.context.ConfigurationData;
import etlmail.engine.NewsletterNotification;

@Configurable
class SendMailWorker extends SwingWorker<Void, Void> {
    private final NewsletterNotification notification;
    private final String password;

    private @Autowired ConfigurationData configuration;

    SendMailWorker(NewsletterNotification notification, String password) {
	this.notification = notification;
	this.password = password;
    }

    @Override
    protected Void doInBackground() {
	configuration.setPassword(password);
	notification.processNotification();
	return null;
    }
}