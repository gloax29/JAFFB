/*
 * Creation : 19 mars 2014
 */

package javax.flat.bind.annotation.positinal;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.flat.bind.annotation.adapter.PositionalAdapter;

/**
 * annotation servant a donn� le nom de la class qui Extende PositionalAdapter<String, ReponceType> <br />
 * Si le format demander n'est pas un format primitif,<br />
 * cette annotation permet de convertir la chaine de carractere <br />
 * en Object complex et L'Object en chaine de carracere
 * 
 * @see #value()
 * @author Gloaguen Joel
 */
@Retention(RUNTIME)
@Target({ PACKAGE, FIELD, METHOD, TYPE, PARAMETER })
public @interface PositionalJavaTypeAdapter {

    /**
     * indique la class qui extends PositionalAdapter<String, ReponceType><br />
     * value = value.class
     * 
     * @return
     */
    @SuppressWarnings("rawtypes")
    Class<? extends PositionalAdapter> value();

    /**
     * desactive le parcing a la lecture de la chaine car ce la vas avec variant<br />
     * quand on ne connait pas la longueur exacte. attention<br />
     * toute la chaine restente est injecter dans cette varriable qui doit etre declarer Object .<br />
     * serre pour les fin de ligne pouvent etre variant
     * 
     * @return
     */
    boolean parcing() default true;

}
