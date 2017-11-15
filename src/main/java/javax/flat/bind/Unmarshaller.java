/**
 * 
 */
package javax.flat.bind;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.flat.bind.api.FieldCsv;
import javax.flat.bind.api.FieldPositional;
import javax.flat.bind.control.ControleInfo;
import javax.flat.bind.make.PositionalMakeAnnotation;
import javax.flat.bind.utils.StringUtils;

import com.flat.internal.runtime.CommunContext;

/**
 * @author root
 */
public abstract class Unmarshaller extends CommunContext {

    private static final Logger LOG = Logger.getLogger(Marshaller.class.getName());

    /**
     * <b>Du fichier a L'objet</b><br />
     * transforme le fichier plat en Objet <br />
     * contenant les informations de celui ci en respectant <br />
     * la description faite par les annotations.<br />
     * qui se trouvent dans la Classe parent.
     * 
     * @throws Throwable
     * @see #PositionalMappingParseRootElem
     */
    public abstract Object unmarshal(File file) throws JFFPBException;

    /**
     * @param inputStreamFile
     * @return
     * @throws JFFPBException
     */
    public abstract Object unmarshal(InputStream inputStream) throws JFFPBException;

    /**
     * Converti la chaine de caractere en Object de la<br />
     * class passer en parametre <br />
     * 
     * @param myClass
     * @param data
     * @return Object de la class
     * @throws Throwable
     */
    public Object convertStringChaineInObject(Class<?> myClass, String data) throws JFFPBException {
        Object obj = getNewInstanceType(myClass);
        List<FieldPositional> fDligneRoot = new ArrayList<FieldPositional>();
        Map<String, Method> map = new HashMap<String, Method>();
        int numberMethode = 0;
        Method[] mth = null;

        mth = ControleInfo.retroMethodes(obj, mth, numberMethode);

        for (Method method : mth) {

            map.put(method.getName().toLowerCase(), method);

        }
        int numberField = 0;
        Field[] fdcp = null;
        fdcp = ControleInfo.retroFields(obj, fdcp, numberField);

        for (Field field : fdcp) {
            FieldPositional f = new FieldPositional(field);
            if (f.getPositionnalMappingParse() != null)
                fDligneRoot.add(new FieldPositional(field));

        }
        Collections.sort(fDligneRoot, COMPARATEUR_RANG_FIELD);
        for (FieldPositional field : fDligneRoot) {

            makeInvokeSetMethodes(obj, field, map.get(field.getNamSetMethode().toLowerCase()), data);
            // on s'arrette car parcing false
            if (field.getPositionnalJavaTypeAdapter() != null && !field.getPositionnalJavaTypeAdapter().parcing()) {
                break;
            }
        }

        return obj;

    }

    /**
     * méthode qui convertit la chaine de caractéres csv en object
     * 
     * @param myClass Classe de l'objet avec les annotations
     * @param data String chaéne de caractéres
     * @param regex String caractére de séparation
     * @return Object of the Class
     * @throws Exception
     */
    public Object convertChaineCsvInObject(Class<?> myClass, String data, String regex) throws JFFPBException {
        List<String> listCSV = StringUtils.splitinList(regex, data);
        Object obj = getNewInstanceType(myClass);
        Map<Integer, FieldCsv> fDligneRoot = new HashMap<Integer, FieldCsv>();
        Map<String, Method> map = new HashMap<String, Method>();
        for (Method method : obj.getClass().getDeclaredMethods()) {

            map.put(method.getName().toLowerCase(), method);

        }

        for (Field field : obj.getClass().getDeclaredFields()) {
            FieldCsv fd = new FieldCsv(field);
            if (fd.getCsvMappingParse() != null) {
                fDligneRoot.put(fd.getCsvMappingParse().offset(), fd);
            }
        }

        for (Integer i = 0; i < listCSV.size(); i++) {
            if (!StringUtils.isBlank(listCSV.get(i))) {
                makeInvokeCsvSetMethode(obj, fDligneRoot.get(i + 1), map.get(fDligneRoot.get(i + 1).getNamSetMethode()), listCSV.get(i));
            }
        }

        return obj;

    }

    /**
     * methode qui convertit la chaine de caracteres csv en object avec une reorganisation de colone /\<br>
     * / \<br>
     * / !! \<br>
     * / !! \<br>
     * --------<br>
     * L' objet doit correcpondre a la reorganisation
     * 
     * @param myClass Classe de l'objet avec les annotations
     * @param data String chaéne de caractéres
     * @param regex String caractére de séparation
     * @return Object of the Class
     * @throws Exception
     */
    public Object convertChaineCsvInObject(Class<?> myClass, String data, String regex, int[] reorganisation) throws JFFPBException {
        List<String> listCSV = StringUtils.splitinList(regex, data);
        if (reorganisation.length != listCSV.size()) {
            throw new JFFPBException(" Nombre de colone different de la reorganosation ");
        }
        Object obj = getNewInstanceType(myClass);
        Map<Integer, FieldCsv> fDligneRoot = new HashMap<Integer, FieldCsv>();
        Map<String, Method> map = new HashMap<String, Method>();
        for (Method method : obj.getClass().getDeclaredMethods()) {

            map.put(method.getName().toLowerCase(), method);

        }

        for (Field field : obj.getClass().getDeclaredFields()) {
            FieldCsv fd = new FieldCsv(field);
            fDligneRoot.put(fd.getCsvMappingParse().offset(), fd);
        }

        for (Integer i = 0; i < reorganisation.length; i++) {
            if (!StringUtils.isBlank(listCSV.get(reorganisation[i]))) {
                makeInvokeCsvSetMethode(obj, fDligneRoot.get(i + 1), map.get(fDligneRoot.get(i + 1).getNamSetMethode()),
                        listCSV.get(reorganisation[i]));
            }
        }

        return obj;
    }

