package etlmail.front.gui.sendmail;

import static javax.swing.SwingWorker.StateValue.DONE;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutionException;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import etlmail.front.gui.NewsletterNotificationBuilder;
import etlmail.front.gui.helper.UserNotifier;

@Configurable
public class SendMailAction implements ActionListener, PropertyChangeListener {
    private static final Logger log = LoggerFactory.getLogger(SendMailAction.class);

    private final JFrame frame;
    private @Autowired UserNotifier notifier;

    private @Autowired NewsletterNotificationBuilder notificationBuilder;
    private ProgressDialog progress;
    private SendMailWorker sendMailWorker;

    public SendMailAction(JFrame frame) {
	this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
	sendMailWorker = new SendMailWorker(notificationBuilder.build());
	sendMailWorker.addPropertyChangeListener(this);
	progress = new ProgressDialog(frame, sendMailWorker);
	progress.setVisible(true);

    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
	if (isDone(event)) {
	    progress.setVisible(false);
	    progress.dispose();
	    progress = null;
	    try {
		sendMailWorker.get();
	    } catch (final InterruptedException e) {
		throw new IllegalStateException("Cannot happen", e);
	    } catch (final ExecutionException e) {
		final Throwable cause = e.getCause();
		log.error("Cannot send mail", cause);
		notifier.showError(cause);
	    }
	}
    }

    private boolean isDone(PropertyChangeEvent event) {
	return "state".equals(event.getPropertyName()) && DONE.equals(event.getNewValue());
    }
}