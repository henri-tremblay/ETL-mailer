package etlmail.front.gui;

import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationEventPublisher;

import etlmail.front.gui.application.ShutdownEvent;
import etlmail.front.gui.application.WindowJanitor;

@Configurable
@SuppressWarnings("serial")
public class MainFrame extends JFrame {
    private @Autowired ApplicationEventPublisher eventPublisher;

    public MainFrame() throws HeadlessException {
	super("Mailer GUI");
	setResizable(false);
	setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	MailDefinitionPane.populate(this);
	addWindowListener(new WindowAdapter() {
	    @Override
	    public void windowClosing(WindowEvent arg0) {
		eventPublisher.publishEvent(new ShutdownEvent(arg0));
	    }
	});
    }

    @Autowired
    public void register(WindowJanitor janitor) {
	janitor.register(this);
    }
}