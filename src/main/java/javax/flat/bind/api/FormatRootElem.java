/*
 * Creation : 17 mars 2014
 */
package javax.flat.bind.api;

import java.lang.reflect.Field;

/**
 * class des valeur de l'annotation
 * 
 * @author Gloaguen Joel
 */
public class FormatRootElem {

    private Integer startRowsIterationLigne;
    private Integer numbersRowsIterator;
    private Integer valuLongueurChaine;
    private String name;
    private boolean islist = false;
    private Class<?> forClass;
    private char charRemplissage;
    private String expression;

    /**
     * Getter valuDebutLigne
     * 
     * @return the valuDebutLigne
     */
    public Integer getStartRowsIterationLigne() {
        return startRowsIterationLigne;
    }

    /**
     * Setter valuDebutLigne
     * 
     * @param startRowsIterationLigne the valuDebutLigne to set
     */
    public void setStartRowsIterationLigne(int startRowsIterationLigne) {
        this.startRowsIterationLigne = startRowsIterationLigne;
    }

    /**
     * Getter valuNumbLigne
     * 
     * @return the valuNumbLigne
     */
    public Integer getNumbersRowsIterator() {
        return numbersRowsIterator;
    }

    /**
     * Setter valuNumbLigne
     * 
     * @param numbersRowsIterator the valuNumbLigne to set
     */
    public void setNumbersRowsIterator(Integer numbersRowsIterator) {
        this.numbersRowsIterator = numbersRowsIterator;
    }

    /**
     * Getter valuLongueurChaine
     * 
     * @return the valuLongueurChaine
     */
    public Integer getValuLongueurChaine() {
        return valuLongueurChaine;
    }

    /**
     * Setter valuLongueurChaine
     * 
     * @param valuLongueurChaine the valuLongueurChaine to set
     */
    public void setValuLongueurChaine(Integer valuLongueurChaine) {
        this.valuLongueurChaine = valuLongueurChaine;
    }

    /**
     * Getter name
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter name
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter islist
     * 
     * @return the islist
     */
    public boolean isIslist() {
        return islist;
    }

    /**
     * Setter islist
     * 
     * @param islist the islist to set
     */
    public void setIslist(boolean islist) {
        this.islist = islist;
    }

    /**
     * Getter forClass
     * 
     * @return the forClass
     */
    public Class<?> getForClass() {
        return forClass;
    }

    /**
     * Setter forClass
     * 
     * @param forClass the forClass to set
     */
    public void setForClass(Class<?> forClass) {
        this.forClass = forClass;
    }

    /**
     * Getter charcatereRepli
     * 
     * @return the charcatereRepli
     */
    public char getCharRemplissage() {
        return charRemplissage;
    }

    /**
     * Setter charcatereRepli
     * 
     * @param charcatereRepli the charcatereRepli to set
     */
    public void setCharRemplissage(char charRemplissage) {
        this.charRemplissage = charRemplissage;
    }

    public void setExpression(String expression) {
        this.expression = expression;

    }

    public String getExpression() {
        return this.expression;

    }

    @Override
    public String toString() {

        Field[] nomChamps = this.getClass().getDeclaredFields();
        StringBuffer formatString = new StringBuffer(this.getClass().getSimpleName() + "(");
        for (Field field : nomChamps) {
            if (!"serialVersionUID".equals(field.getName())) {
                try {
                    Object Valu = this.getClass().getMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1))
                            .invoke(this, new Object[] {});
                    formatString.append(";[ " + field.getName() + "='" + Valu.toString() + "' ]");
                } catch (Exception e) {
                    formatString.append(";[ " + field.getName() + "='null' ]");
                }

            }
        }
        return formatString.append(")\n").toString().replaceFirst(";", "");

    }
}
