package etlmail.front.gui.application;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import etlmail.front.gui.helper.UserNotifier;
import etlmail.front.gui.preferences.PreferencesWindow;

@Component
public class ApplicationEventHandler {
    private @Autowired ApplicationEventPublisher eventPublisher;
    private @Autowired UserNotifier notifier;
    private @Autowired ObjectFactory<PreferencesWindow> preferencesWindowProvider;

    public void shutDown(Object cause) {
	eventPublisher.publishEvent(new ShutdownEvent(cause));
    }

    /**
     * Only call from EDT
     */
    public void showPreferences() {
	preferencesWindowProvider.getObject().setVisible(true);
    }

    public void showAbout() {
	notifier.showAbout();
    }
}
