package etlmail.front.gui.application;

import org.simplericity.macify.eawt.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MacListener implements ApplicationListener {
    private @Autowired ApplicationEventHandler applicationEventHandler;

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
	applicationEventHandler.shutDown(e);
    }

    @Override
    public void handlePrintFile(ApplicationEvent e) {
    }

    @Override
    public void handlePreferences(ApplicationEvent e) {
	applicationEventHandler.showPreferences();
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
	applicationEventHandler.showAbout();
    }
}