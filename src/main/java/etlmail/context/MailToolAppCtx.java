package etlmail.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.aspectj.EnableSpringConfigured;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

@Configuration
@EnableSpringConfigured
@ComponentScan(basePackageClasses = { //
etlmail.context.ComponentScanMarker.class, //
	etlmail.engine.ComponentScanMarker.class //
})
public class MailToolAppCtx {

    @Autowired private ConfigurationData configuration;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
	return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public VelocityEngineFactoryBean velocityEngineMailTooSender() {
	final VelocityEngineFactoryBean bean = new VelocityEngineFactoryBean();
	bean.setVelocityProperties(new PropertyBuilder() //
		.key("class.resource.loader.class").yields("org.apache.velocity.runtime.resource.loader.FileResourceLoader") //
		.key("file.resource.loader.path").yields(configuration.resourcesDirectory) //
		.key("input.encoding").yields("UTF-8") //
		.asProperties());
	return bean;
    }
}
