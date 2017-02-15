/**
 * 
 */
package com.ffpm.sample.fileflat.file.readsimple;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.flat.bind.annotation.positinal.PositionalFormatFile;
import javax.flat.bind.annotation.positinal.PositionalMappingParseRootElem;

/**
 * @author gloax29
 *
 */
@PositionalFormatFile(name = "FileRootForFFPMSimple")
public class FileRootForFFPMSimple {
	// no de l'attribut , le nombre de ligne conserner
	@PositionalMappingParseRootElem(name = "enteteSimple",numbersRowsIterator=1, startRowsIterationLigne = 1,valuLongueurChaine=56,theclass=EnteteSimple.class)
	private EnteteSimple enteteSimple ;
	
	@PositionalMappingParseRootElem(name = "ligneSimples",list=true,theclass=LigneSimple.class, startRowsIterationLigne = 2,valuLongueurChaine=185)
	private List<LigneSimple> ligneSimples ;

	/**
	 * @return the enteteSimple
	 */
	public EnteteSimple getEnteteSimple() {
		return enteteSimple;
	}

	/**
	 * @param enteteSimple the enteteSimple to set
	 */
	public void setEnteteSimple(EnteteSimple enteteSimple) {
		this.enteteSimple = enteteSimple;
	}

	/**
	 * @return the ligneSimples
	 */
	public List<LigneSimple> getLigneSimples() {
		if(ligneSimples == null){
			this.ligneSimples = new ArrayList<LigneSimple>();
		}
		return ligneSimples;
	}

	/**
	 * @param ligneSimples the ligneSimples to set
	 */
	public void setLigneSimples(List<LigneSimple> ligneSimples) {
		this.ligneSimples = ligneSimples;
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
