/**
 * 
 */
package com.flat.internal.unmarshaller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.flat.bind.JFFPBException;
import javax.flat.bind.Unmarshaller;
import javax.flat.bind.api.FieldPositional;
import javax.flat.bind.api.FormatRootElem;
import javax.flat.bind.api.PositionalParseExpression;
import javax.flat.bind.control.ControleInfo;
import javax.flat.bind.make.PositionalMakeRootElem;
import javax.flat.bind.utils.StringUtils;

/**
 * @author root
 */
public class Unmarshallerimp extends Unmarshaller {

    private static final Logger LOG = Logger.getLogger(Unmarshallerimp.class.getName());

    public Unmarshallerimp(Class<?> clazz) throws JFFPBException {
        setObjClass(clazz);
        try {
            // annulation de la desactivation
            this.desactivat = new Properties();
            LOG.setLevel(Level.SEVERE);
            initVar();

        } catch (Exception e) {
            throw new JFFPBException(e);
        }
    }

    public Unmarshallerimp() throws JFFPBException {

    }

    @Override
    public Object unmarshal(File file) throws JFFPBException {
        if (!file.exists()) {
            throw new JFFPBException("Fichier n'existe pas ! " + file.getAbsolutePath() + " path ==> " + new File("file").getAbsolutePath());
        }
        return fileRunReadFile(file, Charset.defaultCharset());
    }

    @Override
    public Object unmarshal(InputStream inputStreamFile) throws JFFPBException {
        if (inputStreamFile == null) {
            throw new JFFPBException("InputStream null");
        }
        return fileRunRead(inputStreamFile, Charset.defaultCharset());
    }

    @Override
    public Object unmarshal(InputStream inputStream, Charset iso) throws JFFPBException {
        if (inputStream == null) {
            throw new JFFPBException("InputStream null");
        }
        return fileRunRead(inputStream, iso);
    }

    public Object fileRunReadFile(File file, Charset charset) throws JFFPBException {

        try {
            // return fileRunRead(Files.readAllLines(file.toPath()));
            return fileRunRead(new FileInputStream(file), charset);
        } catch (Exception e) {
            throw new JFFPBException(e);
        }

    }

