/**
 * 
 */
package com.ffpm.sample.fileflat.file.readcomplex;

import java.lang.reflect.Field;

import javax.flat.bind.annotation.positinal.PositionalMappingParse;

/**
 * @author joel
 *
 */
public class NonTraiter {

	@PositionalMappingParse(offset = 1, length = -1)
	private String ligneNonTraiter ;

	/**
	 * @return the ligneNonTraiter
	 */
	public String getLigneNonTraiter() {
		return ligneNonTraiter;
	}

	/**
	 * @param ligneNonTraiter the ligneNonTraiter to set
	 */
	public void setLigneNonTraiter(String ligneNonTraiter) {
		this.ligneNonTraiter = ligneNonTraiter;
	}
	@Override
	public String toString() {

		Field[] nomChamps = this.getClass().getDeclaredFields();
		StringBuffer formatString = new StringBuffer(   this.getClass().getSimpleName() + "(");
		for (Field field : nomChamps) {
			if (!"serialVersionUID".equals(field.getName())) {
				try {
					Object Valu = this.getClass().getMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1))
							.invoke(this, new Object[] {});
					formatString.append( ";[ " + field.getName() + "='" + Valu.toString() + "' ]");
				} catch (Exception e) {
					formatString.append(";[ " + field.getName() + "='null' ]");
				}

			}
		}
		return formatString.append(")\n").toString().replaceFirst(";", "");

	}
}
