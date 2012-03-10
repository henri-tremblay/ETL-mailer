package etlmail.front.gui.helper;

@SuppressWarnings("serial")
public class ExceptionWrapper extends RuntimeException {
	public ExceptionWrapper(Throwable cause) {
		super(cause);
	}
}