package etlmail.front.gui.helper;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

import java.awt.Component;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Dialogs {

    public static void showAbout(Component parent) {
	JOptionPane.showMessageDialog(parent, "ETL mail v0.20\nby FRO, NDN & FME", "About ETL Mail", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showError(Component parent, final Throwable cause) {
	final JTextArea messageArea = new JTextArea(cause.getLocalizedMessage());
	messageArea.setColumns(30);
	messageArea.setLineWrap(true);
	messageArea.setWrapStyleWord(true);
	messageArea.setSize(messageArea.getPreferredSize().width, 1);
	JOptionPane.showMessageDialog(parent, messageArea, "Oops", ERROR_MESSAGE);
    }
}
