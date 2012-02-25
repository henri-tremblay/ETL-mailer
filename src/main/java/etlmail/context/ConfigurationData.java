package etlmail.context;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationData {
    @Value("${mail.sender.host}") public String host;
    @Value("${mail.sender.port:25}") public int port;
    @Value("${mail.sender.username:}") public String username;
    @Value("${mail.sender.password:}") private String password;
    @Value("${mail.resources.directory:/}") public String resourcesDirectory;

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }
}
