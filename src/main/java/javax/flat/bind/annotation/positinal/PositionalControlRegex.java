/*
 */
package javax.flat.bind.annotation.positinal;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation de controle par expretion reguliere
 * 
 * @see #expression()
 * 
 * @author Gloaguen Joel
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ METHOD, FIELD })
public @interface PositionalControlRegex {

	/**
	 * expretion reguliere qui sera test�. en relation avec
	 * {@link #required()} si la valeur est requise et arrete le traitement<br />
	 * en Exception <br />
	 * si non un simple message est indique<br />
	 *  que la valeur ne corespond pas a l'expretion
	 * 
	 * @return
	 */
	String expression();

}
