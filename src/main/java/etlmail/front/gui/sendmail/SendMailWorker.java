package etlmail.front.gui.sendmail;

import javax.swing.SwingWorker;

import etlmail.engine.NewsletterNotification;

class SendMailWorker extends SwingWorker<Void, Void> {
    private final NewsletterNotification notification;

    SendMailWorker(NewsletterNotification notification) {
	this.notification = notification;
    }

    @Override
    protected Void doInBackground() {
	notification.processNotification();
	return null;
    }
}