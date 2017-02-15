/*
 * Creation : 7 janv. 2015
 */
/**
 * 
 */
package com.ffpm.sample.csv.data.list;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.flat.bind.annotation.csv.CsvMappingParse;

/**
  * 
 * @author E446027
 *
 */
public class CorpCsvAnnalyse {
    
    @CsvMappingParse(offset = 1)
    private String famille ;
    @CsvMappingParse(offset = 2)
    private String ut;
    @CsvMappingParse(offset = 4)
    private String ligne;
    @CsvMappingParse(offset = 3)
    private List<PeriodeCsvAnnalyse> periodes ;
    private Integer numberPeriodes;
    private Date dateDeDebut;
    private Date dateDeFin;
    
    
    
    
   

    /**
     * @param famille
     * @param ut
     * @param ligne
     */
    public CorpCsvAnnalyse(String famille, String ut, String ligne,Integer numberPeriodes) {
        super();
        this.famille = famille;
        this.ut = ut;
        this.ligne = ligne;
        this.setNumberPeriodes(numberPeriodes);
    }
    
    public CorpCsvAnnalyse(Integer numberPeriodes) {
        super();
        this.setNumberPeriodes(numberPeriodes);
        
    }
    
    
    /**
     * @param date2 
     * @param date 
     * @param maxPer 
     * @param string3 
     * @param string2 
     * @param string 
     * @param famille
     * @param ut
     * @param ligne
     */
    public CorpCsvAnnalyse(String famille, String ut, String ligne,Integer numberPeriodes,Date dateDeDebut ,Date dateDeFin) {
        super();
        this.famille = famille;
        this.ut = ut;
        this.ligne = ligne;
        this.setNumberPeriodes(numberPeriodes);
        this.dateDeDebut = dateDeDebut ;
        this.dateDeFin = dateDeFin ;
    }
    /**
     * Getter famille
     * 
     * @return the famille
     */
    public String getFamille() {
        return famille;
    }
    /**
     * Setter famille
     * 
     * @param famille the famille to set
     */
    public void setFamille(String famille) {
        this.famille = famille;
    }
    /**
     * Getter ut
     * 
     * @return the ut
     */
    public String getUt() {
        return ut;
    }
    /**
     * Setter ut
     * 
     * @param ut the ut to set
     */
    public void setUt(String ut) {
        this.ut = ut;
    }
    /**
     * Getter ligne
     * 
     * @return the ligne
     */
    public String getLigne() {
        return ligne;
    }
    /**
     * Setter ligne
     * 
     * @param ligne the ligne to set
     */
    public void setLigne(String ligne) {
        this.ligne = ligne;
    }
    /**
     * Getter periodes
     * 
     * @return the periodes
     */
    public List<PeriodeCsvAnnalyse> getPeriodes() {
        if(periodes == null){
            periodes = new ArrayList<PeriodeCsvAnnalyse>(this.numberPeriodes);
         
        }
        return periodes;
    }
   
    /**
     * Getter numberPeriodes
     * 
     * @return the numberPeriodes
     */
    public Integer getNumberPeriodes() {
        return numberPeriodes;
    }

    /**
     * Setter numberPeriodes
     * 
     * @param numberPeriodes the numberPeriodes to set
     */
    public void setNumberPeriodes(Integer numberPeriodes) {
        this.numberPeriodes = numberPeriodes;
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
    
    public static void main(String[] args) {
        StringBuffer buffer = new StringBuffer("123;456;789;;");
        if(buffer.toString().endsWith(";")){
        System.out.println(buffer.delete(buffer.length()-1, buffer.length()));
        }else{
            
            System.out.println(buffer);
        }
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
        result = prime * result + ((famille == null) ? 0 : famille.hashCode());
        result = prime * result + ((ligne == null) ? 0 : ligne.hashCode());
        result = prime * result + ((ut == null) ? 0 : ut.hashCode());
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
        CorpCsvAnnalyse other = (CorpCsvAnnalyse) obj;
        if (famille == null) {
            if (other.famille != null)
                return false;
        } else if (!famille.equals(other.famille))
            return false;
        if (ligne == null) {
            if (other.ligne != null)
                return false;
        } else if (!ligne.equals(other.ligne))
            return false;
        if (ut == null) {
            if (other.ut != null)
                return false;
        } else if (!ut.equals(other.ut))
            return false;
        return true;
    }


}
