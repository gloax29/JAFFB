package javax.flat.bind;

import java.io.File;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.flat.bind.annotation.adapter.PositionalDefault;
import javax.flat.bind.annotation.csv.CsvMappingParse;
import javax.flat.bind.annotation.positinal.PositionalControlRegex;
import javax.flat.bind.annotation.positinal.PositionalJavaTypeAdapter;
import javax.flat.bind.annotation.positinal.PositionalMappingParse;
import javax.flat.bind.api.FieldCsv;
import javax.flat.bind.control.ControleInfo;
import javax.flat.bind.make.PositionalMakeAnnotation;
import javax.flat.bind.utils.StringUtils;

import com.flat.internal.runtime.CommunContext;

public abstract class Marshaller extends CommunContext {

    private static final Logger LOG = Logger.getLogger(Marshaller.class.getName());

    /**
     * génére le fichier passé en paramétre,<br />
     * avec les valeurs de l'objet passé en paramétre
     */
    public abstract void marshal(Object object, File fichier) throws JFFPBException;

    /**
     * génére le fichier passé en paramétre,<br />
     * avec les valeurs de l'objet passé en paramétre
     */
    public abstract void marshal(Object object, File fichier, Charset iso) throws JFFPBException;

    /**
     * génére le OutputStream passé en paramétre,<br />
     * avec les valeurs de l'objet passé en paramétre
     */
    public abstract void marshal(Object object, OutputStream out) throws JFFPBException;

    /**
     * génére le OutputStream passé en paramétre,<br />
     * avec les valeurs de l'objet passé en paramétre
     */
    public abstract void marshal(Object object, OutputStream out, Charset is) throws JFFPBException;

    /**
     * génére le fichier passé en paramétre,<br />
     * avec les valeurs de l'objet passé en paramétre
     */
    public abstract void marshal(Object object, File fichier, Boolean carriageReturn) throws JFFPBException;

    /**
     * génére le fichier passé en paramétre,<br />
     * avec les valeurs de l'objet passé en paramétre
     */
    public abstract void marshal(Object object, File fichier, Charset is, Boolean carriageReturn) throws JFFPBException;

    /**
     * génére le OutputStream passé en paramétre,<br />
     * avec les valeurs de l'objet passé en paramétre
     */
    public abstract void marshal(Object object, OutputStream out, Boolean carriageReturn) throws JFFPBException;

    /**
     * génére le OutputStream passé en paramétre,<br />
     * avec les valeurs de l'objet passé en paramétre
     */
    public abstract void marshal(Object object, OutputStream out, Charset is, Boolean carriageReturn) throws JFFPBException;

    /**
     * @param instanceObject
     * @param caractereRemplissage
     * @param longueur
     * @return
     * @throws Exception
     */
    public String convertObjectInStringChaine(Object instanceObject) throws JFFPBException {
        return convertObjectInStringChaine(instanceObject, null, null, new Properties());
    }

    /**
     * @param instanceObject
     * @param caractereRemplissage
     * @param longueur
     * @return
     * @throws Exception
     */
    public String convertObjectInStringChaine(Object instanceObject, Properties desactivat) throws JFFPBException {
        return convertObjectInStringChaine(instanceObject, null, null, desactivat);
    }

    /**
     * @param instanceObject
     * @param caractereRemplissage
     * @param longueur
     * @return
     * @throws Exception
     */
    public String convertObjectInStringChaine(Object instanceObject, Integer longueur, Properties desactivat) throws JFFPBException {
        return convertObjectInStringChaine(instanceObject, caractereRemplissageDefaul, longueur, desactivat);
    }

    /**
     * @param instanceObject
     * @param caractereRemplissage
     * @param longueur
     * @return
     * @throws Exception
     */
    public String convertObjectInStringChaine(Object instanceObject, Character caractereRemplissage, Integer longueur) throws JFFPBException {
        return convertObjectInStringChaine(instanceObject, caractereRemplissage, longueur, new Properties());
    }

