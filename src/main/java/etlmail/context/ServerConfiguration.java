package etlmail.context;

public interface ServerConfiguration {
	public abstract String getHost();

	public abstract int getPort();

	public abstract String getUsername();

	public abstract String getPassword();
}