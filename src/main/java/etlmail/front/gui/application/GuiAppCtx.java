package etlmail.front.gui.application;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import org.springframework.beans.factory.aspectj.EnableSpringConfigured;
import org.springframework.context.annotation.*;

import etlmail.front.gui.*;
import etlmail.front.gui.preferences.SwingServerConfiguration;

@Configuration
@ComponentScan(basePackageClasses = { //
etlmail.front.gui.ComponentScanMarker.class, //
	etlmail.context.ComponentScanMarker.class //
})
@EnableSpringConfigured
public class GuiAppCtx {
    @Bean
    public SwingServerConfiguration serverConfiguration() throws InterruptedException, InvocationTargetException {
	if (SwingUtilities.isEventDispatchThread()) {
	    return newServerConfiguration();
	} else {
	    final SwingServerConfiguration[] result = new SwingServerConfiguration[1];
	    SwingUtilities.invokeAndWait(new Runnable() {
		@Override
		public void run() {
		    result[0] = newServerConfiguration();
		}
	    });
	    return result[0];
	}
    }

    private SwingServerConfiguration newServerConfiguration() {
	return new SwingServerConfiguration();
    }

    @Bean
    public NewsletterNotificationBuilder newsletterNotificationBuilder() throws InterruptedException, InvocationTargetException {
	if (SwingUtilities.isEventDispatchThread()) {
	    return newNewsletterNotificationBuilder();
	} else {
	    final NewsletterNotificationBuilder[] result = new NewsletterNotificationBuilder[1];
	    SwingUtilities.invokeAndWait(new Runnable() {
		@Override
		public void run() {
		    result[0] = newNewsletterNotificationBuilder();
		}
	    });
	    return result[0];
	}
    }

    private NewsletterNotificationBuilder newNewsletterNotificationBuilder() {
	return new NewsletterNotificationBuilder();
    }

    @Bean
    public MainFrame mainFrame() throws InterruptedException, InvocationTargetException {
	if (SwingUtilities.isEventDispatchThread()) {
	    return newMainFrame();
	} else {
	    final MainFrame[] result = new MainFrame[1];
	    SwingUtilities.invokeAndWait(new Runnable() {
		@Override
		public void run() {
		    result[0] = newMainFrame();
		}
	    });
	    return result[0];
	}
    }

    private MainFrame newMainFrame() {
	final MainFrame mainFrame = new MainFrame();
	return mainFrame;
    }

    @Bean
    public MailDefinitionPane mailDefinitionPane() throws InterruptedException, InvocationTargetException {
	if (SwingUtilities.isEventDispatchThread()) {
	    return newMailDefinitionPanee();
	} else {
	    final MailDefinitionPane[] result = new MailDefinitionPane[1];
	    SwingUtilities.invokeAndWait(new Runnable() {
		@Override
		public void run() {
		    result[0] = newMailDefinitionPanee();
		}
	    });
	    return result[0];
	}
    }

    private MailDefinitionPane newMailDefinitionPanee() {
	return new MailDefinitionPane();
    }
}
