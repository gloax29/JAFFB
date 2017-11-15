/*
 * Creation : 17 mars 2014
 */
package javax.flat.bind.annotation.positinal;

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
 * @see #charcatereRepliForPadding()
 * @see #stripChaine()
 * @author Gloaguen Joel
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ METHOD, FIELD })
public @interface PositionalMappingParse {

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int TOP = -1;

    /**
     * position de depart de l'offset<br />
     * <br />
     * determine le position de depart de l'ecture <br />
     * de la chaine de carractere lu
     * 
     * @return
     */
    int offset();

    /**
     * longueur de la chaine de carractere prelever<br />
     * sur la ligne lu du fichier<br />
     * <br />
     * coupler avec offset cela determine la partie de chaine<br />
     * de caractere qui est a traiter <br />
     * <br />
     * si -1 la ligne a partir de l'offset lu du fichier est treter dans sa globalité
     * 
     * @return
     */
    @SuppressWarnings("javadoc")
    int length();

    /**
     * indique si l expression @PositionalControlRegex et obligatoire <br />
     * La valeur par defaut est false
     * 
     * @return
     */
    boolean required() default false;

    /**
     * padding left 0 is default padding rigth 1 padding top -1 center <br />
     * 
     * @return
     */
    int padding() default 0;

    /**
     * indique le caractere de remplisage.<br />
     * si il n'est pas indiqu�, la valeur par default est l'espace<br />
     * et la vaieur est pris en compte si la longueur de chaine <bt /> {@link #valuLongueurChaine()}est valoriser
     * 
     * @return
     */
    char charcatereRepliForPadding() default '\u0000';

    /**
     * supprime les espace devant et deriere a la lecture
     * 
     * @return
     */
    boolean stripChaine() default false;

    /**
     * valeur default si object null
     * 
     * @return
     */
    String DefaultValue() default "";

    /**
     * permet a l'ecriture de la chaîne de caracter de decaler <br />
     * les offsets pour pouvoir genere une chaine de taille variable <br />
     * n'est pris en compte que pour l'ecriture
     * 
     * @return
     */
    boolean variant() default false;

    /**
     * desactive la valeur
     * 
     * @return
     */
    boolean desactivate() default false;

}