    /**
     * @param instanceObject
     * @param longueur
     * @return
     * @throws Exception
     */
    public String convertObjectInStringChaine(Object instanceObject, Integer longueur) throws JFFPBException {
        return convertObjectInStringChaine(instanceObject, caractereRemplissageDefaul, longueur, new Properties());
    }

    /**
     * @param instanceObject
     * @param caractereRemplissage
     * @param longueur
     * @return
     * @throws Exception
     */
    public String convertObjectInStringChaine(Object instanceObject, Character caractereRemplissage, Integer longueur, Properties desactivat)
            throws JFFPBException {

        StringBuffer buffer = null;
        int numberField = 0;
        Field[] fdcp = null;
        fdcp = ControleInfo.retroFields(instanceObject, fdcp, numberField);

        boolean longueurIndiquer = false;
        if (longueur != null && longueur > 0) {
            buffer = new StringBuffer(StringUtils.repeat(caractereRemplissage, longueur));
            longueurIndiquer = true;
        } else {
            buffer = new StringBuffer();
        }
        return makeInvokeWriteMethodes(buffer, instanceObject, fdcp, longueurIndiquer, desactivat);

    }

    /**
     * @param data
     * @param caractereSeparateur
     * @return
     * @throws Exception
     */
    public String convertObjectInChaineCsv(Object data, String caractereSeparateur) throws JFFPBException {

        Map<String, Method> map = new HashMap<String, Method>();

        for (Method method : data.getClass().getSuperclass().getDeclaredMethods()) {

            map.put(method.getName().toLowerCase(), method);

        }

        for (Method method : data.getClass().getDeclaredMethods()) {

            map.put(method.getName().toLowerCase(), method);

        }
        Map<Integer, FieldCsv> fDligneRoot = new HashMap<Integer, FieldCsv>();

        for (Field field : data.getClass().getSuperclass().getDeclaredFields()) {
            FieldCsv fd = new FieldCsv(field);
            if (fd.getCsvMappingParse() != null) {
                fDligneRoot.put(fd.getCsvMappingParse().offset(), fd);
            }
        }

        for (Field field : data.getClass().getDeclaredFields()) {
            FieldCsv fd = new FieldCsv(field);
            if (fd.getCsvMappingParse() != null) {
                fDligneRoot.put(fd.getCsvMappingParse().offset(), fd);
            }
        }

        StringBuffer buffer = new StringBuffer();
        List<Integer> keyListField = new ArrayList<Integer>(fDligneRoot.keySet());

        Collections.sort(keyListField);

        for (Integer entryfdLigneRoot : keyListField) {
            buffer.append(caractereSeparateur);
            buffer = (StringBuffer) makeInvokeCsvGetMethode(buffer, data, fDligneRoot.get(entryfdLigneRoot).getField(), caractereSeparateur);

        }

        if (caractereSeparateur.equals(buffer.subSequence(0, caractereSeparateur.length()))) {

            buffer.delete(0, caractereSeparateur.length());

        }

        return buffer.toString();
    }

    protected String makeInvokeWriteMethodes(StringBuffer buffer, Object ob, Field[] fd, boolean longeurIndiquer, Properties desactivat)
            throws JFFPBException {

        return makeInvokeGetMethodes(buffer, ob, fd, longeurIndiquer, desactivat).toString();
    }

