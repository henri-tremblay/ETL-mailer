package etlmail.front.gui.about;

import java.awt.Container;

import javax.annotation.PostConstruct;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import etlmail.front.gui.application.InvokeAndWait;
import etlmail.front.gui.helper.FrameHolder;

@Component
public class AboutWindow extends FrameHolder {
	@Autowired
	private ImageProvider imageProvider;

	@PostConstruct
	@InvokeAndWait
	public void init() {
		makeLayout(frame.getContentPane());
	}

	private void makeLayout(Container container) {
		container.setLayout(new MigLayout("fill", "10[]20", "push[]push"));

		container.add(imageProvider.getMadame(), "dock west");
		container.add(new JLabel(
				"<html>ETL mail v0.20<br>by FRO, NDN & FME</html>"), "");
	}
}
