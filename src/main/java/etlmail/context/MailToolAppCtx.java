package etlmail.context;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.CommonsLogLogChute;
import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.ToolManager;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import etlmail.engine.ToolMailSender;

@Configuration
@ComponentScan(basePackageClasses = { //
etlmail.context.ComponentScanMarker.class, //
	etlmail.engine.ComponentScanMarker.class //
})
public class MailToolAppCtx {
    @Bean
    public static PropertySourcesPlaceholderConfigurer configurer() {
	return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public ToolMailSender toolMailSender() {
	return new ToolMailSender() {
	    @Override
	    protected VelocityEngine velocityEngine(String resourcesDirectory) {
		final VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, resourcesDirectory);
		velocityEngine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, CommonsLogLogChute.class.getName());
		return velocityEngine;
	    }
	};
    }

    @Bean
    public ToolContext toolContext() {
	final ToolManager toolManager = new ToolManager();
	return toolManager.createContext();
    }
}
