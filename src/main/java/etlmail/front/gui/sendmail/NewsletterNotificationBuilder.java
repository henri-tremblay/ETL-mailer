package etlmail.front.gui.sendmail;

import static etlmail.front.gui.helper.ModelUtils.setText;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import etlmail.engine.NewsletterNotification;
import etlmail.engine.ToolMailSender;
import etlmail.front.gui.application.InvokeAndWait;
import etlmail.front.gui.choosetemplate.FileDocument;
import etlmail.front.gui.choosetemplate.FilenameListener;
import etlmail.front.gui.helper.DocumentAdapter;

@Component
public class NewsletterNotificationBuilder {
	private final Document subjectDocument = new DefaultStyledDocument();
	private final FileDocument templateDocument = new FileDocument();
	private final Document fromDocument = new DefaultStyledDocument();
	private final Document toDocument = new DefaultStyledDocument();

	private final Document ccDocument = new DefaultStyledDocument();

	private volatile String subject = "";
	private volatile File template = new File("");
	private volatile String from = "";
	private volatile String to = "";
	private volatile String cc = "";

	private final Map<String, Object> variables = new HashMap<String, Object>();

	private @Autowired
	ToolMailSender toolMailSender;

	@InvokeAndWait
	public NewsletterNotificationBuilder() {
		subjectDocument.addDocumentListener(new DocumentAdapter() {
			@Override
			protected void update(String newText) {
				subject = newText;
			}
		});
		templateDocument.addFilenameListener(new FilenameListener() {
			@Override
			public void update(File file) {
				template = file;
			}
		});
		fromDocument.addDocumentListener(new DocumentAdapter() {
			@Override
			protected void update(String newText) {
				from = newText;
			}
		});
		toDocument.addDocumentListener(new DocumentAdapter() {
			@Override
			protected void update(String newText) {
				to = newText;
			}
		});
		ccDocument.addDocumentListener(new DocumentAdapter() {
			@Override
			protected void update(String newText) {
				cc = newText;
			}
		});
	}

	public NewsletterNotification build() {
		final File templateFile = template().getAbsoluteFile();
		return new NewsletterNotification(subject(), templateFile.getName(),
				templateFile.getParent(), from(), to(), cc(), variables,
				toolMailSender);
	}

	public FileDocument getTemplate() {
		return templateDocument;
	}

	/**
	 * Only call from the EDT
	 */
	public NewsletterNotificationBuilder template(File template) {
		templateDocument.setFile(template);
		return this;
	}

	public File template() {
		return template;
	}

	public Document getSubject() {
		return subjectDocument;
	}

	/**
	 * Only call from the EDT
	 */
	public NewsletterNotificationBuilder subject(String subject) {
		setText(subjectDocument, subject);
		return this;
	}

	public String subject() {
		return subject;
	}

	public Document getFrom() {
		return fromDocument;
	}

	/**
	 * Only call from the EDT
	 */
	public NewsletterNotificationBuilder from(String from) {
		setText(fromDocument, from);
		return this;
	}

	public String from() {
		return from;
	}

	public Document getTo() {
		return toDocument;
	}

	/**
	 * Only call from the EDT
	 */
	public NewsletterNotificationBuilder to(String to) {
		setText(toDocument, to);
		return this;
	}

	public String to() {
		return to;
	}

	public Document getCc() {
		return ccDocument;
	}

	/**
	 * Only call from the EDT
	 */
	public NewsletterNotificationBuilder cc(String cc) {
		setText(ccDocument, cc);
		return this;
	}

	public String cc() {
		return cc;
	}
}
