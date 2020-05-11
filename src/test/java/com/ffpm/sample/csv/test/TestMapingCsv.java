/**
 * 
 */
package com.ffpm.sample.csv.test;

import javax.flat.bind.JAFFBContext;
import javax.flat.bind.Unmarshaller;

import com.ffpm.sample.csv.data.ObjectForCsvMapping;

/**
 * @author e446027
 *
 */
public class TestMapingCsv {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		String data = "Nom;Prenom;05/10/2014;100;" ;

		
		
		JAFFBContext  jaffbContext = JAFFBContext.newInstance() ;
	      Unmarshaller  unmarshaller = jaffbContext.createUnmarshaller();
		
		
		ObjectForCsvMapping csvMapping = (ObjectForCsvMapping) unmarshaller.convertChaineCsvInObject(ObjectForCsvMapping.class, data, ";");
		
		System.out.println(csvMapping.toString());
		
		String result = jaffbContext.createMarshaller().convertObjectInChaineCsv(csvMapping, ";" );
		
		System.out.println(result);
		

	}

}
