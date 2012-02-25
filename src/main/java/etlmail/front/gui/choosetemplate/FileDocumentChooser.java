package etlmail.front.gui.choosetemplate;

import java.awt.Component;
import java.awt.HeadlessException;
import java.io.File;

import javax.swing.JFileChooser;


@SuppressWarnings("serial")
public class FileDocumentChooser extends JFileChooser {
    private final FileDocument document;

    public FileDocumentChooser(FileDocument document) {
	this.document = document;
    }

    @Override
    public int showOpenDialog(Component arg0) throws HeadlessException {
	setFile();
	final int result = super.showOpenDialog(arg0);
	if (result == APPROVE_OPTION) {
	    document.setFile(getSelectedFile());
	}
	return result;
    }

    private void setFile() {
	final File currentFile = document.getFile();
	if (currentFile.isFile()) {
	    setSelectedFile(currentFile);
	} else if (currentFile.isDirectory()) {
	    setCurrentDirectory(currentFile);
	}
    }
}
