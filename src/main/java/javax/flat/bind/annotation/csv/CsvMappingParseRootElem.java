/*
 * Creation : 1 avr. 2014
 */

package javax.flat.bind.annotation.csv;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Gloaguen Joel
 * 
 * <p>
 * Annotation qui sert � la description de la classe principale pour le mapping.
 * elle comporte les valeurs :<br/>
 *
 * </p>
 * @see #valuDebutLigne
 * @see #valuNumbLigne
 * @see #valuLongueurChaine
 * @see #name
 * @see #list
 * @see #charcatereRepli
 * @see #expression
 * @see #theclass
 * @see #NOEXPRES
 * 
*/
@Retention(RetentionPolicy.RUNTIME)
@Target({ FIELD, METHOD })
public @interface CsvMappingParseRootElem {

	/**
	 * Constante pour l utilisation de la <br />
	 * reconnaisance des chaines de caract�res par expression r�guli�re.
	 * <br />
	 * elle signifie que c'est cette variable qui sera utilis�e  pour l'injection br /> 
	 * par defaut. si la cha�ne ne matche avec aucune expression
	 */
	public static final String NOEXPRES = "noExpres"; 
	/**
	 * d�termine � partir de quelle ligne commence cet �l�ment.<br />
	 *  les �l�ments sont class�s selon cet ordre.
	 * 
	 * @return
	 */
	int valuDebutLigne();

	/**
	 * indique le nombre de lignes concern�es par cet �l�ment,<br />
	 *  si non indiqu�.
	 * il sera le dernier ou le seul consern�.
	 * 
	 * <br />cela peut concerner les fichiers avec un nombre de lignes<br/>
	 * fixe.
	 * 
	 * @return
	 */
	
	int valuNumbLigne() default -1;

	
	/**
	 * nom de l'attribu <br />
	 *  
	 * @return
	 */
	
	String name();

	/**
	 * indique si l'attribu est un List.<br/>
	 * valeur par defaut est false<br />
	 * si la valeur est � true, indique le nom de la classe <br />
	 * au param�tre {@link #theclass()}.
	 * <p> 
	 * Pour les Listes le Getteur doit tester la nullit� <br />
	 * et dans ce cas l'instancier.
	 * </p>
	 * 
	 * @return
	 */
	boolean list() default true;

	/**
	 * indique le caractere de remplisage.<br />
	 * s"il n'est pas indiqu�, la valeur par default est l'espace<br />
	 * et la vaieur est prise en compte si la longueur de chaine <bt />
	 * {@link #valuLongueurChaine()}est valoris�e
	 * 
	 * 
	 * @return
	 */
	char charRemplissage() default '\u0000';

	/**
	 * indique le caract�re de remplissage.<br />
	 * s'il n'est pas indiqu�, la valeur par default est l'espace<br />
	 * et la vaieur est prise en compte si la longueur de chaine <bt />
	 * {@link #valuLongueurChaine()}est valoris�e
	 * 
	 * 
	 * @return
	 */
	char charSeparateur() default '\u003B';

	
	
	/**
	 * 
	 * expression d'appartenance.<br/>
	 *  
	 * 
	 * <p>si cette valeur est utilis�e. <br>cela indique au parseur une m�thode de
	 * fonctionnement diff�rente il n'y a plus d'ordre dans la classe principale
	 * #PositionalMappingParseRootElem</p>
	 * 
	 * <p>si la ligne matche avec l'expression, celle si est inject�e<br />
	 *  dans la variable d'appartenance.<br />
	 * . si c'est une liste l'objet est ajout�.
	 * </p>
	 * 
	 * <p>pour qu'une variable recup�re toutes les lignes<br />
	 *  qui ne matche avec aucune expression la valeur {@link #NOEXPRES}<br />
	 *  est � utiliser, ou celle-ci ne sera pas trait�e.
	 * 
	 * </p>
	 * <br/> Attantion valeur par defaut est -X
	 * 
	 * @return
	 */
	String expression() default "-X";

	/**
	 * nom de la classe de l'�l�ment. si c'est une liste, il faut l'indiquer par
	 * defaut DEFAULT.class si list a false
	 * 
	 * @return
	 */
	Class<?> theclass() default DEFAULT.class;

	static final class DEFAULT {
	}

}
