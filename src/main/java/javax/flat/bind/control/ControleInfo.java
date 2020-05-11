package javax.flat.bind.control;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.flat.bind.JFFPBException;
import javax.flat.bind.api.FieldPositional;
import javax.flat.bind.api.FormatRootElem;
import javax.flat.bind.make.PositionalMakeRootElem;
import javax.flat.bind.utils.StringUtils;

public class ControleInfo {
    private static String VALUEEXPRETION = "-X";

    private ControleInfo() {
    }

    public static boolean controleDebutLigne(List<PositionalMakeRootElem> positionalMakeRootElems) {
        List<Integer> lis = new ArrayList<Integer>();
        Set<Integer> setComp = new HashSet<Integer>();
        for (PositionalMakeRootElem element2 : positionalMakeRootElems) {
            lis.add(element2.getFormatRootElem().getStartRowsIterationLigne());
            setComp.add(element2.getFormatRootElem().getStartRowsIterationLigne());
        }

        if (lis.size() != setComp.size()) {
            return true;
        }

        return false;
    }

    public static Map<String, Method> creatMapClefMethode(PositionalMakeRootElem element) throws JFFPBException {
        Map<String, Method> map = new HashMap<String, Method>();

        Method[] declaredMethods = null;
        int numberMethode = 0;

        declaredMethods = retroMethodes(getNewInstanceType(element.getFormatRootElem().getForClass()), declaredMethods, numberMethode);

        for (Method method : declaredMethods) {

            map.put(method.getName().toLowerCase(), method);

        }

        return map;
    }

    public static List<FieldPositional> createListFiel(PositionalMakeRootElem element) throws JFFPBException {
        List<FieldPositional> fdLigneRoot = new ArrayList<FieldPositional>();
        Field[] fdcp = null;
        int numberField = 0;

        try {
            fdcp = retroFields(element.getFormatRootElem().getForClass().newInstance(), fdcp, numberField);
        } catch (InstantiationException e) {

            e.printStackTrace();
        } catch (IllegalAccessException e) {

            e.printStackTrace();
        }

        // for (Field field : element.getFormatRootElem().getForClass().getDeclaredFields()) {
        for (Field field : fdcp) {
            fdLigneRoot.add(new FieldPositional(field));

        }
        return fdLigneRoot;
    }

    public static boolean ifExpretion(List<PositionalMakeRootElem> positionalMakeRootElems) throws JFFPBException {
        boolean valable = false;

        for (PositionalMakeRootElem element : positionalMakeRootElems) {

            if (!VALUEEXPRETION.equals(element.getFormatRootElem().getExpression())) {

                valable = true;

            }
            if (StringUtils.isBlank(element.getFormatRootElem().getExpression())) {

                throw new JFFPBException("Toutes les annotations Expression doivent étre indiquées. Si pas d'expression alors  NOEXPRES"
                        + " doit étre indiqué et ne peut étre utilisé qu'une seule fois.");

            }
        }
        return valable;
    }

    /**
     * @param forClass
     * @return
     * @throws Exception
     */
    private static Object getNewInstanceType(Class<?> forClass) throws JFFPBException {
        try {
            return forClass.newInstance();
        } catch (InstantiationException e) {
            throw new JFFPBException(e);
        } catch (IllegalAccessException e) {
            throw new JFFPBException(e);
        }
    }

    public static boolean ifConform(FormatRootElem argformatRoot, String argligne) {
        return (argformatRoot.getValuLongueurChaine() != -1 && (argformatRoot.getValuLongueurChaine() != argligne.length())) ? true : false;

    }

    public static boolean ifContinu(FormatRootElem argformatRoot, Integer argitElement) {
        return (argformatRoot.getNumbersRowsIterator() == -1 ? true
                : (argformatRoot.getStartRowsIterationLigne() + argformatRoot.getNumbersRowsIterator()) > argitElement);

    }

    public static boolean ifTraitementList(FormatRootElem argformatRoot, Integer argitElement) {
        return (argformatRoot.isIslist() && (argformatRoot.getStartRowsIterationLigne().equals(argitElement))
                && ((argformatRoot.getNumbersRowsIterator() == -1 ? true
                        : (argformatRoot.getStartRowsIterationLigne() + argformatRoot.getNumbersRowsIterator()) > argitElement))) ? true : false;

    }

