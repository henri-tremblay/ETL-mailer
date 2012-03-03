package etlmail.front.gui.application;

import javax.swing.SwingWorker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;

import etlmail.front.gui.mainframe.MainFrame;
import etlmail.front.gui.sendmail.ProgressDialog;
import etlmail.front.gui.sendmail.SendMailAction;

@Configuration
@ComponentScan(basePackageClasses = { //
etlmail.front.gui.ComponentScanMarker.class, //
	etlmail.context.ComponentScanMarker.class //
})
public class GuiAppCtx {
    @Bean
    @Autowired
    public SendMailAction sendMailAction(final MainFrame mainFrame) {
	return new SendMailAction() {
	    @Override
	    protected ProgressDialog makeProgressDialog(SwingWorker<?, ?> sendMailWorker) {
		return new ProgressDialog(mainFrame, sendMailWorker);
	    }
	};
    }
}
