package etlmail.front.gui;

import java.awt.Container;

import javax.swing.*;
import javax.swing.text.Document;

import net.miginfocom.swing.MigLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import etlmail.front.gui.choosetemplate.ChooseFileAction;
import etlmail.front.gui.choosetemplate.FileDocumentChooser;
import etlmail.front.gui.sendmail.SendMailAction;

public class MailDefinitionPane {
    static final Logger log = LoggerFactory.getLogger(MailDefinitionPane.class);

    private @Autowired NewsletterNotificationBuilder notificationBuilder;
    private @Autowired SendMailAction sendMailAction;

    private final JButton fileButton = new JButton("\u2026");
    private final JButton sendButton = new JButton("Send");

    public void addButtonActions(JFrame frame) {
	fileButton.addActionListener(new ChooseFileAction(new FileDocumentChooser(notificationBuilder.getTemplate()), frame));
	sendButton.addActionListener(sendMailAction);
    }

    public void makeLayout(Container container, Document password) {
	container.setLayout(new MigLayout(//
		"fill", //
		"[trailing][leading,grow,fill]", //
		""));

	container.add(new JLabel("Template"));
	container.add(new JTextField(notificationBuilder.getTemplate(), null, 20), "split");
	container.add(fileButton, "wrap, grow 0");

	container.add(new JLabel("Subject"));
	container.add(new JTextField(notificationBuilder.getSubject(), null, 20), "wrap");

	container.add(new JLabel("From"));
	container.add(new JTextField(notificationBuilder.getFrom(), null, 20), "wrap");

	container.add(new JLabel("To"));
	container.add(new JTextField(notificationBuilder.getTo(), null, 20), "wrap");

	container.add(new JLabel("Password"));
	container.add(new JPasswordField(password, null, 20), "wrap");

	container.add(sendButton, "span 2, center");
    }

}
