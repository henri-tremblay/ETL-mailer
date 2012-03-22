package etlmail.front.gui.application;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class ShutdownEvent extends ApplicationEvent {

	public ShutdownEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

}
