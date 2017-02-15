/*
 * Creation : 7 janv. 2015
 */
/**
 * 
 */
package com.ffpm.sample.csv.data.list;

import java.lang.reflect.Field;
import java.util.Date;

import javax.flat.bind.annotation.csv.CsvMappingParse;

/**
 * TODO : Description
 * 
 * @author E446027
 *
 */
public class SemaineCsvAnalyse {
    
    private String  numeroSemaine ;
    @CsvMappingParse(offset = 1)
    private Integer quantiteForSemaine  ;
    
    private Date dateDeDebut ;
    
    
    
    
    /**
     * 
     */
    public SemaineCsvAnalyse() {

        quantiteForSemaine = new Integer(0);
    }
    public SemaineCsvAnalyse(String string) {
        quantiteForSemaine = new Integer(0);
        numeroSemaine = string ;
    }
    /**
     * Getter numeroSemaine
     * 
     * @return the numeroSemaine
     */
    public String getNumeroSemaine() {
        return numeroSemaine;
    }
    /**
     * Setter numeroSemaine
     * 
     * @param numeroSemaine the numeroSemaine to set
     */
    public void setNumeroSemaine(String numeroSemaine) {
        this.numeroSemaine = numeroSemaine;
    }
    /**
     * Getter quantiteForSemaine
     * 
     * @return the quantiteForSemaine
     */
    public Integer getQuantiteForSemaine() {
        return quantiteForSemaine;
    }
    /**
     * Setter quantiteForSemaine
     * 
     * @param quantiteForSemaine the quantiteForSemaine to set
     */
    public void setQuantiteForSemaine(Integer quantiteForSemaine) {
        this.quantiteForSemaine = quantiteForSemaine;
    }
    /**
     * Getter dateDeDebut
     * 
     * @return the dateDeDebut
     */
    public Date getDateDeDebut() {
        return dateDeDebut;
    }
    /**
     * Setter dateDeDebut
     * 
     * @param dateDeDebut the dateDeDebut to set
     */
    public void setDateDeDebut(Date dateDeDebut) {
        this.dateDeDebut = dateDeDebut;
    }
    
    
    
    
    
    
    
    
    
    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((numeroSemaine == null) ? 0 : numeroSemaine.hashCode());
        return result;
    }
    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SemaineCsvAnalyse other = (SemaineCsvAnalyse) obj;
        if (numeroSemaine == null) {
            if (other.numeroSemaine != null)
                return false;
        } else if (!numeroSemaine.equals(other.numeroSemaine))
            return false;
        return true;
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
