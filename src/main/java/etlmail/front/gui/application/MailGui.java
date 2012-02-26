package etlmail.front.gui.application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.simplericity.macify.eawt.Application;
import org.simplericity.macify.eawt.DefaultApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import etlmail.front.gui.MainFrame;
import etlmail.front.gui.helper.Dialogs;

public class MailGui implements Runnable {

    public MailGui() {
	new AnnotationConfigApplicationContext(GuiAppCtx.class);
    }

    @Override
    public void run() {
	final JFrame frame = new MainFrame();

	if (isMac()) {
	    final Application app = new DefaultApplication();
	    app.removePreferencesMenuItem();
	    app.addAboutMenuItem();
	    app.setEnabledAboutMenu(true);
	    app.addApplicationListener(new MacListener(frame));
	} else {
	    addMenuBar(frame);
	}
	frame.pack();
	frame.setVisible(true);
    }

    private void addMenuBar(final JFrame frame) {
	final JMenuBar menuBar = new JMenuBar();
	final JMenu menu = new JMenu("File");
	menuBar.add(menu);
	final JMenuItem aboutMenuItem = new JMenuItem("about");
	aboutMenuItem.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		Dialogs.showAbout(frame);
	    }
	});
	final JMenuItem exitMenuItem = new JMenuItem("exit");
	exitMenuItem.addActionListener(new ExitAction());
	menu.add(aboutMenuItem);
	menu.add(exitMenuItem);
	frame.setJMenuBar(menuBar);
    }

    private static boolean isMac() {
	return System.getProperty("mrj.version") != null;
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
	if (isMac()) {
	    System.setProperty("apple.laf.useScreenMenuBar", "true");
	    System.setProperty("com.apple.mrj.application.apple.menu.about.name", "ETL Mail");
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	}
	SwingUtilities.invokeLater(new MailGui());
    }
}
