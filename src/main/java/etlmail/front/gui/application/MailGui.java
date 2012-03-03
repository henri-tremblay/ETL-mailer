package etlmail.front.gui.application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import etlmail.front.gui.mainframe.MainFrame;

@Component
public class MailGui implements Runnable {
    private @Autowired MacListener macListener;
    private @Autowired MainFrame frame;
    private @Autowired ApplicationEventHandler eventHandler;

    @Override
    public void run() {
	if (isMac()) {
	    macListener.enable();
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
	final JMenuItem aboutMenuItem = new JMenuItem("About");
	aboutMenuItem.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		eventHandler.showAbout();
	    }
	});
	final JMenuItem preferencesMenuItem = new JMenuItem("Preferences");
	preferencesMenuItem.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		eventHandler.showPreferences();
	    }
	});
	final JMenuItem exitMenuItem = new JMenuItem("Exit");
	exitMenuItem.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		eventHandler.shutDown(e);
	    }
	});
	menu.add(aboutMenuItem);
	menu.add(preferencesMenuItem);
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
	final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(GuiAppCtx.class);
	SwingUtilities.invokeLater(ctx.getBean(MailGui.class));
    }
}
