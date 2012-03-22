package etlmail.front.gui.about;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.*;

@Component
public class ImageProvider {
	private JLabel madame;

	public JLabel getMadame() {
		if (madame == null) {
			madame = new JLabel();
			new FetchImageWorker(madame,
					"http://feeds.feedburner.com/BonjourMadame?format=xml")
					.execute();
		}
		return madame;
	}
}

class FetchImageWorker extends SwingWorker<BufferedImage, Void> {
	private static final Logger log = LoggerFactory
			.getLogger(FetchImageWorker.class);

	private final HttpClient httpClient = new DefaultHttpClient();
	private final JLabel label;
	private final String feedUrl;

	public FetchImageWorker(JLabel label, String feedUrl) {
		this.label = label;
		this.feedUrl = feedUrl;
	}

	@Override
	protected BufferedImage doInBackground() throws Exception {
		final List<SyndEntry> entries = getFeedEntries(feedUrl);
		final String url = getUrlDerniereImage(entries);
		return getImage(url);
	}

	@Override
	protected void done() {
		try {
			label.setIcon(new ImageIcon(get()));
		} catch (final InterruptedException e) {
			Thread.interrupted();
		} catch (final ExecutionException e) {
			log.error("Could not set image", e);
		}
	}

	private List<SyndEntry> getFeedEntries(String url) throws IOException,
			ClientProtocolException, FeedException {
		final SyndFeedInput input = new SyndFeedInput();
		final InputStream bonjourStream = get(url);
		final SyndFeed feed = input.build(new XmlReader(bonjourStream));
		bonjourStream.close();
		@SuppressWarnings("unchecked")
		final List<SyndEntry> entries = feed.getEntries();
		return entries;
	}

	private String getUrlDerniereImage(List<SyndEntry> entries) {
		final String derniereEntree = entries.get(0).getDescription()
				.getValue();
		final Document doc = Jsoup.parse(derniereEntree);
		final Elements image = doc.getElementsByTag("img");
		return image.attr("src");
	}

	private BufferedImage getImage(String url) throws IOException,
			ClientProtocolException {
		final InputStream imageStream = get(url);
		final BufferedImage image = ImageIO.read(imageStream);
		imageStream.close();
		return image;
	}

	private InputStream get(String url) throws IOException,
			ClientProtocolException {
		final HttpResponse response = httpClient.execute(new HttpGet(url));
		final HttpEntity entity = response.getEntity();
		return entity.getContent();
	}
}
