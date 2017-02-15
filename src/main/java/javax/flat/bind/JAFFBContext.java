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

	public abstract Unmarshaller createUnmarshaller() throws JFFPBException;

	public abstract Marshaller createMarshaller() throws JFFPBException;

}
