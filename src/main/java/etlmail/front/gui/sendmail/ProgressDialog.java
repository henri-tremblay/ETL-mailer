package etlmail.front.gui.sendmail;

import java.awt.Frame;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;

import etlmail.front.gui.application.WindowJanitor;

@SuppressWarnings("serial")
public class ProgressDialog extends JDialog {
    public ProgressDialog(Frame parent, final SwingWorker<?, ?> worker) {
	super(parent, "Sending mail", true);
	setLayout(new MigLayout());
	setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	add(progressBar());
	pack();
	addComponentListener(new ComponentAdapter() {
	    @Override
	    public void componentShown(ComponentEvent e) {
		worker.execute();
	    }
	});
    }

    private JProgressBar progressBar() {
	final JProgressBar progressBar = new JProgressBar();
	progressBar.setString("Sending");
	progressBar.setIndeterminate(true);
	progressBar.setStringPainted(true);
	return progressBar;
    }

    @Autowired
    public void register(WindowJanitor janitor) {
	janitor.register(this);
    }
}