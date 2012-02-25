package etlmail.front.cli;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.ToolManager;
import org.springframework.stereotype.Component;

import etlmail.engine.NewsletterNotification;

@Component
public class NewsletterNotificationBuilder {
    private String subject = "";
    private String template = "";
    private String resourcesPath = "";
    private String from = "";
    private String to = "";
    private String cc = "";
    private final Map<String, Object> variables = new HashMap<String, Object>();

    public NewsletterNotificationBuilder() {
	final ToolManager toolManager = new ToolManager();
	final ToolContext toolContext = toolManager.createContext();

	variables.put("date", toolContext.get("date"));
    }

    public NewsletterNotification build() {
	return new NewsletterNotification(subject, template, resourcesPath, from, to, cc, variables);
    }

    public NewsletterNotificationBuilder subject(String subject) {
	this.subject = subject;
	return this;
    }

    public String subject() {
	return subject;
    }

    public NewsletterNotificationBuilder template(String template) {
	this.template = template;
	return this;
    }

    public String template() {
	return template;
    }

    public NewsletterNotificationBuilder resourcesPath(String resourcesPath) {
	this.resourcesPath = resourcesPath;
	return this;
    }

    public String resourcesPath() {
	return resourcesPath;
    }

    public NewsletterNotificationBuilder from(String from) {
	this.from = from;
	return this;
    }

    public String from() {
	return from;
    }

    public NewsletterNotificationBuilder to(String to) {
	this.to = to;
	return this;
    }

    public String to() {
	return to;
    }

    public NewsletterNotificationBuilder cc(String cc) {
	this.cc = cc;
	return this;
    }

    public String cc() {
	return cc;
    }
}
