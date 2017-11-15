/*
 * Creation : 17 mars 2014
 */
package javax.flat.bind.annotation.csv;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * annotation de description des variables <br />
 * qui sont dans le fichier <br />
 * 
 * @see #offset()
 * @see #length()
 * @see #required()
 * @see #padding()
 * @author Gloaguen Joel
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ METHOD, FIELD })
public @interface CsvMappingParse {

    /**
     * indique la position dans la suite de valeur<br />
     * <br />
     * <br />
     * de la chaine de caract�res lus
     * 
     * @return
     */
    int offset();

    /**
     * indique si l expression @PositionalControlRegex et obligatoire <br />
     * La valeur par d�faut est false
     * 
     * @return
     */
    boolean required() default false;

    /**
     * padding left 0 is default padding rigth 1 padding top -1 center <br />
     * non implementer
     * 
     * @return
     */
    int padding() default 0;

    /**
     * supprime les espace devant et deriere a la lecture
     * 
     * @return
     */
    boolean stripChaine() default false;

}
