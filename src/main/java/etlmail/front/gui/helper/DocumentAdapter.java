package etlmail.front.gui.helper;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public abstract class DocumentAdapter implements DocumentListener {
	@Override
	public final void removeUpdate(DocumentEvent e) {
		update(e);
	}

	@Override
	public final void insertUpdate(DocumentEvent e) {
		update(e);
	}

	@Override
	public final void changedUpdate(DocumentEvent e) {
		update(e);
	}

	private void update(DocumentEvent e) {
		update(ModelUtils.getText(e.getDocument()));
	}

	protected abstract void update(String newText);
}