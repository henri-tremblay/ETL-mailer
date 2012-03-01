package etlmail.engine;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.*;
import org.springframework.ui.velocity.VelocityEngineUtils;

import etlmail.engine.css.CssInliner;

public abstract class ToolMailSender {
    private @Autowired JavaMailSender javaMailSender;
    private @Autowired CssInliner cssInliner;

    protected abstract VelocityEngine velocityEngine(String resourcesDirectory) throws VelocityException, IOException;

    public void sendMail(final NewsletterNotification notification) {
	javaMailSender.send(new MimeMessagePreparator() {
	    @Override
	    public void prepare(MimeMessage mimeMessage) throws Exception {
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
		message.setTo(toStringArray(getAddressesFromString(notification.getTo()))); // Set destinators
		message.setCc(toStringArray(getAddressesFromString(notification.getCc()))); // Set CC destinators
		message.setFrom(notification.getFrom()); // Set source email
		message.setSubject(notification.getSubject()); // Set eMail Subject

		VelocityEngine velocityEngine = velocityEngine(notification.getResourcesPath());
		final String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, notification.getTemplate(), notification.getVariables());

		final Document doc = Jsoup.parse(text);

		final Collection<String> imageNames = convertImagesToCid(doc);
		cssInliner.inlineStyles(doc);

		message.setText(doc.outerHtml(), true);

		final File resources = new File(notification.getResourcesPath());
		// Adding Inline Resources
		for (final String imageName : imageNames) {
		    final FileSystemResource image = new FileSystemResource(new File(resources, imageName));
		    message.addInline(imageName, image);
		}
	    }
	});
    }

    List<String> getAddressesFromString(String addresses) {
	final StringTokenizer tokenizer = new StringTokenizer(addresses, ",");
	final List<String> addressList = new ArrayList<String>();
	while (tokenizer.hasMoreElements()) {
	    String addresse = (String) tokenizer.nextElement();
	    addresse = addresse.trim().toLowerCase();
	    addressList.add(addresse);
	}

	return addressList;
    }

    String[] toStringArray(List<String> strings) {
	if (strings == null) {
	    return new String[0];
	}

	String[] stringsArray = new String[strings.size()];
	stringsArray = strings.toArray(stringsArray);
	return stringsArray;
    }

    Collection<String> convertImagesToCid(final Document doc) {
	final Set<String> imageNames = new HashSet<String>();

	for (final Element image : doc.select("img")) {
	    final String source = image.attr("src");
	    imageNames.add(source);
	    image.attr("src", "cid:" + source);
	}
	return imageNames;
    }
}
