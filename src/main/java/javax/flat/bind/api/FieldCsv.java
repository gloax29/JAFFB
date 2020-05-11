/**
 * 
 */
package javax.flat.bind.api;

import java.lang.reflect.Field;

import javax.flat.bind.annotation.csv.CsvMappingParse;
import javax.flat.bind.annotation.positinal.PositionalControlRegex;
import javax.flat.bind.annotation.positinal.PositionalJavaTypeAdapter;
import javax.flat.bind.make.PositionalMakeAnnotation;

/**
 * @author joel
 */
public class FieldCsv {

    private static final String SETTEUR = "set";
    private Field field;
    private CsvMappingParse csvMappingParse;
    private PositionalControlRegex positionnalControlRegex;
    private PositionalJavaTypeAdapter positionnalJavaTypeAdapter;
    private String namSetMethode;

    public FieldCsv(Field field) {
        this.field = field;
        this.csvMappingParse = PositionalMakeAnnotation.getFieldCsvMappingParse(field);
        this.positionnalControlRegex = PositionalMakeAnnotation.getFielPositionnalControlRegex(field);
        this.positionnalJavaTypeAdapter = PositionalMakeAnnotation.getFielPositionnalJavaTypeAdapter(field);
        this.namSetMethode = SETTEUR + field.getName().toLowerCase();

    }

    /**
     * @return the field
     */
    public Field getField() {
        return field;
    }

    /**
     * @param field the field to set
     */
    public void setField(Field field) {
        this.field = field;
    }

    /**
     * @return the csvMappingParse
     */
    public CsvMappingParse getCsvMappingParse() {
        return csvMappingParse;
    }

    /**
     * @param csvMappingParse the csvMappingParse to set
     */
    public void setCsvMappingParse(CsvMappingParse csvMappingParse) {
        this.csvMappingParse = csvMappingParse;
    }

    /**
     * @return the positionnalControlRegex
     */
    public PositionalControlRegex getPositionnalControlRegex() {
        return positionnalControlRegex;
    }

    /**
     * @param positionnalControlRegex the positionnalControlRegex to set
     */
    public void setPositionnalControlRegex(PositionalControlRegex positionnalControlRegex) {
        this.positionnalControlRegex = positionnalControlRegex;
    }

    /**
     * @return the positionnalJavaTypeAdapter
     */
    public PositionalJavaTypeAdapter getPositionnalJavaTypeAdapter() {
        return positionnalJavaTypeAdapter;
    }

    /**
     * @param positionnalJavaTypeAdapter the positionnalJavaTypeAdapter to set
     */
    public void setPositionnalJavaTypeAdapter(PositionalJavaTypeAdapter positionnalJavaTypeAdapter) {
        this.positionnalJavaTypeAdapter = positionnalJavaTypeAdapter;
    }

    /**
     * @return the namSetMethode
     */
    public String getNamSetMethode() {
        return namSetMethode;
    }

    /**
     * @param namSetMethode the namSetMethode to set
     */
    public void setNamSetMethode(String namSetMethode) {
        this.namSetMethode = namSetMethode;
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
