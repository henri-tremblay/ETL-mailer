package etlmail.front.gui.helper;

import javax.swing.SwingUtilities;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class SwingPolicies {
    private final Logger log = LoggerFactory.getLogger(SwingPolicies.class);

    @Around("execution(@InvokeAndWait * *(..))")
    public Object callOnEdt(final ProceedingJoinPoint thisJoinPoint) throws Throwable {
	if (SwingUtilities.isEventDispatchThread()) {
	    return thisJoinPoint.proceed();
	} else {
	    try {
		final Object[] result = new Object[1];
		log.info("Waiting for EDT");
		SwingUtilities.invokeAndWait(new Runnable() {
		    @Override
		    public void run() {
			try {
			    result[0] = thisJoinPoint.proceed();
			} catch (final Throwable e) {
			    throw new ExceptionWrapper(e);
			}
		    }
		});
		return result[0];
	    } catch (final ExceptionWrapper ew) {
		throw ew.getCause();
	    }
	}
    }
}

@SuppressWarnings("serial")
class ExceptionWrapper extends RuntimeException {
    public ExceptionWrapper(Throwable cause) {
	super(cause);
    }
}
