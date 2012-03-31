package etlmail.front.gui.preferences;

import static etlmail.front.gui.helper.ModelUtils.setText;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;

import org.springframework.stereotype.Component;

import etlmail.context.ServerConfiguration;
import etlmail.front.gui.application.InvokeAndWait;
import etlmail.front.gui.helper.DocumentAdapter;

@Component
public class SwingServerConfiguration implements ServerConfiguration {

    private final Document hostDocument = new DefaultStyledDocument();
    private final Document portDocument = new DefaultStyledDocument();
    private final Document usernameDocument = new DefaultStyledDocument();
    private final Document passwordDocument = new DefaultStyledDocument();

    private volatile String host = "";
    private volatile Integer port = 25;
    private volatile String username = "";
    private volatile String password = "";

    @InvokeAndWait
    public SwingServerConfiguration() {
	hostDocument.addDocumentListener(new DocumentAdapter() {
	    @Override
	    protected void update(String newText) {
		host = newText;
	    }
	});
	portDocument.addDocumentListener(new DocumentAdapter() {
	    @Override
	    protected void update(String newText) {
		port = Integer.parseInt(newText);
	    }
	});
	usernameDocument.addDocumentListener(new DocumentAdapter() {
	    @Override
	    protected void update(String newText) {
		username = newText;
	    }
	});
	passwordDocument.addDocumentListener(new DocumentAdapter() {
	    @Override
	    protected void update(String newText) {
		password = newText;
	    }
	});
    }

    public Document getHostDocument() {
	return hostDocument;
    }

    @Override
    public String getHost() {
	return host;
    }

    /**
     * Only call from the EDT
     */
    public void setHost(String host) {
	setText(hostDocument, host);
    }

    public Document getPortDocument() {
	return portDocument;
    }

    @Override
    public int getPort() {
	return port;
    }

    /**
     * Only call from the EDT
     */
    public void setPort(int port) {
	setText(portDocument, Integer.toString(port));
    }

    public Document getUserDocument() {
	return usernameDocument;
    }

    @Override
    public String getUsername() {
	return username;
    }

    /**
     * Only call from the EDT
     */
    public void setUsername(String username) {
	setText(usernameDocument, username);
    }

    public Document getPasswordDocument() {
	return passwordDocument;
    }

    @Override
    public String getPassword() {
	return password;
    }
}
