package etlmail.engine

import java.io._
import java.util._

import javax.mail.internet.MimeMessage

import org.apache.velocity.VelocityContext
import org.apache.velocity.app.VelocityEngine
import org.apache.velocity.exception.VelocityException
import org.apache.velocity.tools.ToolContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.FileSystemResource
import org.springframework.mail.javamail._

import etlmail.engine.css.CssInliner
import etlmail.front.gui.application.SwingPolicies

import scala.collection.JavaConversions._

abstract class ToolMailSender {
  private val log = LoggerFactory.getLogger(classOf[ToolMailSender])

  @Autowired var javaMailSender: JavaMailSender = null

  @Autowired var cssInliner: CssInliner = null

  @Autowired var toolContext: ToolContext = null

  @throws(classOf[VelocityException])
  @throws(classOf[IOException])
  protected def velocityEngine(resourcesDirectory: String): VelocityEngine

  def sendMail(notification: NewsletterNotification) {
    javaMailSender.send(new MimeMessagePreparator() {
      @Override
      def prepare(mimeMessage: MimeMessage) {
        val message = new MimeMessageHelper(mimeMessage, true)
        message.setTo(toStringArray(getAddressesFromString(notification.to))) // Set destinators
        message.setCc(toStringArray(getAddressesFromString(notification.cc))) // Set CC destinators
        message.setFrom(notification.from) // Set source email
        message.setSubject(notification.subject) // Set eMail
        // Subject

        val text = render(notification)

        val doc = Jsoup.parse(text)

        val imageNames = convertImagesToCid(doc)
        cssInliner.inlineStyles(doc)

        message.setText(doc.outerHtml(), true)

        val resources = new File(notification.resourcesPath)
        // Adding Inline Resources
        for (imageName <- imageNames) {
          val image = new FileSystemResource(new File(resources, imageName))
          message.addInline(imageName, image)
        }
      }
    })
  }

  @throws(classOf[IOException])
  def render(notification: NewsletterNotification): String = {
    val velocityEngine = this.velocityEngine(notification.resourcesPath)
    val result = new StringWriter()
    val velocityContext = new VelocityContext(notification.variables, toolContext)
    velocityEngine.mergeTemplate(notification.template, "UTF-8", velocityContext, result)
    return result.toString()
  }

  def getAddressesFromString(addresses: String): List[String] = {
    val tokenizer = new StringTokenizer(addresses, ",")
    val addressList = new ArrayList[String]()
    while (tokenizer.hasMoreElements()) {
      val addresse = tokenizer.nextElement().asInstanceOf[String].trim().toLowerCase()
      addressList.add(addresse)
    }

    return addressList
  }

  def toStringArray(strings: List[String]): Array[String] =
    if (strings == null) {
      new Array[String](0)
    } else {
      strings.toArray(new Array[String](strings.size()))
    }

  def convertImagesToCid(doc: Document): Collection[String] = {
    val imageNames: Set[String] = new HashSet[String]()
    for (image <- doc.select("img")) {
      val source = image.attr("src")
      imageNames.add(source)
      image.attr("src", "cid:" + source)
      log.debug("Convert image to cid :" + source)
    }
    return imageNames
  }
}
