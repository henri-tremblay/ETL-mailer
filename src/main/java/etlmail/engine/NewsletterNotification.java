package etlmail.engine;

import java.util.Collections;
import java.util.Map;

public class NewsletterNotification {
	private final String subject;
	// TODO: fusionner ces deux champs
	private final String template;
	private final String resourcesPath;
	private final String from;
	private final String to;
	private final String cc;
	private final Map<String, Object> variables;

	private final ToolMailSender toolMailSender;

	public NewsletterNotification(String subject, String template,
			String resourcesPath, String from, String to, String cc,
			Map<String, Object> variables, ToolMailSender toolMailSender) {
		this.subject = subject;
		this.template = template;
		this.resourcesPath = resourcesPath;
		this.from = from;
		this.to = to;
		this.cc = cc;
		this.variables = variables;
		this.toolMailSender = toolMailSender;
	}

	public void processNotification() {
		toolMailSender.sendMail(this);
	}

	public String getSubject() {
		return subject;
	}

	public String getTemplate() {
		return template;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public String getCc() {
		return cc;
	}

	public String getResourcesPath() {
		return resourcesPath;
	}

	public Map<String, Object> getVariables() {
		return Collections.unmodifiableMap(variables);
	}
}
