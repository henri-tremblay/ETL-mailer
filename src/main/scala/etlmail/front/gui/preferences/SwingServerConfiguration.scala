package etlmail.front.gui.preferences

import etlmail.front.gui.helper.ModelUtils.setText

import javax.swing.text.DefaultStyledDocument
import javax.swing.text.Document

import org.springframework.stereotype.Component

import etlmail.context.ServerConfiguration
import etlmail.front.gui.application.InvokeAndWait
import etlmail.front.gui.helper.DocumentAdapter

@Component
class SwingServerConfiguration extends ServerConfiguration {

  val hostDocument = new DefaultStyledDocument
  val portDocument = new DefaultStyledDocument
  val usernameDocument = new DefaultStyledDocument
  val passwordDocument = new DefaultStyledDocument

  @volatile private var host_ : String = ""
  @volatile private var port_ : Integer = 25
  @volatile private var username_ : String = ""
  @volatile private var password_ : String = ""

  init()

  def host: String = host_
  def port: Int = port_
  def username: String = username_
  def password: String = password_

  @InvokeAndWait
  private def init() {
    hostDocument.addDocumentListener(new DocumentAdapter() {
      protected def update(newText: String) {
        host_ = newText
      }
    })
    portDocument.addDocumentListener(new DocumentAdapter() {
      protected def update(newText: String) {
        port_ = Integer.parseInt(newText)
      }
    })
    usernameDocument.addDocumentListener(new DocumentAdapter() {
      protected def update(newText: String) {
        username_ = newText
      }
    })
    passwordDocument.addDocumentListener(new DocumentAdapter() {
      protected def update(newText: String) {
        password_ = newText
      }
    })
  }

  /**
   * Only call from the EDT
   */
  def setHost(host: String) {
    setText(hostDocument, host)
  }

  /**
   * Only call from the EDT
   */
  def setPort(port: Int) {
    setText(portDocument, Integer.toString(port))
  }

  /**
   * Only call from the EDT
   */
  def setUsername(username: String) {
    setText(usernameDocument, username)
  }
}
