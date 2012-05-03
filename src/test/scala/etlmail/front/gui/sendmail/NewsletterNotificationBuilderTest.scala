package etlmail.front.gui.sendmail

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.junit.runner.RunWith

@RunWith(classOf[JUnitRunner])
class NewsletterNotificationBuilderTest extends FlatSpec with ShouldMatchers {
  "the form's fields" should "never be null" in {
    val builder = new NewsletterNotificationBuilder

    builder.to should not be null
    builder.cc should not be null
    builder.from should not be null
    builder.subject should not be null
    builder.template should not be null
  }
}
