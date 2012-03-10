package etlmail.front.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import etlmail.engine.NewsletterNotification;

@Component
public class NewsletterNotifier {
	private static final Logger log = LoggerFactory
			.getLogger(NewsletterNotifier.class);

	private @Autowired
	NewsletterNotification newsletterNotification;

	public static void main(String[] args) {
		try {
			log.info("Initializing mail tool...");
			final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
					CliAppCtx.class);
			ctx.registerShutdownHook();
			ctx.getBean(NewsletterNotifier.class).run();
		} catch (final Exception e) {
			log.error("Error, could not send mail", e);
			exitOnError();
		}
	}

	public void run() {
		log.info("Sending mail...");
		newsletterNotification.processNotification();
		log.info("Mail successfully sent");
		exitOnSuccess();
	}

	private static void exitOnError() {
		System.exit(1);
	}

	private static void exitOnSuccess() {
		System.exit(0);
	}

}
