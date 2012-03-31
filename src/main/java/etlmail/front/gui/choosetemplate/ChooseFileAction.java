package etlmail.front.gui.choosetemplate;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChooseFileAction implements ActionListener {
    private final FileDocumentChooser fileChooser;
    private final Container frame;

    public ChooseFileAction(FileDocumentChooser fileChooser, Container frame) {
	this.fileChooser = fileChooser;
	this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
	fileChooser.showOpenDialog(frame);
    }
}