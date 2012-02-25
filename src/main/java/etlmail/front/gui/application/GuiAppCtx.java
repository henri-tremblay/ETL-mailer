package etlmail.front.gui.application;

import org.springframework.beans.factory.aspectj.EnableSpringConfigured;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource({ "classpath:mailGuiServer.properties" })
@ComponentScan(basePackageClasses = { //
etlmail.front.gui.ComponentScanMarker.class, //
	etlmail.context.ComponentScanMarker.class //
})
@EnableSpringConfigured
public class GuiAppCtx {

}
