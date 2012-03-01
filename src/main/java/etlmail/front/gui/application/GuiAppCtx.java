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
	return new SwingBean<SwingServerConfiguration>() {
	    @Override
	    public SwingServerConfiguration unsafeMakeBean() {
		return new SwingServerConfiguration();
	    }
	}.bean();
    }

    @Bean
    public NewsletterNotificationBuilder notificationBuilder() throws InterruptedException, InvocationTargetException {
	return new SwingBean<NewsletterNotificationBuilder>() {
	    @Override
	    public NewsletterNotificationBuilder unsafeMakeBean() {
		return new NewsletterNotificationBuilder();
	    }
	}.bean();
    }

    @Bean
    public MainFrame mainFrame() throws InterruptedException, InvocationTargetException {
	return new SwingBean<MainFrame>() {
	    @Override
	    public MainFrame unsafeMakeBean() {
		return new MainFrame();
	    }
	}.bean();
    }

    @Bean
    public MailDefinitionPane mailDefinitionPane() throws InterruptedException, InvocationTargetException {
	return new SwingBean<MailDefinitionPane>() {
	    @Override
	    public MailDefinitionPane unsafeMakeBean() {
		return new MailDefinitionPane();
	    }
	}.bean();
    }
}

abstract class SwingBean<T> implements Runnable {
    private T bean;

    public T bean() throws InterruptedException, InvocationTargetException {
	if (SwingUtilities.isEventDispatchThread()) {
	    run();
	} else {
	    SwingUtilities.invokeAndWait(this);
	}
	return bean;
    }

    @Override
    public final void run() {
	this.bean = unsafeMakeBean();
    }

    protected abstract T unsafeMakeBean();
}
