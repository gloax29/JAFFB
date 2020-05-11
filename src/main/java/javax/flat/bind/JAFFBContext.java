/**
 * 
 */
package javax.flat.bind;




/**
 * @author Gloax29
 *
 */
public abstract class JAFFBContext {

	protected JAFFBContext() {

	}

	public static JAFFBContext newInstance(Class<?> clazz) throws JFFPBException {

		return newInstanceFabrique( clazz);

	}
	
	public static JAFFBContext newInstance() throws JFFPBException {

		return newInstanceFabrique(null);

	}

	private static JAFFBContext newInstanceFabrique(Class<?> clazz)
			throws JFFPBException {
		
		return ContextFinder.find(clazz);
	}

	/**
	 * deserialize chaine of caractere to Object
	 * @return
	 * @throws JFFPBException
	 */
	public abstract Unmarshaller createUnmarshaller() throws JFFPBException;

	/**
	 * Serialize Object to chaine of caractere 
	 * @return
	 * @throws JFFPBException
	 */
	public abstract Marshaller createMarshaller() throws JFFPBException;

}
