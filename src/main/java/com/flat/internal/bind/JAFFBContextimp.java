/**
 * 
 */
package com.flat.internal.bind;

import javax.flat.bind.JAFFBContext;
import javax.flat.bind.JFFPBException;
import javax.flat.bind.Marshaller;
import javax.flat.bind.Unmarshaller;

import com.flat.internal.marshaller.Marshallerimp;
import com.flat.internal.unmarshaller.Unmarshallerimp;


/**
 * @author root
 *
 */
public class JAFFBContextimp extends JAFFBContext {
	
	private Class<?>  clazz ;
	
	public JAFFBContextimp() {
		this.clazz = null ;
	}
	
	public JAFFBContextimp(Class<?> clazz) {
		this.clazz =clazz;
	}

	/* (non-Javadoc)
	 * @see javax.flat.bind.JAFFBContext#createUnmarshaller()
	 */
	@Override
	public Unmarshaller createUnmarshaller() throws JFFPBException {
		
		return this.clazz == null ?new Unmarshallerimp(): new Unmarshallerimp(clazz);
	}

	/* (non-Javadoc)
	 * @see javax.flat.bind.JAFFBContext#createMarshaller()
	 */
	@Override
	public Marshaller createMarshaller() throws JFFPBException {
		// TODO Auto-generated method stub
		return new Marshallerimp();
	}

}
