package etlmail.cli;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import etlmail.front.cli.CliAppCtx;

public class CliAppCtxTest {

	@Test
	public void instantiate() {
		new AnnotationConfigApplicationContext(CliAppCtx.class).close();
	}
}
