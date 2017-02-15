/**
 * 
 */
package com.ffpm.sample.csv.data;

import java.lang.reflect.Field;
import java.util.Date;

import javax.flat.bind.annotation.csv.CsvMappingParse;
import javax.flat.bind.annotation.positinal.PositionalJavaTypeAdapter;

/**
 * @author e446027
 *
 */
public class ObjectForCsvMapping {
	
	@CsvMappingParse(offset = 1)
	private String nom ;
	@CsvMappingParse(offset = 2)
	private String prenom ;
	@CsvMappingParse(offset = 3)
	@PositionalJavaTypeAdapter(value=DateConvertddMMyyyy.class)
	private Date dateNaissanse ;
	@CsvMappingParse(offset = 4)
	private Integer age ; 
	

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}


	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}


	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}


	/**
	 * @param prenom the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	/**
	 * @return the dateNaissanse
	 */
	public Date getDateNaissanse() {
		return dateNaissanse;
	}


	/**
	 * @param dateNaissanse the dateNaissanse to set
	 */
	public void setDateNaissanse(Date dateNaissanse) {
		this.dateNaissanse = dateNaissanse;
	}


	/**
	 * @return the age
	 */
	public Integer getAge() {
		return age;
	}


	/**
	 * @param age the age to set
	 */
	public void setAge(Integer age) {
		this.age = age;
	}


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
	
	
}
