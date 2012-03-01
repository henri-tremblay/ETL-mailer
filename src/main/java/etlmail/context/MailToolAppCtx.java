package etlmail.context;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import java.io.IOException;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.aspectj.EnableSpringConfigured;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

import etlmail.engine.ToolMailSender;

@Configuration
@EnableSpringConfigured
@ComponentScan(basePackageClasses = { //
etlmail.context.ComponentScanMarker.class, //
	etlmail.engine.ComponentScanMarker.class //
})
public class MailToolAppCtx {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
	return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public ToolMailSender toolMailSender() {
	return new ToolMailSender() {
	    @Override
	    protected VelocityEngine velocityEngine(String resourcesDirectory) throws VelocityException, IOException {
		return velocityEngineMailToolSender(resourcesDirectory);
	    }
	};
    }

    private VelocityEngine velocityEngineMailToolSender(String resourcesDirectory) throws VelocityException, IOException {
	// TODO se passer de la factory
	final VelocityEngineFactoryBean bean = new VelocityEngineFactoryBean();
	bean.setVelocityProperties(new PropertyBuilder() //
		.key("class.resource.loader.class").yields("org.apache.velocity.runtime.resource.loader.FileResourceLoader") //
		.key("file.resource.loader.path").yields(resourcesDirectory) //
		.key("input.encoding").yields("UTF-8") //
		.asProperties());
	return bean.createVelocityEngine();
    }
}
