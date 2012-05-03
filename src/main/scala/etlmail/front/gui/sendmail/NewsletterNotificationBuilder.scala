package etlmail.front.gui.sendmail;

import etlmail.front.gui.helper.ModelUtils.setText;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;

import org.springframework.stereotype.Component;

import etlmail.engine.NewsletterNotification;
import etlmail.front.gui.application.InvokeAndWait;
import etlmail.front.gui.choosetemplate.FileDocument;
import etlmail.front.gui.choosetemplate.FilenameListener;
import etlmail.front.gui.helper.DocumentAdapter;

@Component
class NewsletterNotificationBuilder {
  val subjectDocument = new DefaultStyledDocument
  val templateDocument = new FileDocument
  val fromDocument = new DefaultStyledDocument
  val toDocument = new DefaultStyledDocument

  @volatile private var subject_ = ""
  @volatile private var template_ = new File("")
  @volatile private var from_ = ""
  @volatile private var to_ = ""

  def subject: String = subject_
  def template: File = template_
  def from: String = from_
  def to: String = to_
  val cc: String = ""

  private val variables = new HashMap[String, Any]

  init()

  @InvokeAndWait
  private def init() {
    subjectDocument.addDocumentListener(new DocumentAdapter {
      protected def update(newText: String) {
        subject_ = newText
      }
    })
    templateDocument.addFilenameListener(new FilenameListener {
      def update(file: File) {
        template_ = file
      }
    })
    fromDocument.addDocumentListener(new DocumentAdapter {
      @Override
      protected def update(newText: String) {
        from_ = newText
      }
    })
    toDocument.addDocumentListener(new DocumentAdapter {
      @Override
      protected def update(newText: String) {
        to_ = newText
      }
    })
  }

  def build(): NewsletterNotification = {
    val templateFile = template.getAbsoluteFile
    return new NewsletterNotification(subject, templateFile.getName, templateFile.getParent, from, to, cc, variables)
  }

  /**
   * Only call from the EDT
   */
  def template(template: File): NewsletterNotificationBuilder = {
    templateDocument.setFile(template)
    return this
  }

  /**
   * Only call from the EDT
   */
  def subject(subject: String): NewsletterNotificationBuilder = {
    setText(subjectDocument, subject)
    return this
  }

  /**
   * Only call from the EDT
   */
  def from(from: String): NewsletterNotificationBuilder = {
    setText(fromDocument, from)
    return this
  }

  /**
   * Only call from the EDT
   */
  def to(to: String): NewsletterNotificationBuilder = {
    setText(toDocument, to)
    return this
  }
}
