/**
 * 
 */
package com.ffpm.sample.fileflat.file.readcomplex;

import java.lang.reflect.Field;
import java.util.Date;

import javax.flat.bind.annotation.positinal.PositionalJavaTypeAdapter;
import javax.flat.bind.annotation.positinal.PositionalMappingParse;

import com.ffpm.sample.fileflat.file.readsimple.DateConvertddMMyyyy;

/**
 * @author Gloax29
 *
 */
public class EnteteComplex {
	// offset =  le depart length = la longeur de la chaine
	// longeur total de l	 chaine est 56  
	@PositionalMappingParse(offset = 1, length = 20)
	private String nomFichier ;
	@PositionalMappingParse(offset = 21, length = 12)
	private String description ;
	//il faut convertir en Date au bon format
	@PositionalMappingParse(offset = 33, length = 8)
	@PositionalJavaTypeAdapter(value=DateConvertddMMyyyy.class) 
	private Date debutInfo ;
	@PositionalMappingParse(offset = 41, length = 8)
	@PositionalJavaTypeAdapter(value=DateConvertddMMyyyy.class)
	private Date finInfo ;
	@PositionalMappingParse(offset = 49, length = 2)
	private String filer ;
	@PositionalMappingParse(offset = 51, length = 8)
	@PositionalJavaTypeAdapter(value=DateConvertddMMyyyy.class)
	private Date dateCreation ;
	/**
	 * @return the nomFichier
	 */
	public String getNomFichier() {
		return nomFichier;
	}
	/**
	 * @param nomFichier the nomFichier to set
	 */
	public void setNomFichier(String nomFichier) {
		this.nomFichier = nomFichier;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the debutInfo
	 */
	public Date getDebutInfo() {
		return debutInfo;
	}
	/**
	 * @param debutInfo the debutInfo to set
	 */
	public void setDebutInfo(Date debutInfo) {
		this.debutInfo = debutInfo;
	}
	/**
	 * @return the finInfo
	 */
	public Date getFinInfo() {
		return finInfo;
	}
	/**
	 * @param finInfo the finInfo to set
	 */
	public void setFinInfo(Date finInfo) {
		this.finInfo = finInfo;
	}
	/**
	 * @return the filer
	 */
	public String getFiler() {
		return filer;
	}
	/**
	 * @param description the description to set
	 */
	public void setFiler(String filer) {
		this.filer = filer;
	}
	/**
	 * @return the dateCreation
	 */
	public Date getDateCreation() {
		return dateCreation;
	}
	/**
	 * @param dateCreation the dateCreation to set
	 */
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
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

