/**
 * 
 */
package javax.flat.bind;

import java.io.PrintWriter;
import java.lang.reflect.Field;

/**
 * @author Gloax29
 *
 */
public class JFFPBException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	 /**
     * Vendor specific error code
     *
     */
	private final String errorCode;

    /**
     * Exception reference
     *
     */
    private  Throwable linkedException;


    /**
     * Construct a JFFPBException with the specified detail message.  The
     * errorCode and linkedException will default to null.
     *
     * @param message a description of the exception
     */
    public JFFPBException(String message) {
        this( message, null, null );
    }

    /**
     * Construct a JFFPBException with the specified detail message and vendor
     * specific errorCode.  The linkedException will default to null.
     *
     * @param message a description of the exception
     * @param errorCode a string specifying the vendor specific error code
     */
    public JFFPBException(String message, String errorCode) {
        this( message, errorCode, null );
    }

    /**
     * Construct a JFFPBException with a linkedException.  The detail message and
     * vendor specific errorCode will default to null.
     *
     * @param exception the linked exception
     */
    public JFFPBException(Throwable exception) {
        this( null, null, exception );
    }

    /**
     * Construct a JFFPBException with the specified detail message and
     * linkedException.  The errorCode will default to null.
     *
     * @param message a description of the exception
     * @param exception the linked exception
     */
    public JFFPBException(String message, Throwable exception) {
        this( message, null, exception );
    }

    /**
     * Construct a JFFPBException with the specified detail message, vendor
     * specific errorCode, and linkedException.
     *
     * @param message a description of the exception
     * @param errorCode a string specifying the vendor specific error code
     * @param exception the linked exception
     */
    public JFFPBException(String message, String errorCode, Throwable exception) {
        super( message );
        this.errorCode = errorCode;
        this.linkedException = exception;
    }

    /**
     * Get the vendor specific error code
     *
     * @return a string specifying the vendor specific error code
     */
    public String getErrorCode() {
        return this.errorCode;
    }

    /**
     * Get the linked exception
     *
     * @return the linked Exception, null if none exists
     */
    public Throwable getLinkedException() {
        return linkedException;
    }

    /**
     * Add a linked Exception.
     *
     * @param exception the linked Exception (A null value is permitted and
     *                  indicates that the linked exception does not exist or
     *                  is unknown).
     */
    public synchronized void setLinkedException( Throwable exception ) {
        this.linkedException = exception;
    }

    /**
     * Returns a short description of this JFFPBException.
     *
     */
    @Override
    public String toString() {

           Field[] nomChamps = this.getClass().getDeclaredFields();
            String formatString = this.getClass().getSimpleName() + "(";

            for (Field field : nomChamps) {
                if (!"serialVersionUID".equals(field.getName())) {
                    try {
                        Object Valu = this.getClass().getMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1))
                                .invoke(this, new Object[] {});
                        formatString = formatString + ";[ " + field.getName() + "='" + Valu.toString() + "' ]";
                    } catch (Exception e) {
                        formatString = formatString + ";[ " + field.getName() + "='null' ]";
                    }

                }
            }
            return (formatString + ")").replaceFirst(";", "");
        }
    

    /**
     * Prints this JFFPBException and its stack trace (including the stack trace
     * of the linkedException if it is non-null) to the PrintStream.
     *
     * @param s PrintStream to use for output
     */
    @Override
    public void printStackTrace( java.io.PrintStream s ) {
        super.printStackTrace(s);
    }

    /**
     * Prints this JFFPBException and its stack trace (including the stack trace
     * of the linkedException if it is non-null) to <tt>System.err</tt>.
     *
     */
    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }

    /**
     * Prints this JFFPBException and its stack trace (including the stack trace
     * of the linkedException if it is non-null) to the PrintWriter.
     *
     * @param s PrintWriter to use for output
     */
    @Override
    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
    }

    @Override
    public Throwable getCause() {
        return linkedException;
    }
	
}
