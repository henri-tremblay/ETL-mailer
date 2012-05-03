package etlmail.engine

import java.lang.Override
import java.util.Arrays

import org.apache.velocity.app.VelocityEngine
import org.apache.velocity.tools.ToolContext
import org.easymock.EasyMock.anyObject
import org.easymock.EasyMock.expectLastCall
import org.easymock.EasyMockSupport
import org.jsoup.nodes.Document
import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.EasyMockSugar
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.mail.javamail.MimeMessagePreparator
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestContextManager

import etlmail.engine.css.CssInliner
import etlmail.front.cli.PropertyServerConfiguration

class EngineLessToolMailSender extends ToolMailSender {
  @Override
  def velocityEngine(resourcesDirectory: String): VelocityEngine = null
}

object Mockery {
  @Bean
  def propertyPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer
}

@Configuration
@PropertySource(Array("classpath:mailTool.properties"))
class Mockery extends EasyMockSupport with EasyMockSugar {
  @Bean
  def javaMailSender = mock[JavaMailSender]

  @Bean
  def cssInliner = mock[CssInliner]

  @Bean
  def toolContext = mock[ToolContext]
}

@RunWith(classOf[JUnitRunner])
@ContextConfiguration(classes = Array(classOf[Mockery], classOf[EngineLessToolMailSender], classOf[PropertyServerConfiguration]))
@DirtiesContext
class ToolMailSenderSpec extends FlatSpec with ShouldMatchers with EasyMockSugar {
  new TestContextManager(getClass()).prepareTestInstance(this)

  @Autowired var javaMailSenderMock: JavaMailSender = _

  @Autowired var toolMailSender: ToolMailSender = _

  @Autowired var mockery: Mockery = _

  "sendMail" should "call JavaMailSender" in {
    // given
    expecting {
      javaMailSenderMock.send(anyObject(classOf[MimeMessagePreparator]))
    }
    mockery.replayAll()

    // when
    toolMailSender.sendMail(null)

    // then
    mockery.verifyAll()
  }

  "toStringArray with a 2 element list" should "yield an array with both elements" in {
    // given
    val strings = Arrays.asList("string1", "string2")

    // when
    val stringArray = toolMailSender.toStringArray(strings)

    // then
    stringArray should be(Array("string1", "string2"))
  }

  "toStringArray without a list" should "yield an empty array" in {
    // given
    val strings = null

    // when
    val stringArray = toolMailSender.toStringArray(strings)

    // then
    stringArray should have length (0)
  }

  "getAddressesFromString" should "split a string on commas, ignoring optional finishing commas" in {
    // given
    val addresses = "aaa@aaa.com, BBB@bbb.com,ccc@ccc.com,"

    // when
    val addressList = toolMailSender.getAddressesFromString(addresses)

    // then
    addressList should have size (3)
    addressList should contain("aaa@aaa.com")
    addressList should contain("bbb@bbb.com")
    addressList should contain("ccc@ccc.com")
  }

  "getAddressesFromString" should "work with empty lists" in {
    // given
    val addressesEmpty = ""

    // when
    val addressListEmpty = toolMailSender.getAddressesFromString(addressesEmpty)

    // then
    addressListEmpty should be('empty)
  }

  "convertImagesToCid with duplicate images" should "only keep 1" in {
    // given
    val document = new Document("")
    document.appendElement("img").attr("src", "gnu.gif")
    document.appendElement("img").attr("src", "gnu.gif")

    // when
    val imageNames = toolMailSender.convertImagesToCid(document)

    // then
    imageNames should have size (1)
  }
}
