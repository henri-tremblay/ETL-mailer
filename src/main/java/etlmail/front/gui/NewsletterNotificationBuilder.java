package etlmail.front.gui;

import static etlmail.front.gui.helper.ModelUtils.setText;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;

import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.ToolManager;
import org.springframework.beans.factory.annotation.Autowired;

import etlmail.engine.NewsletterNotification;
import etlmail.engine.ToolMailSender;
import etlmail.front.gui.choosetemplate.FileDocument;
import etlmail.front.gui.choosetemplate.FilenameListener;
import etlmail.front.gui.helper.DocumentAdapter;

public class NewsletterNotificationBuilder {
    private final Document subjectDocument = new DefaultStyledDocument();
    private final FileDocument templateDocument = new FileDocument();
    private final Document fromDocument = new DefaultStyledDocument();
    private final Document toDocument = new DefaultStyledDocument();

    private volatile String subject;
    private volatile File template;
    private volatile String from;
    private volatile String to;

    private final Map<String, Object> variables = new HashMap<String, Object>();

    private @Autowired ToolMailSender toolMailSender;

    /**
     * Only call from the EDT
     */
    public NewsletterNotificationBuilder() {
	final ToolManager toolManager = new ToolManager();
	final ToolContext toolContext = toolManager.createContext();

	variables.put("date", toolContext.get("date"));

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
    }

    public NewsletterNotification build() {
	final File templateFile = template().getAbsoluteFile();
	return new NewsletterNotification(subject(), templateFile.getName(), templateFile.getParent(), from(), to(), cc(), variables, toolMailSender);
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

    public String cc() {
	return "";
    }
}
