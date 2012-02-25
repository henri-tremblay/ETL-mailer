package etlmail.front.gui.application;

import javax.swing.JFrame;

import org.simplericity.macify.eawt.ApplicationEvent;
import org.simplericity.macify.eawt.ApplicationListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationEventPublisher;

import etlmail.front.gui.helper.Dialogs;

@Configurable
public class MacListener implements ApplicationListener {
    private final JFrame frame;

    private @Autowired ApplicationEventPublisher eventPublisher;

    public MacListener(JFrame frame) {
	this.frame = frame;
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
	Dialogs.showAbout(frame);
    }
}