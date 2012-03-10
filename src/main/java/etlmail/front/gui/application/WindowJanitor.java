package etlmail.front.gui.application;

import static java.util.Collections.synchronizedMap;

import java.awt.Window;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.lang.Validate;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class WindowJanitor implements ApplicationListener<ShutdownEvent> {
	private final Map<Window, WindowJanitor> windows = synchronizedMap(new WeakHashMap<Window, WindowJanitor>());

	@Override
	public void onApplicationEvent(ShutdownEvent event) {
		synchronized (windows) {
			for (final Window window : windows.keySet()) {
				window.dispose();
			}
		}
	}

	public void register(Window window) {
		Validate.notNull(window);
		windows.put(window, this);
	}
}
