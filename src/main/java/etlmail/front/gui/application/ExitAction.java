package etlmail.front.gui.application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ExitAction implements ActionListener {
    private @Autowired ApplicationEventPublisher eventPublisher;

    @Override
    public void actionPerformed(ActionEvent e) {
	eventPublisher.publishEvent(new ShutdownEvent(e));
    }
}