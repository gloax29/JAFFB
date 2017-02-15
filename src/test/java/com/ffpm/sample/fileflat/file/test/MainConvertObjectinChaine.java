/**
 * 
 */
package com.ffpm.sample.fileflat.file.test;

import java.util.Properties;

import javax.flat.bind.JAFFBContext;
import javax.flat.bind.Marshaller;

import com.ffpm.sample.fileflat.file.readsimple.FileRootForFFPMSimple;
import com.ffpm.sample.fileflat.file.readsimple.LigneSimple;

/**
 * @author e446027
 *
 */
public class MainConvertObjectinChaine {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		//chaine de caractere
		String chaine = "002165224203        76B0A01BB0B0CB B0CWAB B0DAFB B0E0EB B0FBFB B0G0TB B0H04B B0JA0B B0K04B B0L00B B0MM0B B0NKUB B0P2LB B0RFXBDAB00CDDAD00CDDAE07CDDAF01CDDAG00CDDAI00CDDAJ01CDDAL33CDDAP0";
		/**
		 * creation d'un simple object a partir d'une chaine de carractere
		 */
		
		JAFFBContext contex = JAFFBContext.newInstance() ;
	
		
		
		LigneSimple ligneSimple = (LigneSimple) contex.createUnmarshaller().convertStringChaineInObject(LigneSimple.class, chaine);
		
		System.out.println(ligneSimple.toString());
		
		/**
		 * creation d'une chaine de caractere a partir d'un object
		 * 
		 */
		//ligneSimple.setColume(ligneSimple.getColume().trim() ) ;
		Marshaller marshaller = contex.createMarshaller() ;
		String inverse = marshaller.convertObjectInStringChaine(ligneSimple, ' ', 185);
		
		System.out.println(inverse);
		/**
		 * 
		 * creation d' une chaine de caractere avec desactivation
		 * 
		 */
		Properties desactivate = new Properties();
		desactivate.put("refference", "refference");
		desactivate.put("valueRef", "valueRef");
		
		/**
		 * avec indication de remplissage et longueur
		 * avec desactivation des champts
		 * production de chaine de longueur indiquer
		 * 
		 */
		
		String inverseDesac = marshaller.convertObjectInStringChaine(ligneSimple, ' ', 185,desactivate);
		
		System.out.println(inverseDesac);
		/**
		 * pas d'indication de longueur n y remplissage
		 * 
		 * produit une chaine de longueur 
		 * variate en fonction de la  valeur des attributs
		 * 
		 */
		Properties desactivate2 = new Properties();
		desactivate2.put("refference", "refference");
		desactivate2.put("valueRef", "valueRef");
		desactivate2.put("idLigne", "idLigne");
		
		String inverseDesanoLong = marshaller.convertObjectInStringChaine(ligneSimple, desactivate2);
		
		System.out.println(inverseDesanoLong);

	}

}
