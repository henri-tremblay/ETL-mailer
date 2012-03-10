package etlmail.front.gui.application;

import java.util.concurrent.atomic.AtomicReference;

import javax.swing.SwingUtilities;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import etlmail.front.gui.helper.ExceptionWrapper;

@Aspect
public class SwingPolicies {
	private final Logger log = LoggerFactory.getLogger(SwingPolicies.class);

	@Around("execution(@InvokeAndWait * *(..)) || execution(@InvokeAndWait new(..))")
	public Object callOnEdt(final ProceedingJoinPoint thisJoinPoint)
			throws Throwable {
		if (SwingUtilities.isEventDispatchThread()) {
			return thisJoinPoint.proceed();
		} else {
			try {
				final AtomicReference<Object> result = new AtomicReference<Object>();
				log.debug("Waiting for EDT");
				SwingUtilities.invokeAndWait(new Runnable() {
					@Override
					public void run() {
						try {
							result.set(thisJoinPoint.proceed());
						} catch (final Throwable e) {
							throw new ExceptionWrapper(e);
						}
					}
				});
				return result.get();
			} catch (final ExceptionWrapper ew) {
				throw ew.getCause();
			}
		}
	}
}
