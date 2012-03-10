package etlmail.front.cli;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import etlmail.engine.NewsletterNotification;
import etlmail.engine.ToolMailSender;

@Configuration
@PropertySource({ "classpath:mailTool.properties" })
@ComponentScan(basePackageClasses = { //
etlmail.context.ComponentScanMarker.class, //
		etlmail.front.cli.ComponentScanMarker.class //
})
public class CliAppCtx {
	private @Autowired
	ToolMailSender toolMailSender;

	@Bean
	public NewsletterNotification notification( //
			@Value("${mail.resources.directory}") String resourcesDirectory, //
			@Value("${mail.from}") String from, //
			@Value("${mail.to}") String to, //
			@Value("${mail.cc}") String cc, //
			@Value("${mail.subject}") String subject, //
			@Value("${mail.template}") String template //
	) {
		final Map<String, Object> variables = new HashMap<String, Object>();

		return new NewsletterNotification(subject, template,
				resourcesDirectory, from, to, cc, variables, toolMailSender);
	}
}
