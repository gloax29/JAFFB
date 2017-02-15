/**
 * 
 */
package com.ffpm.sample.csv.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.flat.bind.JAFFBContext;
import javax.flat.bind.Marshaller;

import com.ffpm.sample.csv.data.list.CorpCsvAnnalyse;
import com.ffpm.sample.csv.data.list.PeriodeCsvAnnalyse;
import com.ffpm.sample.csv.data.list.SemaineCsvAnalyse;

/**
 * @author E446027
 *
 */
public class TestForWriteWithTheList {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		List<CorpCsvAnnalyse> corpCsvAnnalyses = new ArrayList<CorpCsvAnnalyse>();
		
		CorpCsvAnnalyse  corpCsvAnnalyse = new CorpCsvAnnalyse("FAM1","UT1","L01",5,new Date() ,new Date() );
        
        
        if(corpCsvAnnalyses.contains(corpCsvAnnalyse)){
            
            corpCsvAnnalyse = corpCsvAnnalyses.get(corpCsvAnnalyses.indexOf(corpCsvAnnalyse));
            
        }else{
            
            corpCsvAnnalyses.add(corpCsvAnnalyse);
        }
        
        PeriodeCsvAnnalyse per = new PeriodeCsvAnnalyse("P01",new Date(),new Date());
        corpCsvAnnalyse.getPeriodes().add(per);
        SemaineCsvAnalyse semaineCsvAnalyse = new SemaineCsvAnalyse() ;
        semaineCsvAnalyse.setNumeroSemaine("01");
        
        if(per.getSemaines().contains(semaineCsvAnalyse)){
        semaineCsvAnalyse = per.getSemaines().get(per.getSemaines().indexOf(semaineCsvAnalyse)) ;
        }else{
            
        per.getSemaines().add(semaineCsvAnalyse);
            
            
        }

       for (int i = 0; i < 10 ; i++) {
		
        
            semaineCsvAnalyse.setQuantiteForSemaine(semaineCsvAnalyse.getQuantiteForSemaine() +  i );
           
       }
       
       
      JAFFBContext  jaffbContext = JAFFBContext.newInstance() ;
      Marshaller parse = jaffbContext.createMarshaller();
       for (CorpCsvAnnalyse corpCsvAnnalyse2 : corpCsvAnnalyses) {
           
       
  
         String chaine =  parse.convertObjectInChaineCsv(corpCsvAnnalyse2, ";");
         
         
         System.out.println(chaine);
       }
       
        
       
   }

	
	
	
	
	
	

}
