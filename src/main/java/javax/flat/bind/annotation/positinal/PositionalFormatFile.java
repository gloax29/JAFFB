/*
 * Creation : 17 mars 2014
 */
package javax.flat.bind.annotation.positinal;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * indique le nom de la class parent <br />
 * 
 * @see #name()
 * @author Gloaguen Joel
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ TYPE, FIELD, METHOD })
public @interface PositionalFormatFile {

    /**
     * nom de la classe <br />
     * 
     * @return
     */
    String name();

    String type() default "txt";

}
