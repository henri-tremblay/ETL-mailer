package etlmail.front.gui.preferences

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.junit.runner.RunWith

@RunWith(classOf[JUnitRunner])
class SwingServerConfigurationTest extends FlatSpec with ShouldMatchers {
  "the form's fields" should "never be null" in {
    // given
    val builder = new SwingServerConfiguration

    // then
    builder.host should be("")
    builder.port should be(25)
    builder.password should be("")
    builder.username should be("")
  }
}
