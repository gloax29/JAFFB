/*
 * Creation : 19 mars 2014
 */
/**
 *
 */
package javax.flat.bind.make;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.flat.bind.JFFPBException;
import javax.flat.bind.annotation.adapter.PositionalAdapter;
import javax.flat.bind.annotation.adapter.PositionalDefault;
import javax.flat.bind.annotation.csv.CsvMappingParse;
import javax.flat.bind.annotation.positinal.PositionalControlRegex;
import javax.flat.bind.annotation.positinal.PositionalFormatFile;
import javax.flat.bind.annotation.positinal.PositionalJavaTypeAdapter;
import javax.flat.bind.annotation.positinal.PositionalMappingParse;
import javax.flat.bind.annotation.positinal.PositionalMappingParseRootElem;
import javax.flat.bind.api.FormatRootElem;
import javax.flat.bind.utils.StringUtils;

/**
 * class static qui cers a recuparer les annotation de la class
 * 
 * @author Gloaguen Joel
 */
public class PositionalMakeAnnotation {
    private static String UNPARSING = "unmarshal";

    /**
     * Getter positionnalFormatFile
     * 
     * @return the positionnalFormatFile
     */
    public static PositionalFormatFile getPositionnalFormatFile(Class<?> object2) {

        return object2.getAnnotation(PositionalFormatFile.class);
    }

    /**
     * Getter positionnalMappingParce
     * 
     * @return the positionnalMappingParce
     */
    public static PositionalMappingParse getPositionnalMappingParse(Class<?> objClass) {

        return objClass.getAnnotation(PositionalMappingParse.class);
    }

    /**
     * Getter positionnalJavaTypeAdapter
     * 
     * @return the positionnalJavaTypeAdapter
     */
    public static PositionalJavaTypeAdapter getPositionnalJavaTypeAdapter(Class<?> objClass) {
        return objClass.getAnnotation(PositionalJavaTypeAdapter.class);
    }

    /**
     * Getter positionnalControlRegex
     * 
     * @return the positionnalControlRegex
     */
    public static PositionalControlRegex getPositionnalControlRegex(Class<?> objClass) {
        return objClass.getAnnotation(PositionalControlRegex.class);
    }

    public static PositionalMappingParse getFieldPositionnalMappingParse(Field field) {

        return field.getAnnotation(PositionalMappingParse.class);
    }

    public static PositionalControlRegex getFielPositionnalControlRegex(Field field) {
        return field.getAnnotation(PositionalControlRegex.class);
    }

    public static PositionalJavaTypeAdapter getFielPositionnalJavaTypeAdapter(Field field) {
        return field.getAnnotation(PositionalJavaTypeAdapter.class);
    }

    public static PositionalMappingParseRootElem getFieldPositionalMappingParseRootElem(Field field) {
        return field.getAnnotation(PositionalMappingParseRootElem.class);
    }

    public static CsvMappingParse getFieldCsvMappingParse(Field field) {
        return field.getAnnotation(CsvMappingParse.class);
    }

    @SuppressWarnings("unchecked")
    public static Object typeAdapter(Object valeur, @SuppressWarnings("rawtypes") Class<? extends PositionalAdapter> value, String parsingORunparsing)
            throws JFFPBException {

        for (Method element : value.getMethods()) {
            if ("unmarshal".equals(element.getName()) && parsingORunparsing.equals(UNPARSING)) {
                for (Class<?> class1 : element.getParameterTypes()) {
                    try {
                        if (valeur.getClass().getName().equals(class1.getName())) {

                            return value.newInstance().unmarshal(valeur);
                        }

                    } catch (Exception e) {
                        throw new JFFPBException(e);
                    }
                }

            }

        }
        try {
            return value.newInstance().marshal(valeur);
        } catch (InstantiationException e) {
            throw new JFFPBException(e);
        } catch (IllegalAccessException e) {
            throw new JFFPBException(e);
        }

    }

    public static Object typeAdapterDefault(@SuppressWarnings("rawtypes") Class<? extends PositionalDefault> defaultValue) throws JFFPBException {

        try {
            return defaultValue.newInstance().getValue();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new JFFPBException(e);
        }

    }

    public static FormatRootElem formatInfoClassRootElem(PositionalMappingParseRootElem fieldClassMain2) {
        FormatRootElem format = new FormatRootElem();
        format.setName(fieldClassMain2.name());
        format.setIslist(fieldClassMain2.list());
        format.setStartRowsIterationLigne(fieldClassMain2.startRowsIterationLigne());
        format.setValuLongueurChaine(fieldClassMain2.valuLongueurChaine());
        format.setNumbersRowsIterator(fieldClassMain2.numbersRowsIterator());
        format.setForClass(fieldClassMain2.theclass());
        format.setCharRemplissage(fieldClassMain2.charcatereRepli());
        format.setExpression(fieldClassMain2.expression());

        return format;

    }

    /**
     * @param valeur
     * @param typeClass
     * @return
     * @throws Exception
     */
    @SuppressWarnings("javadoc")
    public static Object typeAdapterPimitif(Object valeur, Class<?> typeClass) throws JFFPBException {
        String simpleName = typeClass.getSimpleName();

        try {

            if ("byte".equals(simpleName)) {
                return Byte.valueOf(String.valueOf(valeur)).byteValue();
            }

            else if ("char".equals(simpleName)) {
                return String.valueOf(valeur).charAt(0);
            }

            else if ("String".equals(simpleName)) {
                return String.valueOf(valeur);
            }

            else if ("double".equals(simpleName)) {
                return Double.valueOf(StringUtils.strip(String.valueOf(valeur))).doubleValue();
            }

            else if ("float".equals(simpleName)) {
                return Float.valueOf(StringUtils.strip(String.valueOf(valeur))).floatValue();
            }

            else if ("int".equals(simpleName) || "Integer".equals(simpleName)) {
                return Integer.valueOf(StringUtils.strip(String.valueOf(valeur))).intValue();
            }

            else if ("long".equals(simpleName)) {
                return Long.valueOf(String.valueOf(valeur)).longValue();
            }

            else if ("short".equals(simpleName)) {
                return Short.valueOf(StringUtils.strip(String.valueOf(valeur))).shortValue();
            }

            else if ("boolean".equals(simpleName)) {
                return Boolean.valueOf(String.valueOf(valeur)).booleanValue();
            }

        } catch (Exception e) {

            throw new JFFPBException(e);

        }
        return null;
    }

}
