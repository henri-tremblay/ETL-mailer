package etlmail.context

import java.io.InputStream

import javax.mail.internet.MimeMessage

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.MailException
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail._
import org.springframework.stereotype.Component

@Component
class MailSender extends JavaMailSender {

  @Autowired private var configuration: ServerConfiguration = _

  private val bean = new JavaMailSenderImpl

  bean.setJavaMailProperties(new PropertyBuilder //
    key "mail.smtp.auth" yields "true" //
    key "mail.smtp.starttls.enable" yields "true" //
    asProperties)

  private def reconfigure(): JavaMailSender = {
    bean.setHost(configuration.host)
    bean.setPort(configuration.port)
    bean.setUsername(configuration.username)
    bean.setPassword(configuration.password)
    bean
  }

  @Override
  @throws(classOf[MailException])
  def send(simpleMessage: SimpleMailMessage) {
    reconfigure().send(simpleMessage);
  }

  @Override
  @throws(classOf[MailException])
  def send(simpleMessages: Array[SimpleMailMessage]) {
    reconfigure().send(simpleMessages);
  }

  @Override
  def createMimeMessage(): MimeMessage = reconfigure().createMimeMessage()

  @Override
  @throws(classOf[MailException])
  def createMimeMessage(contentStream: InputStream): MimeMessage = reconfigure().createMimeMessage(contentStream)

  @Override
  @throws(classOf[MailException])
  def send(mimeMessage: MimeMessage) {
    reconfigure().send(mimeMessage)
  }

  @Override
  @throws(classOf[MailException])
  def send(mimeMessages: Array[MimeMessage]) {
    reconfigure().send(mimeMessages)
  }

  @Override
  @throws(classOf[MailException])
  def send(mimeMessagePreparator: MimeMessagePreparator) {
    reconfigure().send(mimeMessagePreparator)
  }

  @Override
  @throws(classOf[MailException])
  def send(mimeMessagePreparators: Array[MimeMessagePreparator]) {
    reconfigure().send(mimeMessagePreparators)
  }
}