    /**
     * @param ob
     * @param map
     * @param obMake
     * @param field
     * @return
     * @throws Exception
     */
    private Object makeInvokeGetMethodes(StringBuffer buffer, Object ob, Field[] fd, boolean longeurIndiquer, Properties desactivat)
            throws JFFPBException {
        Object obInvoke = null;
        Set<Object> setDesactivate = desactivat.keySet();
        boolean desactivateAttrib = setDesactivate.size() == 0 ? false : true;
        int longueurChaine = 0;
        int moduloDep = 0;
        int ifDesactiRecalc = 0;
        Map<String, Integer> mapForVariant = new HashMap<String, Integer>();

        for (Field field : fd) {

            PositionalMappingParse positionnalMappingParce = PositionalMakeAnnotation.getFieldPositionnalMappingParse(field);
            // si il n'y a pas d'annotation ou si désactiver pour l'ecriture
            if (positionnalMappingParce == null || positionnalMappingParce.desactivateOut()) {
                continue;
            }
            if (desactivateAttrib && setDesactivate.contains(field.getName())) {
                if (!longeurIndiquer) {
                    // int departremove = positionnalMappingParce.offset() - 1;
                    int longueurChainedesac = positionnalMappingParce.length();
                    ifDesactiRecalc += longueurChainedesac;
                }

                continue;
            }

            field.setAccessible(true);

            Object valu;
            try {
                valu = field.get(ob);
            } catch (IllegalArgumentException e) {
                throw new JFFPBException(e);
            } catch (IllegalAccessException e) {
                throw new JFFPBException(e);
            }
            int depart = 0;
            if (longeurIndiquer) {
                depart = ((mapForVariant.containsKey(field.getName()) ? mapForVariant.get(field.getName()) : positionnalMappingParce.offset()) - 1)
                        - ifDesactiRecalc;
                longueurChaine = positionnalMappingParce.length();
            } else {
                depart = buffer.length();
            }

            // il prime sur positionnalMappingParce
            PositionalJavaTypeAdapter positionnalJavaTypeAdapter = PositionalMakeAnnotation.getFielPositionnalJavaTypeAdapter(field);

            if (positionnalJavaTypeAdapter != null) {
                if (valu == null && !positionnalJavaTypeAdapter.DefaultValue().equals(PositionalDefault.class)) {
                    valu = PositionalMakeAnnotation.typeAdapterDefault(positionnalJavaTypeAdapter.DefaultValue());
                }

            }

            if (valu == null && !"".equals(positionnalMappingParce.DefaultValue())) {
                valu = positionnalMappingParce.DefaultValue();
            }

            if (valu != null) {
                if (positionnalJavaTypeAdapter != null) {
                    obInvoke = PositionalMakeAnnotation.typeAdapter(valu, positionnalJavaTypeAdapter.value(), PARSING);

                } else {
                    if (positionnalMappingParce.stripChaine()) {
                        obInvoke = StringUtils.strip(valu.toString());
                    } else {
                        obInvoke = valu.toString();
                    }
                }
                PositionalControlRegex positionnalControlRegex = PositionalMakeAnnotation.getFielPositionnalControlRegex(field);
                if (positionnalControlRegex != null) {
                    // si la ligne est conform
                    if (!Pattern.compile(positionnalControlRegex.expression()).matcher(obInvoke.toString()).find()) {
                        if (positionnalMappingParce.required()) {

                            throw new JFFPBException("Non conformité de la chaine de caracter ! '" + obInvoke.toString() + "' pour l'expression "
                                    + positionnalControlRegex.expression());
                        }
                        getAfficheLog(
                                "value Non conforme ==> " + obInvoke.toString() + " pour l'expression ==> " + positionnalControlRegex.expression());
                    }
                }
                if (positionnalMappingParce.variant()) {

                    int valueVariant = positionnalMappingParce.length() - String.valueOf(obInvoke).length();
                    int posi = positionnalMappingParce.offset();

                    for (Field f : fd) {
                        PositionalMappingParse po = PositionalMakeAnnotation.getFieldPositionnalMappingParse(f);
                        if (po.offset() > posi) {

                            mapForVariant.put(f.getName(), po.offset() - valueVariant);
                        }

                    }

                }
                if (positionnalMappingParce.padding() == -1) { // top
                    StringBuffer buf = null;
                    moduloDep = (longueurChaine - obInvoke.toString().length()) / 2;
                    if (moduloDep < 0) {
                        moduloDep = 0;
                    }
                    if (longueurChaine == 0) {
                        longueurChaine = obInvoke.toString().length();
                    }
                    buf = new StringBuffer(StringUtils.repeat(positionnalMappingParce.charcatereRepliForPadding(), moduloDep));
                    buf.append(obInvoke.toString());
                    buf.append(StringUtils.repeat(positionnalMappingParce.charcatereRepliForPadding(), moduloDep));
                    buffer.replace(depart, depart + longueurChaine, buf.toString().substring(0, longueurChaine));

                } else if (positionnalMappingParce.padding() == 1) {// right
                    StringBuilder buf = null;
                    buf = new StringBuilder(StringUtils.repeat(positionnalMappingParce.charcatereRepliForPadding(), longueurChaine));
                    buf.append(obInvoke.toString());
                    int calcCut = buf.length() - longueurChaine;
                    buffer.replace(depart, depart + longueurChaine, buf.toString().substring(calcCut, longueurChaine + calcCut));

                } else {// 0 left
                    buffer.replace(depart, depart + obInvoke.toString().length(), obInvoke.toString());
                }

            }
        }

        return buffer;

    }

