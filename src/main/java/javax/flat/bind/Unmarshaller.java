/**
 * 
 */
package javax.flat.bind;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.flat.bind.annotation.positinal.PositionalMappingParse;
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
     * @param inputStream
     * @param iso
     * @return
     * @throws JFFPBException
     */
    public abstract Object unmarshal(InputStream inputStream, Charset iso) throws JFFPBException;

    /**
     * * Converti la chaine de caractere en prenant Object en refference <br />
     * passer en parametre <br />
     * 
     * @param obj
     * @param data
     * @return
     * @throws JFFPBException
     */
    public synchronized Object convertStringChaineInObject(Object obj, String data) throws JFFPBException {
        // l'intance obj exista deja
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
        // correction de valeur des offset avec laste
        correctionDeValeurDuAuLast(fDligneRoot, data);
        for (FieldPositional field : fDligneRoot) {

            makeInvokeSetMethodes(obj, field, map.get(field.getNamSetMethode().toLowerCase()), data);
            // on s'arrette car parcing false
            if (field.getPositionnalJavaTypeAdapter() != null && !field.getPositionnalJavaTypeAdapter().parcing()) {
                break;
            }
        }
        restorDeValeurDuAuLast(fDligneRoot);
        return obj;

    }

    private void restorDeValeurDuAuLast(List<FieldPositional> fDligneRoot) {
        // LOG.info("restor De Va leur Du Au Last Field est laste. recalcule de ");

        for (int i = 0; i < fDligneRoot.size(); i++) {

            FieldPositional field = fDligneRoot.get(i);

            if (field.getPositionnalMappingParse().laste()) {

                try {

                    changeAnnotationValue(fDligneRoot.get(i).getPositionnalMappingParse(), "length", 0);
                    // LOG.info(String.format("restor De Valeur Du Au Last [%s] ", field.getField().getName()));

                    for (int j = i + 1; j < fDligneRoot.size(); j++) {

                        changeAnnotationValue(fDligneRoot.get(j).getPositionnalMappingParse(), "offset",
                                fDligneRoot.get(j).getPositionnalMappingParse().original());
                        // LOG.info(String.format("restor De Valeur Du Au Last [%s] ", fDligneRoot.get(j).getField().getName()));

                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }

                break;
            }
        }

    }

    /**
     * Correction de valeur du au last. recalcule des Offeset <br />
     * seul un last peux etre present dans le bean
     * 
     * @param fDligneRoot the f dligne root
     * @param data the data
     * @param position the position
     * @throws JFFPBException the JFFPB exception
     */
    protected synchronized void correctionDeValeurDuAuLast(List<FieldPositional> fDligneRoot, String data) throws JFFPBException {
        int unLaste = 0;
        for (int i = 0; i < fDligneRoot.size(); i++) {

            FieldPositional field = fDligneRoot.get(i);

            if (field.getPositionnalMappingParse().laste()) {
                // LOG.info(String.format("Le Field est laste. recalcule de [%s] ", field.getField().getName()));
                int cumule_reste_longeur = 0;
                int longeurChainerestenteacouper = data.length() - i;
                for (int j = i + 1; j < fDligneRoot.size(); j++) {
                    cumule_reste_longeur = cumule_reste_longeur + fDligneRoot.get(j).getPositionnalMappingParse().length();

                }

                changeAnnotationValue(fDligneRoot.get(i).getPositionnalMappingParse(), "length", longeurChainerestenteacouper - cumule_reste_longeur);

                for (int j = i + 1; j < fDligneRoot.size(); j++) {

                    changeAnnotationValue(fDligneRoot.get(j).getPositionnalMappingParse(), "offset",
                            fDligneRoot.get(j).getPositionnalMappingParse().offset() + longeurChainerestenteacouper - cumule_reste_longeur);

                }
                break;
            }

        }

    }

    /**
     * Changes the annotation value for the given key of the given annotation to newValue and returns the previous value.
     */
    @SuppressWarnings("unchecked")
    public synchronized static void changeAnnotationValue(PositionalMappingParse annotation, String key, Object newValue) throws JFFPBException {
        try {
            Object handler = Proxy.getInvocationHandler(annotation);
            Field f = handler.getClass().getDeclaredField("memberValues");
            f.setAccessible(true);
            Map<String, Object> memberValues = (Map<String, Object>) f.get(handler);
            Object oldValue = memberValues.get(key);

            if (oldValue == null || oldValue.getClass() != newValue.getClass()) {
                throw new JFFPBException(" IllegalArgumentException  ");

            }
            // LOG.info(String.format("Clef modifier [%s] ensiene valeur [%s] nouvelle valeur calculer [%s] ", key, oldValue, newValue));
            memberValues.put(key, newValue);
        } catch (Exception e) {
            throw new JFFPBException(" Nombre de colone different de la reorganosation " + e.getMessage());
        }
    }

    /**
     * Converti la chaine de caractere en Object de la<br />
     * class passer en parametre <br />
     * 
     * @param myClass
     * @param data
     * @return Object de la class
     * @throws Throwable
     */
    public synchronized Object convertStringChaineInObject(Class<?> myClass, String data) throws JFFPBException {
        // creation de l'instance
        Object obj = getNewInstanceType(myClass);
        return convertStringChaineInObject(obj, data);

    }

    public synchronized Object convertChaineCsvInObject(Class<?> myClass, String data, String regex) throws JFFPBException {
        return convertChaineCsvInObject(myClass, data, regex, null);
    }

    public synchronized Object convertChaineCsvInObject(Class<?> myClass, String data, String regex, int[] reorganisation) throws JFFPBException {
        Object obj = getNewInstanceType(myClass);
        return convertChaineCsvInObject(obj, data, regex, reorganisation);
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
    public synchronized Object convertChaineCsvInObject(Object obj, String data, String regex) throws JFFPBException {
        return convertChaineCsvInObject(obj, data, regex, null);

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
    public synchronized Object convertChaineCsvInObject(Object obj, String data, String regex, int[] reorganisation) throws JFFPBException {
        boolean reorg = reorganisation != null ? true : false;
        List<String> listCSV = StringUtils.splitinList(regex, data);

        if (reorg) {
            if (reorganisation.length != listCSV.size()) {
                throw new JFFPBException(" Nombre de colone different de la reorganosation ");
            }
        }

        Map<Integer, FieldCsv> fDligneRoot = new HashMap<Integer, FieldCsv>();
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
            FieldCsv fd = new FieldCsv(field);
            if (fd.getCsvMappingParse() != null) {
                fDligneRoot.put(fd.getCsvMappingParse().offset(), fd);
            }
        }

        if (reorg) {
            for (Integer i = 0; i < reorganisation.length; i++) {
                if (!StringUtils.isBlank(listCSV.get(reorganisation[i]))) {
                    makeInvokeCsvSetMethode(obj, fDligneRoot.get(i + 1), map.get(fDligneRoot.get(i + 1).getNamSetMethode()),
                            listCSV.get(reorganisation[i]));
                }
            }
        } else {
            for (Integer i = 0; i < listCSV.size(); i++) {
                if (!StringUtils.isBlank(listCSV.get(i))) {
                    makeInvokeCsvSetMethode(obj, fDligneRoot.get(i + 1), map.get(fDligneRoot.get(i + 1).getNamSetMethode()), listCSV.get(i));
                }
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

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (fieldPosiotinalAnnot.getPositionnalMappingParse().stripChaine()) {
                valueChaine = StringUtils.strip(valueChaine);
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

}
