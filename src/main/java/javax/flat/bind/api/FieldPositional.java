/*
 * Creation : 8 avr. 2014
 */
package javax.flat.bind.api;

import java.lang.reflect.Field;

import javax.flat.bind.JFFPBException;
import javax.flat.bind.annotation.positinal.PositionalControlRegex;
import javax.flat.bind.annotation.positinal.PositionalJavaTypeAdapter;
import javax.flat.bind.annotation.positinal.PositionalMappingParse;
import javax.flat.bind.make.PositionalMakeAnnotation;

/**
 * conservation des informations<br/>
 * des annotations
 * 
 * @author Gloaguen Joel
 */
public class FieldPositional {

    private static final String SETTEUR = "set";
    private PositionalMappingParse positionnalMappingParse;
    private PositionalControlRegex positionnalControlRegex;
    private PositionalJavaTypeAdapter positionnalJavaTypeAdapter;
    private String namSetMethode;
    private Field field;

    /**
     * @param field
     * @throws JFFPBException
     */
    public FieldPositional(Field field) throws JFFPBException {
        this.field = field;
        this.positionnalMappingParse = PositionalMakeAnnotation.getFieldPositionnalMappingParse(field);
        this.positionnalControlRegex = PositionalMakeAnnotation.getFielPositionnalControlRegex(field);
        this.positionnalJavaTypeAdapter = PositionalMakeAnnotation.getFielPositionnalJavaTypeAdapter(field);
        this.namSetMethode = SETTEUR + field.getName().replaceFirst(field.getName().substring(0, 1), field.getName().substring(0, 1).toUpperCase());

    }

    /**
     * Getter positionnalJavaTypeAdapter
     * 
     * @return the positionnalJavaTypeAdapter
     */
    public PositionalJavaTypeAdapter getPositionnalJavaTypeAdapter() {
        return positionnalJavaTypeAdapter;
    }

    /**
     * Getter positionnalMappingParce
     * 
     * @return the positionnalMappingParce
     */
    public PositionalMappingParse getPositionnalMappingParse() {
        return positionnalMappingParse;
    }

    /**
     * Getter positionnalControlRegex
     * 
     * @return the positionnalControlRegex
     */
    public PositionalControlRegex getPositionnalControlRegex() {
        return positionnalControlRegex;
    }

    /**
     * Getter namMethode
     * 
     * @return the namMethode
     */
    public String getNamSetMethode() {
        return namSetMethode;
    }

    /**
     * Getter field
     * 
     * @return the field
     */
    public Field getField() {
        return field;
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
