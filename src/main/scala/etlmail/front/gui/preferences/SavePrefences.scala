package etlmail.front.gui.preferences

import java.io._
import java.util.Properties

import javax.annotation.PostConstruct

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

import com.google.common.io.Closeables

import com.weiglewilczek.slf4s.Logging

import etlmail.front.gui.application.InvokeAndWait
import etlmail.front.gui.application.ShutdownEvent
import etlmail.front.gui.sendmail.NewsletterNotificationBuilder

object SavePrefences {
  val FILENAME: String = "mailgui.properties"

  val TO: String = "to"
  val FROM: String = "from"
  val SUBJECT: String = "subject"
  val TEMPLATE: String = "template"

  val SERVER: String = "server"
  val PORT: String = "port"
  val USER: String = "user"
}

@Component
class SavePrefences extends ApplicationListener[ShutdownEvent] with Logging {
  import SavePrefences._

  @Autowired private var notificationBuilder: NewsletterNotificationBuilder = _
  @Autowired private var serverConfiguration: SwingServerConfiguration = _

  @PostConstruct
  def init() {
    fromProperties(readProperties())
  }

  private def readProperties(): Properties = {
    val restored = new Properties
    var in: InputStream = null
    try {
      in = new BufferedInputStream(new FileInputStream(FILENAME))
      restored.load(in)
      in.close()
    } catch {
      case e: IOException =>
        logger.error("Cannot read preferences", e)
    } finally {
      Closeables.closeQuietly(in)
    }
    return restored
  }

  @InvokeAndWait
  private def fromProperties(restored: Properties) {
    notificationBuilder.to(restored.getProperty(TO))
    notificationBuilder.from(restored.getProperty(FROM))
    notificationBuilder.subject(restored.getProperty(SUBJECT))
    val template = restored.getProperty(TEMPLATE)
    if (template != null) {
      notificationBuilder.template(new File(template))
    }

    serverConfiguration.setHost(restored.getProperty(SERVER))
    val port = restored.getProperty(PORT)
    if (null != port) {
      serverConfiguration.setPort(Integer.parseInt(port))
    }
    serverConfiguration.setUsername(restored.getProperty(USER))
  }

  @Override
  def onApplicationEvent(event: ShutdownEvent) {
    writeProperties(toProperties())
  }

  @InvokeAndWait
  private def toProperties(): Properties = {
    val toSave = new Properties
    toSave.setProperty(TO, notificationBuilder.to)
    toSave.setProperty(FROM, notificationBuilder.from)
    toSave.setProperty(SUBJECT, notificationBuilder.subject)
    toSave.setProperty(TEMPLATE, notificationBuilder.template.getPath)

    toSave.setProperty(SERVER, serverConfiguration.host)
    toSave.setProperty(PORT, Integer.toString(serverConfiguration.port))
    toSave.setProperty(USER, serverConfiguration.username)

    return toSave
  }

  private def writeProperties(toSave: Properties) {
    var out: OutputStream = null
    try {
      out = new BufferedOutputStream(new FileOutputStream(FILENAME))
      toSave.store(out, null)
      out.close()
    } catch {
      case e: IOException =>
        logger.error("Cannot save preferences", e)
    } finally {
      Closeables.closeQuietly(out)
    }
  }
}
