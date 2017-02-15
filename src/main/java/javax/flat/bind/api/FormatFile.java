/*
 * Creation : 17 mars 2014
 */
package javax.flat.bind.api;

/**
 * 
 * 
 * @author Gloaguen Joel
 * 
 */
public class FormatFile {

	/**
	 * Getter name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter name
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter header
	 * 
	 * @return the header
	 */
	public boolean isHeader() {
		return header;
	}

	/**
	 * Setter header
	 * 
	 * @param header
	 *            the header to set
	 */
	public void setHeader(boolean header) {
		this.header = header;
	}

	private String name;
	private boolean header = false;
}
