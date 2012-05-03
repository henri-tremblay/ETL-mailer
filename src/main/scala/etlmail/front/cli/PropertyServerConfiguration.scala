package etlmail.front.cli;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import etlmail.context.ServerConfiguration;

@Component
class PropertyServerConfiguration extends ServerConfiguration {
  @Value("${mail.sender.host}") private var host_ : String = _
  @Value("${mail.sender.port:25}") private var port_ : Int = _
  @Value("${mail.sender.username:}") private var username_ : String = _
  @Value("${mail.sender.password:}") private var password_ : String = _

  def host(): String = host_

  def port(): Int = port_

  def username(): String = username_

  def password(): String = password_
}
