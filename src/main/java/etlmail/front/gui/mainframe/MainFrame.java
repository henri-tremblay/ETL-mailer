package etlmail.front.gui.mainframe;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.annotation.PostConstruct;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import etlmail.front.gui.application.*;
import etlmail.front.gui.preferences.SwingServerConfiguration;

@Component
public class MainFrame {
    private @Autowired ApplicationEventPublisher eventPublisher;
    private @Autowired SwingServerConfiguration serverConfiguration;
    private @Autowired MailDefinitionPane pane;

    private final JFrame frame;

    @InvokeAndWait
    public MainFrame() {
	frame = new JFrame();
    }

    @PostConstruct
    @InvokeAndWait
    public void init() {
	pane.makeLayout(frame.getContentPane(), serverConfiguration.getPasswordDocument());
	pane.addButtonActions(frame);
	frame.setTitle("Mailer GUI");
	frame.setResizable(false);
	frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	frame.addWindowListener(new WindowAdapter() {
	    @Override
	    public void windowClosing(WindowEvent arg0) {
		eventPublisher.publishEvent(new ShutdownEvent(arg0));
	    }
	});
    }

    @Autowired
    public void register(WindowJanitor janitor) {
	janitor.register(frame);
    }

    public void show() {
	frame.pack();
	frame.setVisible(true);
    }

    public void setJMenuBar(JMenuBar menuBar) {
	frame.setJMenuBar(menuBar);
    }

    public JFrame top() {
	return frame;
    }
}