package etlmail.front.gui.preferences;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import java.awt.Container;

import javax.annotation.PostConstruct;
import javax.swing.*;

import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import etlmail.front.gui.application.InvokeAndWait;
import etlmail.front.gui.application.WindowJanitor;

@Component
@Scope(SCOPE_PROTOTYPE)
public class PreferencesWindow {
    private @Autowired SwingServerConfiguration serverConfiguration;
    private final JFrame frame;

    @InvokeAndWait
    public PreferencesWindow() {
	frame = new JFrame();
    }

    @PostConstruct
    @InvokeAndWait
    public void init() {
	frame.setTitle("Preferences");
	makeLayout(frame.getContentPane());
	frame.setResizable(false);
	frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void makeLayout(Container container) {
	container.setLayout(new MigLayout(//
		"fill", //
		"[trailing][leading,grow,fill]", //
		""));

	container.add(new JLabel("Host"));
	container.add(new JTextField(serverConfiguration.getHostDocument(), null, 20), "wrap");

	container.add(new JLabel("Port"));
	container.add(new JTextField(serverConfiguration.getPortDocument(), null, 20), "wrap");

	container.add(new JLabel("User"));
	container.add(new JTextField(serverConfiguration.getUserDocument(), null, 20), "wrap");
    }

    @Autowired
    public void register(WindowJanitor janitor) {
	janitor.register(frame);
    }

    public void show() {
	frame.pack();
	frame.setVisible(true);
    }
}
