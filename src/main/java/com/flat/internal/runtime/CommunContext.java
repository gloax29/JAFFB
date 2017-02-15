/**
 * 
 */
package com.flat.internal.runtime;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.flat.bind.JFFPBException;
import javax.flat.bind.annotation.positinal.PositionalFormatFile;
import javax.flat.bind.annotation.positinal.PositionalMappingParseRootElem;
import javax.flat.bind.api.FieldPositional;
import javax.flat.bind.api.FormatFile;
import javax.flat.bind.api.FormatRootElem;
import javax.flat.bind.api.PositionalParseExpression;
import javax.flat.bind.control.ControleInfo;
import javax.flat.bind.make.PositionalMakeAnnotation;
import javax.flat.bind.make.PositionalMakeRootElem;

/**
 * @author root
 */
public class CommunContext {
    private static final Logger LOG = Logger.getLogger(CommunContext.class.getName());

    protected boolean passageExecute = false;
    protected static char caractereRemplissageDefaul = ' ';
    protected Properties desactivat;
    protected static final String BR = "\r\n";
    protected boolean carriageReturn = false;
    protected List<PositionalMakeRootElem> positionalMakeRootElems = null;
    protected FormatFile formatFile;
    protected Class<?> clazz;
    protected Object object;
    protected static final String PARSING = "marshal";
    protected static final String UNPARSING = "unmarshal";
    protected static final String NOEXPRES = PositionalMappingParseRootElem.NOEXPRES;
    protected Map<Object, PositionalParseExpression> expressioninject;

    public static final Comparator<FieldPositional> COMPARATEUR_RANG_FIELD = new Comparator<FieldPositional>() {

        @Override
        public int compare(final FieldPositional o1, final FieldPositional o2) {

            return Integer.compare(o1.getPositionnalMappingParse().offset(), o2.getPositionnalMappingParse().offset());
        }
    };

    protected static List<Object> getNewInstanceList() {
        return new ArrayList<Object>();
    }

    protected Object getNewInstance(String name) throws Exception {

        return getNewInstanceType(this.object.getClass().getDeclaredField(name).getType());

    }

    protected static Object getNewInstanceType(Class<?> forClass) throws JFFPBException {
        try {
            return forClass.newInstance();
        } catch (InstantiationException e) {
            throw new JFFPBException(e);
        } catch (IllegalAccessException e) {
            throw new JFFPBException(e);
        }
    }

    protected void setObjClass(Class<?> objClass) {
        this.clazz = objClass;

    }

    protected void initVar() throws JFFPBException {

        if (this.desactivat == null) {
            this.desactivat = new Properties();
        }
        formatFileInfoClass(this.clazz);
        Field[] entTab = getNewInstanceType(this.clazz).getClass().getDeclaredFields();

        if (entTab.length < 1) {
            throw new JFFPBException("annotation PositionnalFormatRootElement manquante !");
        }

        this.positionalMakeRootElems = new ArrayList<PositionalMakeRootElem>(entTab.length);

        for (Field ent : entTab) {

            PositionalMappingParseRootElem inti = PositionalMakeAnnotation.getFieldPositionalMappingParseRootElem(ent);
            if (inti != null) {
                FormatRootElem format = PositionalMakeAnnotation.formatInfoClassRootElem(inti);
                PositionalMakeRootElem makeRootElem = new PositionalMakeRootElem(ent, format);
                this.positionalMakeRootElems.add(makeRootElem);
            }
        }

        Collections.sort(this.positionalMakeRootElems, new Comparator<Object>() {

            public int compare(Object o1, Object o2) {
                Integer pO1 = ((PositionalMakeRootElem) o1).getFormatRootElem().getStartRowsIterationLigne();
                Integer pO2 = ((PositionalMakeRootElem) o2).getFormatRootElem().getStartRowsIterationLigne();
                return pO1.compareTo(pO2);
            }
        });

        controlPositionalMakeRootElems();
    }

    private void formatFileInfoClass(Class<?> clazz) throws JFFPBException {

        PositionalFormatFile data = PositionalMakeAnnotation.getPositionnalFormatFile(clazz);
        if (data == null) {
            throw new JFFPBException("annotation PositionnalFormatFile manquante !");
        }
        this.formatFile = new FormatFile();
        this.formatFile.setName(data.name());

    }

    private void controlPositionalMakeRootElems() throws JFFPBException {
        //

        // parcours de root element pour determiner l'expression

        if (ControleInfo.ifExpretion(this.positionalMakeRootElems)) {

            for (PositionalMakeRootElem element : this.positionalMakeRootElems) {
                List<FieldPositional> fdLigneRoot = ControleInfo.createListFiel(element);

                Map<String, Method> map = ControleInfo.creatMapClefMethode(element);

                if (this.expressioninject == null) {
                    this.expressioninject = new HashMap<Object, PositionalParseExpression>();
                }
                Pattern patern = Pattern.compile(element.getFormatRootElem().getExpression());

                if (element.getFormatRootElem().isIslist()) {

                    this.expressioninject.put(patern, new PositionalParseExpression(getNewInstanceList(), fdLigneRoot, map, element));
                } else {
                    this.expressioninject.put(patern,
                            new PositionalParseExpression(getNewInstanceType(element.getFormatRootElem().getForClass()), fdLigneRoot, map, element));

                }
            }

        } else {

            if (ControleInfo.controleDebutLigne(this.positionalMakeRootElems)) {

                throw new JFFPBException("il ne peut y avoir de debut de ligne identique !  ");
            }
        }

    }

    /**
     * @return the desactivat
     */
    public Properties getDesactivat() {
        if (this.desactivat == null) {
            this.desactivat = new Properties();
        }
        return this.desactivat;
    }

    /**
     * @param desactivat the desactivat to set
     */
    public void setDesactivat(Properties desactivat) {
        this.desactivat = desactivat;
    }

}
