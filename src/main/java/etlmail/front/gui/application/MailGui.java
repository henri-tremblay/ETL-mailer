package etlmail.front.gui.application;

import javax.swing.*;

import org.simplericity.macify.eawt.Application;
import org.simplericity.macify.eawt.DefaultApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import etlmail.front.gui.MainFrame;

public class MailGui implements Runnable {

    public MailGui() {
	new AnnotationConfigApplicationContext(GuiAppCtx.class);
    }

    @Override
    public void run() {
	final JFrame frame = new MainFrame();

	if (isMac()) {
	    final Application app = new DefaultApplication();
	    app.addPreferencesMenuItem();
	    app.setEnabledPreferencesMenu(true);
	    app.removeAboutMenuItem();
	    app.addAboutMenuItem();
	    app.setEnabledAboutMenu(true);
	    app.addApplicationListener(new MacListener(frame));
	} else {
	}
	frame.pack();
	frame.setVisible(true);
    }

    private boolean isMac() {
	return System.getProperty("mrj.version") != null;
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
	System.setProperty("apple.laf.useScreenMenuBar", "true");
	System.setProperty("com.apple.mrj.application.apple.menu.about.name", "ETL Mail");
	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	SwingUtilities.invokeLater(new MailGui());
    }
}
