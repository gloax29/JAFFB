/*
 * Creation : 1 avr. 2014
 */

package javax.flat.bind.annotation.positinal;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Gloaguen Joel
 * 
 * <p>
 * Annotation qui sert a la description de la class principal pour le mapping.
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
public @interface PositionalMappingParseRootElem {

	/**
	 * Constante pour l utilisation de la <br />
	 * reconnaisance des chaine de carractere par exoression reguliere.
	 * <br />
	 * elle signifi que c'est cette variable qui sera utiliser  pour l'injectionbr /> 
	 * par default. si la chaine ne match avec aucune expression
	 */
	public static final String NOEXPRES = "noExpres"; 
	/**
	 * determine a partir de quelle ligne commence cette element.<br />
	 *  les elements sont classer cellon cette ordre.
	 * 
	 * @return
	 */
	int startRowsIterationLigne() default 1;

	/**
	 * indique le nombre de lignes consern� par cette element,<br />
	 *  si non indiqu�.
	 * il serat le dernier ou le seule consern�.<br />
	 * 
	 * indique l'ordre de traitement des lignes
	 * 
	 * <br />cela peut concerner les fichiers avec un nombre de ligne<br/>
	 * fixe :
	 * <br />
	 * 1--<br />
	 * 1--<br />
	 * 1--<br />
	 * 2--<br />
	 * 2--<br />
	 * 2--<br />
	 * 2--<br />
	 * 3--<br />
	 * 3--<br />
	 * 
	 * @return
	 */
	
	int numbersRowsIterator() default -1;

	/**
	 * indique la longueur de la chaine pour control.<br >
	 * si non indique pas de control<br />
	 * <b>Attention</b> si la longueur n'est pas indique <br />
	 * le parsing restitura une chaine de caractere<br />
	 * qui peut varier en longueur suivant le contenu des variable.<br />
	 * 
	 * le carractere de remplissage ne sera pas utiliser
	 * 
	 * @return
	 */
	int valuLongueurChaine() default -1;

	/**
	 * nom de l'attribus <br />
	 *  
	 * @return
	 */
	
	String name();

	/**
	 * indique si l'attribus est un List.<br/>
	 * valeur par default est false<br />
	 * si la valeur est � true, indique le nom de la class <br />
	 * au parametre {@link #theclass()}.
	 * <p> 
	 * Pour les Listes le Getteur dois testes la nullit� <br />
	 * et dans ce cas l'instancier.
	 * </p>
	 * 
	 * @return
	 */
	boolean list() default false;

	/**
	 * indique le caractere de remplisage.<br />
	 * si il n'est pas indiqu�, la valeur par default est l'espace<br />
	 * et la vaieur est pris en compte si la longueur de chaine <bt />
	 * {@link #valuLongueurChaine()}est valoriser
	 * 
	 * 
	 * @return
	 */
	char charcatereRepli() default '\u0000';

	/**
	 * 
	 * expression d'appartenance.<br/>
	 *  
	 * 
	 * <p>si cette valeur est utiliser. <br>cela indique au parseur une methode de
	 * fonctionnement differente il n'y a plus d'ordre dans la class principal
	 * #PositionalMappingParseRootElem</p>
	 * 
	 * <p>si la ligne match avec l'expression, celle si est inject�<br />
	 *  dens la variable d'apartenence.<br />
	 * . si c'est une list l'objet est ajout�.
	 * </p>
	 * 
	 * <p>pour qu'une variable recupaire toutes les lignes<br />
	 *  qui ne match avec aucune expression la valuer {@link #NOEXPRES}<br />
	 *  est a utiliser, ou celle-ci ne serat pas traite.
	 * 
	 * </p>
	 * <br/> Attantion valeur par default est -X
	 * 
	 * @return
	 */
	String expression() default "-X";

	/**
	 * nom de la classe de l'element. si c'est une liste, il faut l'indiqu� par
	 * defaut DEFAULT.class si list a false
	 * 
	 * @return
	 */
	Class<?> theclass() ;

	

}