    private Object makeInvokeCsvGetMethode(StringBuffer buffer, Object ob, Field field, String caractereSeparateur) throws JFFPBException {

        Object obInvoke = null;
        CsvMappingParse csvMappingParse = PositionalMakeAnnotation.getFieldCsvMappingParse(field);
        if (csvMappingParse != null) {

            field.setAccessible(true);
            Object Valu = null;
            try {
                Valu = field.get(ob);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new JFFPBException(e);
            }

            PositionalJavaTypeAdapter positionnalJavaTypeAdapter = PositionalMakeAnnotation.getFielPositionnalJavaTypeAdapter(field);
            if (positionnalJavaTypeAdapter != null) {

                if (Valu == null && !positionnalJavaTypeAdapter.DefaultValue().equals(PositionalDefault.class)) {

                    obInvoke = PositionalMakeAnnotation.typeAdapterDefault(positionnalJavaTypeAdapter.DefaultValue());
                }

            }

            if (Valu != null) {
                if (!(Valu instanceof List)) {

                    if (positionnalJavaTypeAdapter != null) {
                        obInvoke = PositionalMakeAnnotation.typeAdapter(Valu, positionnalJavaTypeAdapter.value(), PARSING);

                    } else {

                        obInvoke = Valu.toString();
                        if (csvMappingParse.stripChaine()) {
                            obInvoke = StringUtils.strip(Valu.toString());
                        }

                    }
                    PositionalControlRegex positionnalControlRegex = PositionalMakeAnnotation.getFielPositionnalControlRegex(field);
                    if (positionnalControlRegex != null) {
                        // si la ligne est conform
                        if (!Pattern.compile(positionnalControlRegex.expression()).matcher(obInvoke.toString()).find()) {
                            if (csvMappingParse.required()) {

                                throw new JFFPBException("Non conformité de la chaine de caracter ! '" + obInvoke.toString() + "' pour l'expression "
                                        + positionnalControlRegex.expression());
                            }
                            getAfficheLog("value Non conforme ==> " + obInvoke.toString() + " pour l'expression ==> "
                                    + positionnalControlRegex.expression());
                        }
                    }

                    buffer.append(obInvoke.toString());
                } else {

                    @SuppressWarnings("unchecked")
                    List<Object> lists = (List<Object>) Valu;
                    // suprime le derrnier caractereSeparateur car on attaque une list

                    if (lists.size() > 0 && (buffer.length() > 1 || buffer.length() == 1) && buffer.toString().endsWith(caractereSeparateur)) {
                        buffer.delete(buffer.length() - 1, buffer.length());
                    }

                    for (Object object : lists) {

                        buffer.append(caractereSeparateur + convertObjectInChaineCsv(object, caractereSeparateur));

                    }

                }
            }

        }
        return buffer;

    }

    private static void getAfficheLog(String string) {
        LOG.info(string);

    }

}