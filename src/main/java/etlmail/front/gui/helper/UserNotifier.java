package etlmail.front.gui.helper;

import static javax.swing.JOptionPane.*;

import javax.swing.JTextArea;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import etlmail.front.gui.mainframe.MainFrame;

@Component
public class UserNotifier {
    private @Autowired MainFrame mainWindow;

    public void showAbout() {
	showMessageDialog(mainWindow, "ETL mail v0.20\nby FRO, NDN & FME", "About ETL Mail", INFORMATION_MESSAGE);
    }

    public void showError(final Throwable cause) {
	final JTextArea messageArea = new JTextArea(cause.getLocalizedMessage());
	messageArea.setColumns(30);
	messageArea.setLineWrap(true);
	messageArea.setWrapStyleWord(true);
	messageArea.setSize(messageArea.getPreferredSize().width, 1);
	showMessageDialog(mainWindow, messageArea, "Oops", ERROR_MESSAGE);
    }
}
