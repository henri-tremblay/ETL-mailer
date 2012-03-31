package etlmail.front.gui.application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.pushingpixels.substance.api.skin.SubstanceBusinessLookAndFeel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import etlmail.front.gui.helper.FrameHolder;
import etlmail.front.gui.mainframe.MainFrame;

@Component
public class MailGui implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(MailGui.class);

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
	frame.show();
	log.debug("Up and running");
    }

    private void addMenuBar(final FrameHolder frame) {
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
	log.debug("Starting");
	if (isMac()) {
	    System.setProperty("apple.laf.useScreenMenuBar", "true");
	    System.setProperty("com.apple.mrj.application.apple.menu.about.name", "ETL Mail");
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	}

	UIManager.setLookAndFeel(lookAndFeel());
	final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(GuiAppCtx.class);
	SwingUtilities.invokeLater(ctx.getBean(MailGui.class));
    }

    private static String lookAndFeel() {
	for (final UIManager.LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
	    if ("Nimbus".equals(laf.getName())) {
		return laf.getClassName();
	    }
	}
	return SubstanceBusinessLookAndFeel.class.getName();
    }
}
