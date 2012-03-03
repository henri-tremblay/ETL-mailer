package etlmail.front.gui.application;

import java.awt.Window;
import java.util.Map;
import java.util.WeakHashMap;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class WindowJanitor implements ApplicationListener<ShutdownEvent> {
    private static final Map<Window, Window> windows = new WeakHashMap<Window, Window>();

    @Override
    public void onApplicationEvent(ShutdownEvent event) {
	for (final Window window : windows.keySet()) {
	    window.dispose();
	}
    }

    public void register(Window window) {
	windows.put(window, window);
    }
}