    protected static Object makeInvokeSetMethodes(Object obMake, FieldPositional fieldPosiotinalAnnot, Method method, String chaineCaractere)
            throws JFFPBException {
        if (fieldPosiotinalAnnot.getPositionnalMappingParse() != null && !fieldPosiotinalAnnot.getPositionnalMappingParse().desactivate()) {
            Object obInvoke = null;
            String valueChaine = null;
            int depart = fieldPosiotinalAnnot.getPositionnalMappingParse().offset() - 1;
            try {

                if (fieldPosiotinalAnnot.getPositionnalMappingParse().length() == -1) {
                    valueChaine = chaineCaractere.substring(depart);
                } else {

                    valueChaine = chaineCaractere.substring(depart, depart + fieldPosiotinalAnnot.getPositionnalMappingParse().length());
                    if (fieldPosiotinalAnnot.getPositionnalMappingParse().stripChaine()) {
                        valueChaine = StringUtils.strip(valueChaine);
                    }
                }
            } catch (Exception e) {
                throw new JFFPBException(" Problem de longueur de chaine :" + chaineCaractere + " : " + e.getMessage());

            }
            if (fieldPosiotinalAnnot.getPositionnalControlRegex() != null) {
                // si la ligne est conform
                if (!Pattern.compile(fieldPosiotinalAnnot.getPositionnalControlRegex().expression()).matcher(valueChaine).find()) {
                    if (fieldPosiotinalAnnot.getPositionnalMappingParse().required()) {

                        throw new JFFPBException("Non conformité de la chaine de caracter ! '" + valueChaine + "' pour l'expression "
                                + fieldPosiotinalAnnot.getPositionnalControlRegex().expression());
                    }
                    getAfficheLog("value Non conforme ==> " + valueChaine + " pour l'expression ==> "
                            + fieldPosiotinalAnnot.getPositionnalControlRegex().expression());
                }

            }

            if (fieldPosiotinalAnnot.getPositionnalJavaTypeAdapter() != null) {
                // si pas de parcing alors le traitement de la chaine est brute (text) et doit etre traiter manuellement
                obInvoke = fieldPosiotinalAnnot.getPositionnalJavaTypeAdapter().parcing()
                        ? PositionalMakeAnnotation.typeAdapter(valueChaine, fieldPosiotinalAnnot.getPositionnalJavaTypeAdapter().value(), UNPARSING)
                        : valueChaine;
            } else {

                obInvoke = PositionalMakeAnnotation.typeAdapterPimitif(valueChaine, fieldPosiotinalAnnot.getField().getType());

            }
            try {

                method.invoke(obMake, obInvoke);

            } catch (Exception e) {

                StringBuffer st = new StringBuffer();

                if (obMake != null)
                    st.append(" obMake ==> " + obMake);
                if (obInvoke != null)
                    st.append(" obInvoke ==> " + obInvoke.toString());
                if (valueChaine != null)
                    st.append(" value ==> " + valueChaine.toString());

                throw new JFFPBException(
                        e.getMessage() + " value Adapter " + fieldPosiotinalAnnot.getPositionnalJavaTypeAdapter().value() + " " + st.toString());
            }

        }
        return obMake;
    }

    private static Object makeInvokeCsvSetMethode(Object obMake, FieldCsv fieldCsv, Method method, String chaineCaractere) throws JFFPBException {
        if (fieldCsv.getCsvMappingParse() != null) {

            Object obInvoke = null;
            if (fieldCsv.getPositionnalControlRegex() != null) {
                // si la ligne est conform
                if (!Pattern.compile(fieldCsv.getPositionnalControlRegex().expression()).matcher(chaineCaractere).find()) {
                    if (fieldCsv.getCsvMappingParse().required()) {

                        throw new JFFPBException("Non conformité de la chaine de caractères ! '" + chaineCaractere + "' pour l'expression "
                                + fieldCsv.getPositionnalControlRegex().expression());
                    }
                    getAfficheLog("value Non conforme ==> " + chaineCaractere + " pour l'expression ==> "
                            + fieldCsv.getPositionnalControlRegex().expression());
                }

            }

            if (fieldCsv.getPositionnalJavaTypeAdapter() != null) {
                obInvoke = PositionalMakeAnnotation.typeAdapter(chaineCaractere, fieldCsv.getPositionnalJavaTypeAdapter().value(), UNPARSING);
            } else {

                if (fieldCsv.getCsvMappingParse().stripChaine()) {
                    chaineCaractere = StringUtils.strip(chaineCaractere);
                }
                obInvoke = PositionalMakeAnnotation.typeAdapterPimitif(chaineCaractere, fieldCsv.getField().getType());

            }
            try {

                method.invoke(obMake, obInvoke);

            } catch (Exception e) {

                StringBuffer st = new StringBuffer();

                if (obMake != null)
                    st.append(" obMake ==> " + obMake);
                if (obInvoke != null)
                    st.append(" obInvoke ==> " + obInvoke.toString());
                if (chaineCaractere != null)
                    st.append(" value ==> " + chaineCaractere.toString());

                throw new JFFPBException(e.getMessage() + " value Adapter " + fieldCsv.getPositionnalJavaTypeAdapter().value() + " " + st.toString());
            }

        }
        return obMake;

    }

    private static void getAfficheLog(String string) {
        LOG.info(string);

    }

    public abstract Object unmarshal(InputStream inputStream, Charset iso) throws JFFPBException;
}
