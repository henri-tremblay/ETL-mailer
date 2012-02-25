package etlmail.front.gui;

import static etlmail.front.gui.helper.ModelUtils.getText;
import static etlmail.front.gui.helper.ModelUtils.setText;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;

import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.ToolManager;
import org.springframework.stereotype.Component;

import etlmail.engine.NewsletterNotification;
import etlmail.front.gui.choosetemplate.FileDocument;

@Component
public class NewsletterNotificationBuilder {
    private final Document subject = new DefaultStyledDocument();
    private final FileDocument template = new FileDocument();
    private final Document from = new DefaultStyledDocument();
    private final Document to = new DefaultStyledDocument();
    private final Document cc = new DefaultStyledDocument();
    private final Map<String, Object> variables = new HashMap<String, Object>();

    private NewsletterNotificationBuilder() {
	final ToolManager toolManager = new ToolManager();
	final ToolContext toolContext = toolManager.createContext();

	variables.put("date", toolContext.get("date"));
    }

    public NewsletterNotification build() {
	final File templateFile = template.getFile().getAbsoluteFile();
	return new NewsletterNotification(subject(), templateFile.getPath(), templateFile.getParent(), from(), to(), cc(), variables);
    }

    public FileDocument getTemplate() {
	return template;
    }

    public NewsletterNotificationBuilder template(File template) {
	this.template.setFile(template);
	return this;
    }

    public File template() {
	return template.getFile();
    }

    public Document getSubject() {
	return subject;
    }

    public NewsletterNotificationBuilder subject(String subject) {
	setText(this.subject, subject);
	return this;
    }

    public String subject() {
	return getText(subject);
    }

    public Document getFrom() {
	return from;
    }

    public NewsletterNotificationBuilder from(String from) {
	setText(this.from, from);
	return this;
    }

    public String from() {
	return getText(from);
    }

    public Document getTo() {
	return to;
    }

    public NewsletterNotificationBuilder to(String to) {
	setText(this.to, to);
	return this;
    }

    public String to() {
	return getText(to);
    }

    public NewsletterNotificationBuilder cc(String cc) {
	setText(this.cc, cc);
	return this;
    }

    public String cc() {
	return getText(cc);
    }
}
