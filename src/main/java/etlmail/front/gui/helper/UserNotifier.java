package etlmail.front.gui.helper;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

import javax.swing.JTextArea;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import etlmail.front.gui.about.AboutWindow;
import etlmail.front.gui.mainframe.MainFrame;

@Component
public class UserNotifier {
    private @Autowired MainFrame mainWindow;
    private @Autowired AboutWindow aboutWindow;

    public void showAbout() {
	aboutWindow.show();
    }

    public void showError(final Throwable cause) {
	final JTextArea messageArea = messageArea(cause.getLocalizedMessage(), 30);
	showMessageDialog(mainWindow.top(), messageArea, "Oops", ERROR_MESSAGE);
    }

    public static JTextArea messageArea(String message, int columns) {
	final JTextArea messageArea = new JTextArea(message);
	messageArea.setEnabled(false);
	messageArea.setColumns(columns);
	messageArea.setLineWrap(true);
	messageArea.setWrapStyleWord(true);
	messageArea.setSize(messageArea.getPreferredSize().width, 1);
	return messageArea;
    }
}
