package etlmail.context;

import java.io.InputStream;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.Component;

@Component
public class MailSender implements JavaMailSender{

    @Autowired private ConfigurationData configuration;

    private final JavaMailSenderImpl bean = new JavaMailSenderImpl();

    private MailSender() {
	bean.setJavaMailProperties(new PropertyBuilder() //
		.key("mail.smtp.auth").yields("true") //
		.key("mail.smtp.starttls.enable").yields("true") //
		.asProperties());
    }

    private JavaMailSender reconfigure() {
	bean.setHost(configuration.host);
	bean.setPort(configuration.port);
	bean.setUsername(configuration.username);
	bean.setPassword(configuration.getPassword());
	return bean;
    }

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
	reconfigure().send(simpleMessage);
    }

    @Override
    public void send(SimpleMailMessage[] simpleMessages) throws MailException {
	reconfigure().send(simpleMessages);
    }

    @Override
    public MimeMessage createMimeMessage() {
	return 	reconfigure().createMimeMessage();
    }

    @Override
    public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
	return 	reconfigure().createMimeMessage(contentStream);
    }

    @Override
    public void send(MimeMessage mimeMessage) throws MailException {
	reconfigure().send(mimeMessage);
    }

    @Override
    public void send(MimeMessage[] mimeMessages) throws MailException {
	reconfigure().send(mimeMessages);
    }

    @Override
    public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
	reconfigure().send(mimeMessagePreparator);
    }

    @Override
    public void send(MimeMessagePreparator[] mimeMessagePreparators) throws MailException {
	reconfigure().send(mimeMessagePreparators);
    }

}
