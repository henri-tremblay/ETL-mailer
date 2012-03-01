package etlmail.front.gui.application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import etlmail.front.gui.helper.UserNotifier;

@Component
public class MailGui implements Runnable {
    private @Autowired ExitAction exitAction;
    private @Autowired UserNotifier notifier;
    private @Autowired MacListener macListener;
    private @Autowired JFrame frame;

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
	final JMenuItem aboutMenuItem = new JMenuItem("about");
	aboutMenuItem.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		notifier.showAbout();
	    }
	});
	final JMenuItem exitMenuItem = new JMenuItem("exit");
	exitMenuItem.addActionListener(exitAction);
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
	final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(GuiAppCtx.class);
	SwingUtilities.invokeLater(ctx.getBean(MailGui.class));
    }
}
