package etlmail.context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.core.io.Resource;

import com.google.common.io.Closeables;

public class PropertyBuilder {
	private final Properties properties = new Properties();

	public class PendingKey {
		private final String key;

		public PendingKey(String key) {
			this.key = key;
		}

		public PropertyBuilder yields(String value) {
			properties.setProperty(key, value);
			return PropertyBuilder.this;
		}
	}

	public PendingKey key(String key) {
		return new PendingKey(key);
	}

	public Properties asProperties() {
		return properties;
	}

	public static Properties readProperties(Resource propertyResource)
			throws IOException {
		final Properties result = new Properties();
		InputStream propertyInputStream = null;
		try {
			propertyInputStream = propertyResource.getInputStream();
			result.load(propertyInputStream);
			propertyInputStream.close();
		} finally {
			Closeables.closeQuietly(propertyInputStream);
		}
		return result;
	}
}
