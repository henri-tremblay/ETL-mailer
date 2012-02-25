package etlmail.front.gui.send;

import static javax.swing.SwingWorker.StateValue.DONE;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutionException;

import javax.swing.JFrame;
import javax.swing.text.Document;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import etlmail.front.gui.NewsletterNotificationBuilder;
import etlmail.front.gui.helper.Dialogs;
import etlmail.front.gui.helper.ModelUtils;

@Configurable
public class SendMailAction implements ActionListener, PropertyChangeListener {
    private static final Logger log = LoggerFactory.getLogger(SendMailAction.class);

    private final JFrame frame;
    private final Document password;

    private @Autowired NewsletterNotificationBuilder notificationBuilder;
    private ProgressDialog progress;
    private SendMailWorker sendMailWorker;

    public SendMailAction(JFrame frame, Document password) {
	this.frame = frame;
	this.password = password;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
	sendMailWorker = new SendMailWorker(notificationBuilder.build(), ModelUtils.getText(password));
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
		Dialogs.showError(frame, cause);
	    }
	}
    }

    private boolean isDone(PropertyChangeEvent event) {
	return "state".equals(event.getPropertyName()) && DONE.equals(event.getNewValue());
    }
}