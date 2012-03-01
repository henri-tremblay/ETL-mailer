package etlmail.front.gui.choosetemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;

import etlmail.front.gui.helper.DocumentAdapter;
import etlmail.front.gui.helper.ModelUtils;

public class FileDocument implements Document {
    private final Document wrapped;
    private final List<FilenameListener> listeners = new ArrayList<FilenameListener>();

    public FileDocument(Document wrapped) {
	this.wrapped = wrapped;
	wrapped.addDocumentListener(new DocumentAdapter() {
	    @Override
	    protected void update(String newText) {
		for (final FilenameListener listener : listeners) {
		    listener.update(new File(newText));
		}
	    }
	});
    }

    public FileDocument() {
	this(new DefaultStyledDocument());
    }

    public File getFile() {
	return new File(ModelUtils.getText(wrapped));
    }

    public void setFile(File file) {
	ModelUtils.setText(wrapped, file.getPath());
    }

    public void addFilenameListener(FilenameListener listener) {
	listeners.add(listener);
    }

    @Override
    public void addDocumentListener(DocumentListener arg0) {
	wrapped.addDocumentListener(arg0);
    }

    @Override
    public void addUndoableEditListener(UndoableEditListener arg0) {
	wrapped.addUndoableEditListener(arg0);
    }

    @Override
    public Position createPosition(int arg0) throws BadLocationException {
	return wrapped.createPosition(arg0);
    }

    @Override
    public Element getDefaultRootElement() {
	return wrapped.getDefaultRootElement();
    }

    @Override
    public Position getEndPosition() {
	return wrapped.getEndPosition();
    }

    @Override
    public int getLength() {
	return wrapped.getLength();
    }

    @Override
    public Object getProperty(Object arg0) {
	return wrapped.getProperty(arg0);
    }

    @Override
    public Element[] getRootElements() {
	return wrapped.getRootElements();
    }

    @Override
    public Position getStartPosition() {
	return wrapped.getStartPosition();
    }

    @Override
    public String getText(int arg0, int arg1) throws BadLocationException {
	return wrapped.getText(arg0, arg1);
    }

    @Override
    public void getText(int arg0, int arg1, Segment arg2) throws BadLocationException {
	wrapped.getText(arg0, arg1, arg2);
    }

    @Override
    public void insertString(int arg0, String arg1, AttributeSet arg2) throws BadLocationException {
	wrapped.insertString(arg0, arg1, arg2);
    }

    @Override
    public void putProperty(Object arg0, Object arg1) {
	wrapped.putProperty(arg0, arg1);
    }

    @Override
    public void remove(int arg0, int arg1) throws BadLocationException {
	wrapped.remove(arg0, arg1);
    }

    @Override
    public void removeDocumentListener(DocumentListener arg0) {
	wrapped.removeDocumentListener(arg0);
    }

    @Override
    public void removeUndoableEditListener(UndoableEditListener arg0) {
	wrapped.removeUndoableEditListener(arg0);
    }

    @Override
    public void render(Runnable arg0) {
	wrapped.render(arg0);
    }
}
