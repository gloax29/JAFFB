/**
 * 
 */
package com.ffpm.sample.fileflat.file.readsimple;

import java.lang.reflect.Field;

import javax.flat.bind.annotation.positinal.PositionalControlRegex;
import javax.flat.bind.annotation.positinal.PositionalJavaTypeAdapter;
import javax.flat.bind.annotation.positinal.PositionalMappingParse;

/**
 * @author Gloax29
 *
 */
public class LigneSimple {
	//total 185 
	//format dix 0
	@PositionalMappingParse(offset = 1, length = 10)
	@PositionalJavaTypeAdapter(value=NumberConvertDix0.class)
	private Integer idLigne ;
	@PositionalMappingParse(offset = 11, length = 10,padding=PositionalMappingParse.TOP,stripChaine=true)
	private String colume ;
	@PositionalMappingParse(offset = 21, length = 8,required=true)
	@PositionalControlRegex(expression="([0-9]{2})([0-9_A-Z]{6})")
	private String refference ;
	//il faut converrtir en tableau
	@PositionalMappingParse(offset = 29, length = 97)
	@PositionalJavaTypeAdapter(value=ConvertStringTableauString.class)
	private String[] valueRef ;
	@PositionalMappingParse(offset = 126, length = 60)
	private String code ;
	/**
	 * @return the idLigne
	 */
	public Integer getIdLigne() {
		return idLigne;
	}
	/**
	 * @param idLigne the idLigne to set
	 */
	public void setIdLigne(Integer idLigne) {
		this.idLigne = idLigne;
	}
	/**
	 * @return the colume
	 */
	public String getColume() {
		return colume;
	}
	/**
	 * @param colume the colume to set
	 */
	public void setColume(String colume) {
		this.colume = colume;
	}
	/**
	 * @return the refference
	 */
	public String getRefference() {
		return refference;
	}
	/**
	 * @param refference the refference to set
	 */
	public void setRefference(String refference) {
		this.refference = refference;
	}
	/**
	 * @return the valueRef
	 */
	public String[] getValueRef() {
		return valueRef;
	}
	/**
	 * @param valueRef the valueRef to set
	 */
	public void setValueRef(String[] valueRef) {
		this.valueRef = valueRef;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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
