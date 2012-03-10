package etlmail.front.gui.helper;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public final class ModelUtils {
	private ModelUtils() {

	}

	public static void setText(Document document, String value) {
		try {
			document.remove(0, document.getLength());
			document.insertString(0, value, null);
		} catch (final BadLocationException e) {
			throw new IllegalStateException("Cannot happen", e);
		}
	}

	public static String getText(Document document) {
		try {
			return document.getText(0, document.getLength());
		} catch (final BadLocationException e) {
			throw new IllegalStateException("Cannot happen", e);
		}
	}
}
