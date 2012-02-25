package etlmail.front.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import etlmail.engine.NewsletterNotification;

@Configuration
@PropertySource({ "classpath:mailTool.properties" })
@ComponentScan(basePackageClasses = { //
etlmail.context.ComponentScanMarker.class, //
	etlmail.front.cli.ComponentScanMarker.class //
})
public class CliAppCtx {
    private @Autowired NewsletterNotificationBuilder notificationBuilder;

    @Bean
    public NewsletterNotification notification( //
	    @Value("${mail.resources.directory}") String resourcesDirectory, //
	    @Value("${mail.from}") String from, //
	    @Value("${mail.to}") String to, //
	    @Value("${mail.cc}") String cc, //
	    @Value("${mail.subject}") String subject, //
	    @Value("${mail.template}") String template //
    ) {
	return notificationBuilder //
		.resourcesPath(resourcesDirectory) //
		.from(from) //
		.to(to) //
		.cc(cc) //
		.subject(subject) //
		.template(template) //
		.build();
    }
}
