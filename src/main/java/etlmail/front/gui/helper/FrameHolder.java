package etlmail.front.gui.helper;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import org.springframework.beans.factory.annotation.Autowired;

import etlmail.front.gui.application.InvokeAndWait;
import etlmail.front.gui.application.WindowJanitor;

public class FrameHolder {
	protected final JFrame frame;

	@InvokeAndWait
	public FrameHolder() {
		frame = new JFrame();
	}

	@Autowired
	public void register(WindowJanitor janitor) {
		janitor.register(frame);
	}

	public void show() {
		frame.pack();
        frame.setLocationByPlatform(true);
		frame.setVisible(true);
	}

	public void setJMenuBar(JMenuBar menuBar) {
		frame.setJMenuBar(menuBar);
	}

	public JFrame top() {
		return frame;
	}
}