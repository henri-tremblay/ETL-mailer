package etlmail.front.gui.application;

import org.simplericity.macify.eawt.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import etlmail.front.gui.helper.UserNotifier;

@Component
public class MacListener implements ApplicationListener {
    private @Autowired ApplicationEventPublisher eventPublisher;
    private @Autowired UserNotifier notifier;
    private @Autowired PreferencesWindowProvider preferencesWindowProvider;

    public void enable() {
	final Application app = new DefaultApplication();
	app.addPreferencesMenuItem();
	app.setEnabledPreferencesMenu(true);
	app.addAboutMenuItem();
	app.setEnabledAboutMenu(true);
	app.addApplicationListener(this);
    }

    @Override
    public void handleReOpenApplication(ApplicationEvent e) {

    }

    @Override
    public void handleQuit(ApplicationEvent e) {
	eventPublisher.publishEvent(new ShutdownEvent(e));
    }

    @Override
    public void handlePrintFile(ApplicationEvent e) {

    }

    @Override
    public void handlePreferences(ApplicationEvent e) {
	preferencesWindowProvider.preferencesWindow().setVisible(true);
    }

    @Override
    public void handleOpenFile(ApplicationEvent e) {

    }

    @Override
    public void handleOpenApplication(ApplicationEvent e) {

    }

    @Override
    public void handleAbout(ApplicationEvent e) {
	e.setHandled(true);
	notifier.showAbout();
    }
}