    public static Map<String, Method> creatMatForMethode(FormatRootElem argFormatRoot) throws SecurityException, JFFPBException {
        Map<String, Method> rtnmap = new HashMap<String, Method>();

        Method[] declaredMethods = null;
        int numberMethode = 0;

        try {
            declaredMethods = retroMethodes(argFormatRoot.getForClass().newInstance(), declaredMethods, numberMethode);
        } catch (InstantiationException e) {

            e.printStackTrace();
        } catch (IllegalAccessException e) {

            e.printStackTrace();
        }

        // for (Method method : getNewInstanceType(argFormatRoot.getForClass()).getClass().getDeclaredMethods()) {
        for (Method method : declaredMethods) {
            rtnmap.put(method.getName().toLowerCase(), method);

        }
        return rtnmap;
    }

    public static List<FieldPositional> creatListFieldPositional(Object argobj) throws JFFPBException {
        List<FieldPositional> rtnfdLigneRoot = new ArrayList<FieldPositional>();
        int numberField = 0;
        Field[] fdcp = null;
        fdcp = retroFields(argobj, fdcp, numberField);

        for (Field field : fdcp) {

            rtnfdLigneRoot.add(new FieldPositional(field));

        }

        return rtnfdLigneRoot;
    }

    public static Field[] retroFields(Class instanceObject, Field[] fdcp, int numberField) {
        Object obj = null;
        try {
            obj = instanceObject.newInstance();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return retroFields(obj, fdcp, numberField);
    }

    public static Field[] retroFields(Object instanceObject, Field[] fdcp, int numberField) {

        if (fdcp == null || fdcp.length == 0) {

            fdcp = new Field[instanceObject.getClass().getDeclaredFields().length];

        } else {

            fdcp = addTableau(fdcp, instanceObject.getClass().getDeclaredFields().length);

        }

        for (int i = 0; i < instanceObject.getClass().getDeclaredFields().length; i++) {

            fdcp[numberField++] = instanceObject.getClass().getDeclaredFields()[i];

        }

        if (!instanceObject.getClass().getSuperclass().getName().equals(Object.class.getName())
                && !Modifier.isAbstract(instanceObject.getClass().getSuperclass().getModifiers())
                && !Modifier.isInterface(instanceObject.getClass().getSuperclass().getModifiers())) {

            try {
                fdcp = retroFields(((Class<?>) instanceObject.getClass().getSuperclass()).newInstance(), fdcp, numberField);
            } catch (InstantiationException | IllegalAccessException e) {

                e.printStackTrace();
            }

        }

        return fdcp;
    }

    private static Field[] addTableau(Field[] fdcp, int numberField) {

        Field[] fdcpTmp = new Field[fdcp.length + numberField];

        for (int i = 0; i < fdcp.length; i++) {
            fdcpTmp[i] = fdcp[i];
        }

        fdcp = fdcpTmp;
        return fdcp;

    }

    private static Method[] addTableau(Method[] declaredMethods, int numberField) {

        Method[] declaredMethodsTmp = new Method[declaredMethods.length + numberField];

        for (int i = 0; i < declaredMethods.length; i++) {
            declaredMethodsTmp[i] = declaredMethods[i];
        }

        declaredMethods = declaredMethodsTmp;
        return declaredMethods;

    }

    public static Method[] retroMethodes(Object obj, Method[] declaredMethods, int numberMethode) {

        if (declaredMethods == null || declaredMethods.length == 0) {

            declaredMethods = new Method[obj.getClass().getDeclaredMethods().length];

        } else {

            declaredMethods = addTableau(declaredMethods, obj.getClass().getDeclaredMethods().length);

        }

        for (int i = 0; i < obj.getClass().getDeclaredMethods().length; i++) {

            declaredMethods[numberMethode++] = obj.getClass().getDeclaredMethods()[i];

        }

        if (!obj.getClass().getSuperclass().getName().equals(Object.class.getName())
                && !Modifier.isAbstract(obj.getClass().getSuperclass().getModifiers())
                && !Modifier.isInterface(obj.getClass().getSuperclass().getModifiers())) {

            try {
                declaredMethods = retroMethodes(((Class<?>) obj.getClass().getSuperclass()).newInstance(), declaredMethods, numberMethode);
            } catch (InstantiationException | IllegalAccessException e) {

                e.printStackTrace();
            }

        }

        return declaredMethods;
    }

}