    /**
     * @param inputStreamFile
     * @return
     * @throws JFFPBException
     */
    private Object fileRunRead(InputStream inputStreamFile, Charset iso) throws JFFPBException {
        try {
            this.object = this.clazz.newInstance();
        } catch (InstantiationException e1) {
            throw new JFFPBException(e1);
        } catch (IllegalAccessException e2) {
            throw new JFFPBException(e2);
        }
        if (this.passageExecute) {
            setObjClass(this.clazz);
            initVar();
        }
        this.passageExecute = true;
        Integer itElement = new Integer(1);
        List<FieldPositional> fdLigneRoot = null;
        Map<String, Method> map = null;
        String ligne = null;
        List<Object> obList = null;
        Object obLigneRoot = null;
        Object obj = null;
        Set<Object> keyExpre = null;
        Pattern pattern = null;
        try (BufferedReader fichierRead = new BufferedReader(new InputStreamReader(inputStreamFile, iso))) {
            // lecture de fichier
            if (this.expressioninject == null) {

                // lecture de mes éléments root
                // pilotage du mapping par positionalMakeRootElems
                for (PositionalMakeRootElem itErableElement : this.positionalMakeRootElems) {

                    FormatRootElem formatRoot = itErableElement.getFormatRootElem();
                    if (formatRoot.isIslist()) {
                        obList = getNewInstanceList();
                    }
                    obj = getNewInstanceType(formatRoot.getForClass());

                    fdLigneRoot = ControleInfo.creatListFieldPositional(obj);
                    Collections.sort(fdLigneRoot, COMPARATEUR_RANG_FIELD);
                    // correction de valeur des offset avec laste

                    map = ControleInfo.creatMatForMethode(formatRoot);

                    ligne = fichierRead.readLine();
                    while (StringUtils.isNotBlank(ligne)) {
                        correctionDeValeurDuAuLast(fdLigneRoot, ligne);
                        if (!formatRoot.isIslist() && (formatRoot.getStartRowsIterationLigne().equals(itElement))) {

                            if (formatRoot.getValuLongueurChaine() != -1 && (formatRoot.getValuLongueurChaine() != ligne.length())) {
                                throw new JFFPBException("Non conformité de la chaine de caracter Longueur Differente!"
                                        + formatRoot.getValuLongueurChaine() + " demandée pour " + ligne.length() + " reel");
                            }
                            for (FieldPositional field : fdLigneRoot) {
                                // passage par chaque élément pour injecter
                                // information.
                                obj = makeInvokeSetMethodes(obj, field, map.get(field.getNamSetMethode().toLowerCase()), ligne);
                                // on s'arrette car parcing false
                                if (field.getPositionnalJavaTypeAdapter() != null && !field.getPositionnalJavaTypeAdapter().parcing()) {
                                    break;
                                }
                            }

                            // methode invoke
                            this.object.getClass()
                                    .getDeclaredMethod(itErableElement.getSetteurMethode(), new Class[] { itErableElement.getField().getType() })
                                    .invoke(this.object, obj);
                            itElement++;
                            break;

                        } else if (ControleInfo.ifTraitementList(formatRoot, itElement)) {

                            boolean quiteList = true;

                            if (ControleInfo.ifConform(formatRoot, ligne)) {
                                throw new JFFPBException("Non conformité de la chaine de caractères entete Longueur Differente!"
                                        + formatRoot.getValuLongueurChaine() + " demandée pour " + ligne.length() + " reel");
                            }

                            obLigneRoot = getNewInstanceType(formatRoot.getForClass());

                            do {

                                obLigneRoot = getNewInstanceType(formatRoot.getForClass());

                                for (FieldPositional field : fdLigneRoot) {

                                    obLigneRoot = makeInvokeSetMethodes(obLigneRoot, field, map.get(field.getNamSetMethode().toLowerCase()), ligne);
                                    // on s'arrette car parcing false
                                    if (field.getPositionnalJavaTypeAdapter() != null && !field.getPositionnalJavaTypeAdapter().parcing()) {
                                        break;
                                    }
                                }
                                obList.add(obLigneRoot);

                                if (!ControleInfo.ifContinu(formatRoot, itElement)) {

                                    this.object.getClass().getDeclaredMethod(itErableElement.getSetteurMethode(),
                                            new Class[] { itErableElement.getField().getType() }).invoke(this.object, obList);

                                    quiteList = false;
                                    break;
                                }

                                itElement++;
                                ligne = fichierRead.readLine();
                            } while (StringUtils.isNotBlank(ligne));

                            if (quiteList) {
                                this.object.getClass()
                                        .getDeclaredMethod(itErableElement.getSetteurMethode(), new Class[] { itErableElement.getField().getType() })
                                        .invoke(this.object, obList);

                            }
                            break;

                        } else {

                            itElement++;
                        }
                        ligne = fichierRead.readLine();
                    }
                }
            } else {

                keyExpre = this.expressioninject.keySet();
                PositionalParseExpression patternExpr = null;
                ligne = fichierRead.readLine();
                // pilotage par la lecture des lignes

                while (StringUtils.isNotBlank(ligne)) {
                    for (Iterator<Object> iterator = keyExpre.iterator(); iterator.hasNext();) {
                        pattern = (Pattern) iterator.next();

                        if (pattern.matcher(ligne).find()) {
                            patternExpr = this.expressioninject.get(pattern);
                            break;
                        }
                        patternExpr = null;
                    }
                    if (patternExpr == null) {

                        for (Iterator<Object> iterator = keyExpre.iterator(); iterator.hasNext();) {
                            pattern = (Pattern) iterator.next();

                            if (NOEXPRES.equals(pattern.pattern())) {

                                patternExpr = this.expressioninject.get(pattern);
                                break;

                            }

                        }
                        // passe à la prochaine ligne
                        if (patternExpr == null) {

                            continue;
                        }
                    }
                    // contréle de la longueur
                    if (patternExpr.getElement().getFormatRootElem().getValuLongueurChaine() != -1
                            && (patternExpr.getElement().getFormatRootElem().getValuLongueurChaine() != ligne.length())) {
                        throw new JFFPBException("Non conformité de la chaine de caracter entete Longueur Differente! "
                                + patternExpr.getElement().getFormatRootElem().getValuLongueurChaine() + " demander pour " + ligne.length()
                                + " reel");
                    }

                    // si ce n'est pas une liste
                    if (!patternExpr.getElement().getFormatRootElem().isIslist()) {

                        for (FieldPositional field : patternExpr.getFd_LigneRoot()) {
                            // passage par chaque élément pour injecter
                            // information.
                            makeInvokeSetMethodes(patternExpr.getNewInstanceType(), field,
                                    patternExpr.getMap().get(field.getNamSetMethode().toLowerCase()), ligne);

                        }

                        itElement++;

                    } else {// si c'est une liste

                        obLigneRoot = getNewInstanceType(patternExpr.getElement().getFormatRootElem().getForClass());

                        for (FieldPositional field : patternExpr.getFd_LigneRoot()) {

                            makeInvokeSetMethodes(obLigneRoot, field, patternExpr.getMap().get(field.getNamSetMethode().toLowerCase()), ligne);

                        }
                        ((List<Object>) patternExpr.getNewInstanceType()).add(obLigneRoot);
                        itElement++;
                    }
                    ligne = fichierRead.readLine();
                }
                for (Object pattern2 : keyExpre) {

                    this.object.getClass()
                            .getDeclaredMethod(this.expressioninject.get(pattern2).getElement().getSetteurMethode(),
                                    new Class[] { this.expressioninject.get(pattern2).getElement().getField().getType() })
                            .invoke(this.object, this.expressioninject.get(pattern2).getNewInstanceType());
                }
            }

        } catch (Exception e) {

            StringBuilder st = new StringBuilder();

            if (ligne != null)
                st.append(" ligne ==> " + ligne);
            if (obj != null)
                st.append(" obj ==> " + obj.toString());
            if (obLigneRoot != null)
                st.append(" obLigneRoot ==> " + obLigneRoot.toString());
            if (this.object != null)
                st.append(" this.object ==> " + this.object.toString());
            if (obList != null)
                st.append(" obList ==> " + obList.toString());

            throw new JFFPBException(" erreur ligne numero : " + (itElement) + " " + st.toString() + "\n" + e);
        } finally {
            fdLigneRoot = null;

        }

        return this.object;
    }

