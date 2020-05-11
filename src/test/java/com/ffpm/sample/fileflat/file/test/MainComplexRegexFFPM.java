/**
 * 
 */
package com.ffpm.sample.fileflat.file.test;

import java.io.File;

import javax.flat.bind.JAFFBContext;
import javax.flat.bind.Unmarshaller;

import com.ffpm.sample.fileflat.file.readcomplex.FileRootForFFPMComplex;

/**
 * @author Gloax29
 *
 */
public class MainComplexRegexFFPM {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		/**
		 * lecture d'un fichier pour cree un object
 		 * 
		 */
		
		
		JAFFBContext contex = JAFFBContext.newInstance(FileRootForFFPMComplex.class) ;
		Unmarshaller unmarshaller = contex.createUnmarshaller();
		
		
		FileRootForFFPMComplex ffpmComplex = (FileRootForFFPMComplex) unmarshaller.unmarshal(new File("src/test/resources/TestComplexRegexFileflat.txt"));

		System.out.println(ffpmComplex.toString());
		
		
	}

}
