package etlmail.front.gui.application;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ METHOD, CONSTRUCTOR })
@Retention(CLASS)
public @interface InvokeAndWait {

}