    @SuppressWarnings({ "unchecked", "unused" })
    private Object fileRunRead(List<String> fichier) throws JFFPBException {
        try {
            this.object = this.clazz.newInstance();
        } catch (InstantiationException e1) {
            throw new JFFPBException(e1);
        } catch (IllegalAccessException e2) {
            throw new JFFPBException(e2);
        }
        if (this.passageExecute) {
            setObjClass(this.clazz);
        }

        Iterator<String> fichierRead = fichier.iterator();

        this.passageExecute = true;
        Integer itElement = new Integer(1);
        List<FieldPositional> fdLigneRoot = null;
        Map<String, Method> map = null;
        String ligne = null;
        List<Object> obList = null;
        Object obLigneRoot = null;
        Object obj = null;
        Set<Object> keyExpre = null;
        Pattern pattern = null;
        try {
            if (this.expressioninject == null) {

                // lecture de mes éléments root
                // pilotage du mapping par positionalMakeRootElems
                for (PositionalMakeRootElem itErableElement : this.positionalMakeRootElems) {

                    FormatRootElem formatRoot = itErableElement.getFormatRootElem();
                    if (formatRoot.isIslist()) {

                        obList = getNewInstanceList();

                    }
                    obj = getNewInstanceType(formatRoot.getForClass());
                    fdLigneRoot = ControleInfo.creatListFieldPositional(obj);
                    map = ControleInfo.creatMatForMethode(formatRoot);

                    ligne = (fichierRead.hasNext() ? fichierRead.next() : null);

                    while (StringUtils.isNotBlank(ligne)) {

                        if (!formatRoot.isIslist() && (formatRoot.getStartRowsIterationLigne().equals(itElement))) {

                            if (formatRoot.getValuLongueurChaine() != -1 && (formatRoot.getValuLongueurChaine() != ligne.length())) {
                                throw new JFFPBException("Non conformité de la chaine de caracter Longueur Differente!"
                                        + formatRoot.getValuLongueurChaine() + " demandée pour " + ligne.length() + " reel");
                            }
                            for (FieldPositional field : fdLigneRoot) {
                                // passage par chaque élément pour injecter
                                // information.
                                obj = makeInvokeSetMethodes(obj, field, map.get(field.getNamSetMethode().toLowerCase()), ligne);
                                // on s'arrette car parcing false
                                if (field.getPositionnalJavaTypeAdapter() != null && !field.getPositionnalJavaTypeAdapter().parcing()) {
                                    break;
                                }

                            }

                            // methode invoke
                            this.object.getClass()
                                    .getDeclaredMethod(itErableElement.getSetteurMethode(), new Class[] { itErableElement.getField().getType() })
                                    .invoke(this.object, obj);
                            itElement++;
                            break;

                        } else if (ControleInfo.ifTraitementList(formatRoot, itElement)) {

                            boolean quiteList = true;

                            if (ControleInfo.ifConform(formatRoot, ligne)) {
                                throw new JFFPBException("Non conformité de la chaine de caractères entete Longueur Differente!"
                                        + formatRoot.getValuLongueurChaine() + " demandée pour " + ligne.length() + " reel");
                            }

                            obLigneRoot = getNewInstanceType(formatRoot.getForClass());

                            do {
                                obLigneRoot = getNewInstanceType(formatRoot.getForClass());

                                for (FieldPositional field : fdLigneRoot) {

                                    obLigneRoot = makeInvokeSetMethodes(obLigneRoot, field, map.get(field.getNamSetMethode().toLowerCase()), ligne);
                                    // on s'arrette car parcing false
                                    if (field.getPositionnalJavaTypeAdapter() != null && !field.getPositionnalJavaTypeAdapter().parcing()) {
                                        break;
                                    }

                                }
                                obList.add(obLigneRoot);

                                if (!ControleInfo.ifContinu(formatRoot, itElement)) {

                                    this.object.getClass().getDeclaredMethod(itErableElement.getSetteurMethode(),
                                            new Class[] { itErableElement.getField().getType() }).invoke(this.object, obList);

                                    quiteList = false;
                                    break;
                                }

                                itElement++;

                            } while (((ligne = (fichierRead.hasNext() ? fichierRead.next() : null))) != null);

                            if (quiteList) {
                                this.object.getClass()
                                        .getDeclaredMethod(itErableElement.getSetteurMethode(), new Class[] { itErableElement.getField().getType() })
                                        .invoke(this.object, obList);

                            }
                            break;

                        } else {

                            itElement++;
                        }

                        ligne = (fichierRead.hasNext() ? fichierRead.next() : null);

                    }
                }
            } else {

                keyExpre = this.expressioninject.keySet();
                PositionalParseExpression patternExpr = null;

                // pilotage par la lecture des lignes

                while (((ligne = (fichierRead.hasNext() ? fichierRead.next() : null))) != null) {
                    for (Iterator<Object> iterator = keyExpre.iterator(); iterator.hasNext();) {
                        pattern = (Pattern) iterator.next();

                        if (pattern.matcher(ligne).find()) {
                            patternExpr = this.expressioninject.get(pattern);
                            break;
                        }
                        patternExpr = null;
                    }
                    if (patternExpr == null) {

                        for (Iterator<Object> iterator = keyExpre.iterator(); iterator.hasNext();) {
                            pattern = (Pattern) iterator.next();

                            if (NOEXPRES.equals(pattern.pattern())) {

                                patternExpr = this.expressioninject.get(pattern);
                                break;

                            }

                        }
                        // passe à la prochaine ligne
                        if (patternExpr == null) {

                            continue;
                        }
                    }
                    // contréle de la longueur
                    if (patternExpr.getElement().getFormatRootElem().getValuLongueurChaine() != -1
                            && (patternExpr.getElement().getFormatRootElem().getValuLongueurChaine() != ligne.length())) {
                        throw new JFFPBException("Non conformité de la chaine de caracter entete Longueur Differente! "
                                + patternExpr.getElement().getFormatRootElem().getValuLongueurChaine() + " demander pour " + ligne.length()
                                + " reel");
                    }

                    // si ce n'est pas une liste
                    if (!patternExpr.getElement().getFormatRootElem().isIslist()) {

                        for (FieldPositional field : patternExpr.getFd_LigneRoot()) {
                            // passage par chaque élément pour injecter
                            // information.
                            makeInvokeSetMethodes(patternExpr.getNewInstanceType(), field,
                                    patternExpr.getMap().get(field.getNamSetMethode().toLowerCase()), ligne);

                        }

                        itElement++;

                    } else {// si c'est une liste

                        obLigneRoot = getNewInstanceType(patternExpr.getElement().getFormatRootElem().getForClass());

                        for (FieldPositional field : patternExpr.getFd_LigneRoot()) {

                            makeInvokeSetMethodes(obLigneRoot, field, patternExpr.getMap().get(field.getNamSetMethode().toLowerCase()), ligne);

                        }
                        ((List<Object>) patternExpr.getNewInstanceType()).add(obLigneRoot);
                        itElement++;
                    }

                }
                for (Object pattern2 : keyExpre) {

                    this.object.getClass()
                            .getDeclaredMethod(this.expressioninject.get(pattern2).getElement().getSetteurMethode(),
                                    new Class[] { this.expressioninject.get(pattern2).getElement().getField().getType() })
                            .invoke(this.object, this.expressioninject.get(pattern2).getNewInstanceType());
                }
            }

        } catch (Exception e) {

            StringBuilder st = new StringBuilder();

            if (ligne != null)
                st.append(" ligne ==> " + ligne);
            if (obj != null)
                st.append(" obj ==> " + obj.toString());
            if (obLigneRoot != null)
                st.append(" obLigneRoot ==> " + obLigneRoot.toString());
            if (this.object != null)
                st.append(" this.object ==> " + this.object.toString());
            if (obList != null)
                st.append(" obList ==> " + obList.toString());

            throw new JFFPBException(" erreur ligne numero : " + (itElement) + " " + st.toString() + "\n" + e);
        } finally {
            fdLigneRoot = null;

        }

        return this.object;
    }

}
