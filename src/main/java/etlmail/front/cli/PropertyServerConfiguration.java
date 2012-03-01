package etlmail.front.cli;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import etlmail.context.ServerConfiguration;

@Component
public class PropertyServerConfiguration implements ServerConfiguration {
    @Value("${mail.sender.host}") private String host;
    @Value("${mail.sender.port:25}") private int port;
    @Value("${mail.sender.username:}") private String username;
    @Value("${mail.sender.password:}") private String password;

    @Override
    public String getHost() {
	return host;
    }

    @Override
    public int getPort() {
	return port;
    }

    @Override
    public String getUsername() {
	return username;
    }

    @Override
    public String getPassword() {
	return password;
    }
}
