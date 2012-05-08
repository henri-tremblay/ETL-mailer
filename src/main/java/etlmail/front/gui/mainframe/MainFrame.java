package etlmail.front.gui.mainframe;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.Document;

import net.miginfocom.swing.MigLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import etlmail.front.gui.application.ApplicationEventHandler;
import etlmail.front.gui.application.InvokeAndWait;
import etlmail.front.gui.choosetemplate.ChooseFileAction;
import etlmail.front.gui.choosetemplate.FileDocumentChooser;
import etlmail.front.gui.helper.FrameHolder;
import etlmail.front.gui.preferences.SwingServerConfiguration;
import etlmail.front.gui.sendmail.NewsletterNotificationBuilder;
import etlmail.front.gui.sendmail.SendMailAction;

@Component
public class MainFrame extends FrameHolder {
	private static final Logger log = LoggerFactory.getLogger(MainFrame.class);

	private @Autowired
	ApplicationEventHandler eventHandler;

	private @Autowired
	SwingServerConfiguration serverConfiguration;

	private @Autowired
	NewsletterNotificationBuilder notificationBuilder;

	private @Autowired
	SendMailAction sendMailAction;

	private JButton fileButton;

	private JButton sendButton;

	@PostConstruct
	@InvokeAndWait
	public void init() {
		fileButton = new JButton("\u2026");
		sendButton = new JButton("Send");
		makeLayout(frame.getContentPane(),
				serverConfiguration.getPasswordDocument());
		addButtonActions();
		frame.setTitle("Mailer GUI");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				eventHandler.shutDown(e);
				log.debug("Shuting down...");
			}
		});
	}

	private void makeLayout(Container container, Document password) {
		container.setLayout(new MigLayout(//
				"fill", //
				"[trailing][leading,grow,fill]", //
				""));

		container.add(new JLabel("Template"));
		container.add(new JTextField(notificationBuilder.getTemplate(), null,
				20), "split");
		container.add(fileButton, "wrap, grow 0");

		container.add(new JLabel("Subject"));
		container.add(
				new JTextField(notificationBuilder.getSubject(), null, 20),
				"wrap");

		container.add(new JLabel("From"));
		container.add(new JTextField(notificationBuilder.getFrom(), null, 20),
				"wrap");

		container.add(new JLabel("To"));
		container.add(new JTextField(notificationBuilder.getTo(), null, 20),
				"wrap");

		container.add(new JLabel("Cc"));
		container.add(new JTextField(notificationBuilder.getCc(), null, 20),
				"wrap");

		container.add(new JLabel("Password"));
		container.add(new JPasswordField(password, null, 20), "wrap");

		container.add(sendButton, "span 2, center");
	}

	private void addButtonActions() {
		fileButton.addActionListener(new ChooseFileAction(
				new FileDocumentChooser(notificationBuilder.getTemplate()),
				frame));
		sendButton.addActionListener(sendMailAction);
		frame.getRootPane().setDefaultButton(sendButton);
	}
}
