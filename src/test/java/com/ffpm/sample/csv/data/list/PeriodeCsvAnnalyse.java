/*
 * Creation : 7 janv. 2015
 */
/**
 * 
 */
package com.ffpm.sample.csv.data.list;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.flat.bind.annotation.csv.CsvMappingParse;

/**
 * TODO : Description
 * 
 * @author E446027
 *
 */
public class PeriodeCsvAnnalyse {
    
   private String  numeroPeriode ;
    // si non decouper a la semaine
   private Date dateDeDebut ;
   private Date dateDeFin ;
   private Integer quantiteForPeriode ;
   @CsvMappingParse(offset = 1)
   private List<SemaineCsvAnalyse> semaines ;
   
   
   
   
/**
 * {@inheritDoc}
 * 
 * @see java.lang.Object#hashCode()
 */
@Override
public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((numeroPeriode == null) ? 0 : numeroPeriode.hashCode());
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
    PeriodeCsvAnnalyse other = (PeriodeCsvAnnalyse) obj;
    if (numeroPeriode == null) {
        if (other.numeroPeriode != null)
            return false;
    } else if (!numeroPeriode.equals(other.numeroPeriode))
        return false;
    
    return true;
}

/**
 * @param numeroPeriode
 */
public PeriodeCsvAnnalyse(String numeroPeriode,Date dateDeDebut ,Date dateDeFin ) {
    super();
    this.numeroPeriode = numeroPeriode;
    this.quantiteForPeriode = new Integer(0);
    this.dateDeDebut = dateDeDebut;
    this.dateDeFin =dateDeFin ;
}

/**
 * @param numeroPeriode
 */
public PeriodeCsvAnnalyse() {

}
/**
 * Getter numeroPeriode
 * 
 * @return the numeroPeriode
 */
public String getNumeroPeriode() {
    return numeroPeriode;
}
/**
 * Setter numeroPeriode
 * 
 * @param numeroPeriode the numeroPeriode to set
 */
public void setNumeroPeriode(String numeroPeriode) {
    this.numeroPeriode = numeroPeriode;
}
/**
 * Getter quantiteForPeriode
 * 
 * @return the quantiteForPeriode
 */
public Integer getQuantiteForPeriode() {
    return quantiteForPeriode;
}
/**
 * Setter quantiteForPeriode
 * 
 * @param quantiteForPeriode the quantiteForPeriode to set
 */
public void setQuantiteForPeriode(Integer quantiteForPeriode) {
    this.quantiteForPeriode = quantiteForPeriode;
}
/**
 * Getter semaines
 * 
 * @return the semaines
 */
public List<SemaineCsvAnalyse> getSemaines() {
    
    Calendar cal = Calendar.getInstance() ;
    cal.setTime(dateDeDebut);
    
    if(semaines == null){
        semaines = new ArrayList<SemaineCsvAnalyse>(5);
        for (int i = 0; i < 5 ; i++) {
            
            SemaineCsvAnalyse sm = new SemaineCsvAnalyse("0"+(i+1) ) ;
            sm.setDateDeDebut(cal.getTime());
            
            semaines.add(sm);
            cal.add(Calendar.DAY_OF_MONTH, 7) ;
        }
    }
    return semaines;
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
