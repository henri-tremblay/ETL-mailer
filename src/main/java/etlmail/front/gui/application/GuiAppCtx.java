package etlmail.front.gui.application;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import javax.swing.SwingWorker;

import org.springframework.context.annotation.*;

import etlmail.front.gui.*;
import etlmail.front.gui.helper.InvokeAndWait;
import etlmail.front.gui.preferences.PreferencesWindow;
import etlmail.front.gui.preferences.SwingServerConfiguration;
import etlmail.front.gui.sendmail.ProgressDialog;
import etlmail.front.gui.sendmail.SendMailAction;

@Configuration
@ComponentScan(basePackageClasses = { //
etlmail.front.gui.ComponentScanMarker.class, //
	etlmail.context.ComponentScanMarker.class //
})
public class GuiAppCtx implements PreferencesWindowProvider {
    @Bean
    public SendMailAction sendMailAction() {
	return new SendMailAction() {
	    @Override
	    protected ProgressDialog makeProgressDialog(SwingWorker<?, ?> sendMailWorker) {
		return new ProgressDialog(mainFrame(), sendMailWorker);
	    }
	};
    }

    @Bean
    @InvokeAndWait
    public SwingServerConfiguration serverConfiguration() {
	return new SwingServerConfiguration();
    }

    @Bean
    @InvokeAndWait
    public NewsletterNotificationBuilder notificationBuilder() {
	return new NewsletterNotificationBuilder();
    }

    @Bean
    @InvokeAndWait
    public MainFrame mainFrame() {
	return new MainFrame();
    }

    @Bean
    @InvokeAndWait
    public MailDefinitionPane mailDefinitionPane() {
	return new MailDefinitionPane();
    }

    @Override
    @Bean
    @InvokeAndWait
    @Scope(SCOPE_PROTOTYPE)
    public PreferencesWindow preferencesWindow() {
	return new PreferencesWindow();
    }
}
