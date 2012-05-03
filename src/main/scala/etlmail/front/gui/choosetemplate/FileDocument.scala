package etlmail.front.gui.choosetemplate

import java.io.File
import java.util.ArrayList
import java.util.List

import javax.swing.event.DocumentListener
import javax.swing.event.UndoableEditListener
import javax.swing.text._

import etlmail.front.gui.helper.DocumentAdapter
import etlmail.front.gui.helper.ModelUtils

import scala.collection.JavaConversions._

class FileDocument(wrapped: Document) extends Document {
  private val listeners = new ArrayList[FilenameListener]

  wrapped.addDocumentListener(new DocumentAdapter {
    protected def update(newText: String) {
      for (listener <- listeners) {
        listener.update(new File(newText))
      }
    }
  })

  def this() {
    this(new DefaultStyledDocument)
  }

  def getFile(): File = new File(ModelUtils.getText(wrapped))

  def setFile(file: File) {
    ModelUtils.setText(wrapped, file.getPath)
  }

  def addFilenameListener(listener: FilenameListener) {
    listeners.add(listener)
  }

  def addDocumentListener(listener: DocumentListener) {
    wrapped.addDocumentListener(listener)
  }

  def addUndoableEditListener(listener: UndoableEditListener) {
    wrapped.addUndoableEditListener(listener)
  }

  def createPosition(arg0: Int): Position = wrapped.createPosition(arg0)

  def getDefaultRootElement(): Element = wrapped.getDefaultRootElement

  def getEndPosition(): Position = wrapped.getEndPosition

  def getLength(): Int = wrapped.getLength

  def getProperty(arg0: Any): Object = wrapped.getProperty(arg0)

  def getRootElements(): Array[Element] = wrapped.getRootElements

  def getStartPosition(): Position = wrapped.getStartPosition

  def getText(arg0: Int, arg1: Int): String = wrapped.getText(arg0, arg1)

  def getText(arg0: Int, arg1: Int, arg2: Segment) {
    wrapped.getText(arg0, arg1, arg2)
  }

  def insertString(arg0: Int, arg1: String, arg2: AttributeSet) {
    wrapped.insertString(arg0, arg1, arg2)
  }

  def putProperty(arg0: Any, arg1: Any) {
    wrapped.putProperty(arg0, arg1)
  }

  def remove(arg0: Int, arg1: Int) {
    wrapped.remove(arg0, arg1)
  }

  def removeDocumentListener(arg0: DocumentListener) {
    wrapped.removeDocumentListener(arg0)
  }

  def removeUndoableEditListener(arg0: UndoableEditListener) {
    wrapped.removeUndoableEditListener(arg0)
  }

  def render(arg0: Runnable) {
    wrapped.render(arg0)
  }
}
