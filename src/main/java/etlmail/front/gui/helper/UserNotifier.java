package etlmail.front.gui.helper;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

import javax.swing.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import etlmail.front.gui.about.AboutWindow;
import etlmail.front.gui.mainframe.MainFrame;

@Component
public class UserNotifier {
	private @Autowired
	MainFrame mainWindow;
	private @Autowired
	AboutWindow aboutWindow;

	public void showAbout() {
		aboutWindow.show();
	}

	public void showError(final Throwable cause) {
		final JComponent messageArea = messageArea(cause.getLocalizedMessage());
		showMessageDialog(mainWindow.top(), messageArea,
				"Somebody set us up the bomb", ERROR_MESSAGE);
	}

	public static JComponent messageArea(String message) {
		final JTextArea text = new JTextArea(message, 5, 30);
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		return new JScrollPane(text);
	}
}
