/**
 * 
 */
package com.ffpm.sample.fileflat.file.readcomplex;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.flat.bind.annotation.positinal.PositionalFormatFile;
import javax.flat.bind.annotation.positinal.PositionalMappingParseRootElem;

/**
 * @author gloax29
 *
 */
@PositionalFormatFile(name = "FileRootForFFPMComplex")
public class FileRootForFFPMComplex {
	// no de l'attribut , le nombre de ligne conserner
	@PositionalMappingParseRootElem(startRowsIterationLigne = 1,list = true, name = "enteteSimple", expression = "^((Test1Simple )(.*)|(Test1Complex)(.*))", theclass = EnteteComplex.class, charcatereRepli = ' ')
	private List<EnteteComplex> enteteComplex ;
	
	
	@PositionalMappingParseRootElem(startRowsIterationLigne = 2, list = true, name = "ligneA", expression =  "^(LIGNEA)(.*)", theclass = LigneComplex.class, charcatereRepli = ' ')
	private List<LigneComplex> ligneA ;

	@PositionalMappingParseRootElem(startRowsIterationLigne = 3, list = true, name = "ligneB", expression = "^(LIGNEB)(.*)", theclass = LigneComplex.class, charcatereRepli = ' ')
	private List<LigneComplex> ligneB ;
	
	@PositionalMappingParseRootElem(startRowsIterationLigne = 4, list = true, name = "ligneC", expression = "^(LIGNEC)(.*)", theclass = LigneComplex.class, charcatereRepli = ' ')
	private List<LigneComplex> ligneC ;
	
	@PositionalMappingParseRootElem(startRowsIterationLigne = 5, list = true, name = "ligneD", expression = "^(LIGNED)(.*)", theclass = LigneComplex.class, charcatereRepli = ' ')
	private List<LigneComplex> ligneD ;
	
	// ligne qui ne correspond a aucune expretion
	@PositionalMappingParseRootElem(startRowsIterationLigne = 6, list = true, name = "ligneE", expression =PositionalMappingParseRootElem.NOEXPRES , theclass = NonTraiter.class, charcatereRepli = ' ')
	private List<NonTraiter> ligneE ;
	
	
	
	



	/**
	 * @return the enteteComplex
	 */
	public List<EnteteComplex> getEnteteComplex() {
		if (enteteComplex == null) {
			enteteComplex = new ArrayList<EnteteComplex>() ;
		}
		return enteteComplex;
	}









	/**
	 * @param enteteComplex the enteteComplex to set
	 */
	public void setEnteteComplex(List<EnteteComplex> enteteComplex) {
		this.enteteComplex = enteteComplex;
	}









	/**
	 * @return the ligneA
	 */
	public List<LigneComplex> getLigneA() {
		if (ligneA == null) {
			ligneA = new ArrayList<LigneComplex>() ;
		}
		return ligneA;
	}









	/**
	 * @param ligneA the ligneA to set
	 */
	public void setLigneA(List<LigneComplex> ligneA) {
		this.ligneA = ligneA;
	}









	/**
	 * @return the ligneB
	 */
	public List<LigneComplex> getLigneB() {
		if (ligneB == null) {
			ligneB = new ArrayList<LigneComplex>() ;
		}
		return ligneB;
	}









	/**
	 * @param ligneB the ligneB to set
	 */
	public void setLigneB(List<LigneComplex> ligneB) {
		this.ligneB = ligneB;
	}









	/**
	 * @return the ligneC
	 */
	public List<LigneComplex> getLigneC() {
		if (ligneC == null) {
			ligneC = new ArrayList<LigneComplex>() ;
		}
		return ligneC;
	}









	/**
	 * @param ligneC the ligneC to set
	 */
	public void setLigneC(List<LigneComplex> ligneC) {
		this.ligneC = ligneC;
	}









	/**
	 * @return the ligneD
	 */
	public List<LigneComplex> getLigneD() {
		if (ligneD == null) {
			ligneD = new ArrayList<LigneComplex>() ;
		}
		return ligneD;
	}









	/**
	 * @param ligneD the ligneD to set
	 */
	public void setLigneD(List<LigneComplex> ligneD) {
		this.ligneD = ligneD;
	}









	/**
	 * @return the ligneE
	 */
	public List<NonTraiter> getLigneE() {
		if (ligneE == null) {
			ligneE = new ArrayList<NonTraiter>() ;
		}
		return ligneE;
	}









	/**
	 * @param ligneE the ligneE to set
	 */
	public void setLigneE(List<NonTraiter> ligneE) {
		this.ligneE = ligneE;
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
