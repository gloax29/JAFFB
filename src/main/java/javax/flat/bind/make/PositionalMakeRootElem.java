/*
 * Creation : 2 avr. 2014
 */
/**
 * 
 */
package javax.flat.bind.make;

import java.lang.reflect.Field;

import javax.flat.bind.api.FormatRootElem;

/**
 * 
 * 
 * @author Gloaguen Joel
 * 
 */
public class PositionalMakeRootElem {

	private static String SETTEUR = "set";
	private static String GETTEUR = "get";
	private Field field;
	private FormatRootElem formatRootElem;

	/**
	 * @param positionalMappingParseRootElem
	 * @param field
	 * @param formatRootElem
	 */
	public PositionalMakeRootElem(Field field, FormatRootElem formatRootElem) {
		super();
		this.field = field;
		this.formatRootElem = formatRootElem;
	}

	public PositionalMakeRootElem() {

	}

	/**
	 * Getter field
	 * 
	 * @return the field
	 */
	public Field getField() {
		return field;
	}

	/**
	 * Setter field
	 * 
	 * @param field
	 *            the field to set
	 */
	public void setField(Field field) {
		this.field = field;
	}

	/**
	 * Getter formatRootElem
	 * 
	 * @return the formatRootElem
	 */
	public FormatRootElem getFormatRootElem() {
		return formatRootElem;
	}

	/**
	 * Setter formatRootElem
	 * 
	 * @param formatRootElem
	 *            the formatRootElem to set
	 */
	public void setFormatRootElem(FormatRootElem formatRootElem) {
		this.formatRootElem = formatRootElem;
	}

	public String getSetteurMethode() {
		return SETTEUR + field.getName().replaceFirst(field.getName().substring(0, 1), field.getName().substring(0, 1).toUpperCase());
	}

	public String getGetteurMethode() {
		return GETTEUR + field.getName().replaceFirst(field.getName().substring(0, 1), field.getName().substring(0, 1).toUpperCase());
	}

}
