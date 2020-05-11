/*
 * Creation : 19 mars 2014
 * @author Gloaguen Joel
 */
package javax.flat.bind.annotation.adapter;

import javax.flat.bind.JFFPBException;

/**
 * @author Gloaguen Joel <br />
 *         class qui propose la possibilit� de convertir les valeurs dans le format demand� <br />
 * @see #unparsing
 * @see #parsing
 * @param <String> Type en entrer toujours String 
 * @param <ReponceType> en fonction du traitement
 */
@SuppressWarnings("hiding")
public abstract class PositionalAdapter<String, BoundType> {

	/**
     * pour convertir au format demandé à la creation de l object <br />
	 * exemple : <br/>
	 * String en Integer : 00001 en 1
	 * 
	 * @param v
	 * @return
	 * @throws Exception
	 */
	public abstract BoundType unmarshal(String v) throws JFFPBException;

	/**
     * pour convertir au format demander pour la creation du fichier <br />
	 * exemple : <br/>
	 *  <br /> 
	 *  Integer en String : 1 en 00001 
     * 
	 * @param v
	 * @return
	 * @throws Exception
	 */
	public abstract String marshal(BoundType v) throws JFFPBException;

}
