package etlmail.context

import org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE

import org.apache.velocity.app.VelocityEngine
import org.apache.velocity.runtime.RuntimeConstants
import org.apache.velocity.runtime.log.CommonsLogLogChute
import org.apache.velocity.tools.ToolContext
import org.apache.velocity.tools.ToolManager
import org.springframework.context.annotation._
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

import etlmail.engine.ToolMailSender

object MailToolAppCtx {
  @Bean
  def configurer = new PropertySourcesPlaceholderConfigurer
}

@Configuration
@ComponentScan(basePackageClasses = Array( //
  classOf[etlmail.context.ComponentScanMarker], //
  classOf[etlmail.engine.ComponentScanMarker] //
  ))
class MailToolAppCtx {
  @Bean
  @Scope(SCOPE_PROTOTYPE)
  def toolMailSender =
    new ToolMailSender() {
      @Override
      def velocityEngine(resourcesDirectory: String): VelocityEngine = {
        val velocityEngine = new VelocityEngine()
        velocityEngine.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, resourcesDirectory)
        velocityEngine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, classOf[CommonsLogLogChute].getName())
        return velocityEngine
      }
    }

  @Bean
  def toolContext(): ToolContext = new ToolManager().createContext()
}
