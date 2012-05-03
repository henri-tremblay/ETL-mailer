package etlmail.front.gui.helper

import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

abstract class DocumentAdapter extends DocumentListener {
  final def removeUpdate(e: DocumentEvent) {
    update(e)
  }

  final def insertUpdate(e: DocumentEvent) {
    update(e)
  }

  final def changedUpdate(e: DocumentEvent) {
    update(e)
  }

  def update(e: DocumentEvent) {
    update(ModelUtils.getText(e.getDocument))
  }

  protected def update(newText: String)
}
