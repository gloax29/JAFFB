/**
 * 
 */
package javax.flat.bind;

import com.flat.internal.bind.JAFFBContextimp;

/**
 * @author root
 */
public class ContextFinder {

    protected ContextFinder() {

    }

    /**
     * @throws JFFPBException
     */
    static JAFFBContext find(Class<?> clazz) throws JFFPBException {

        return clazz == null ? new JAFFBContextimp() : new JAFFBContextimp(clazz);

    }
}
