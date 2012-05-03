package etlmail.front.cli;

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.junit.runner.RunWith

import org.springframework.context.annotation.AnnotationConfigApplicationContext

@RunWith(classOf[JUnitRunner])
class CliAppCtxTest extends FlatSpec with ShouldMatchers {

  "the context" should "load without errors" in {
    new AnnotationConfigApplicationContext(classOf[CliAppCtx]).close()
  }
}
