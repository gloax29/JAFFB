/**
 * 
 */
package com.flat.internal.marshaller;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Logger;

import javax.flat.bind.JFFPBException;
import javax.flat.bind.Marshaller;
import javax.flat.bind.api.FormatRootElem;
import javax.flat.bind.make.PositionalMakeRootElem;
import javax.flat.bind.utils.StringUtils;

/**
 * @author root
 */
public class Marshallerimp extends Marshaller {
    private static final Logger LOG = Logger.getLogger(Marshallerimp.class.getName());

    @SuppressWarnings("resource")
    private void fileRunWrite(File fichier) throws JFFPBException {

        initVar();
        String encoding = new InputStreamReader(System.in).getEncoding();
        PrintWriter creerMonFichier = null;
        try {
            creerMonFichier = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichier), encoding)), true);

            String chaineForFile = null;
            StringBuffer buffer = null;
            boolean longeurIndiquer = true;

            for (PositionalMakeRootElem iterableElement : this.positionalMakeRootElems) {

                FormatRootElem formatRoot = iterableElement.getFormatRootElem();
                Field[] fdcp = formatRoot.getForClass().getDeclaredFields();
                iterableElement.getField().setAccessible(true);

                int longueur = formatRoot.getValuLongueurChaine() < 0 ? 0 : formatRoot.getValuLongueurChaine();
                longeurIndiquer = (longueur != 0);
                buffer = new StringBuffer(StringUtils.repeat(formatRoot.getCharRemplissage(), longueur));

                if (formatRoot.isIslist()) {

                    @SuppressWarnings("unchecked")
                    List<Object> list = (List<Object>) iterableElement.getField().get(this.object);
                    for (Object objectdelaList : list) {

                        chaineForFile = makeInvokeWriteMethodes(buffer, objectdelaList, fdcp, longeurIndiquer, this.desactivat);
                        creerMonFichier.println(chaineForFile);
                        buffer = new StringBuffer(StringUtils.repeat(formatRoot.getCharRemplissage(), longueur));
                    }
                } else {

                    chaineForFile = makeInvokeWriteMethodes(buffer, iterableElement.getField().get(this.object), fdcp, longeurIndiquer,
                            this.desactivat);
                    creerMonFichier.println(chaineForFile);

                }

            }
            creerMonFichier.flush();
            creerMonFichier.close();
        } catch (IllegalAccessException e) {
            throw new JFFPBException(e);
        } catch (UnsupportedEncodingException e) {
            throw new JFFPBException(e);
        } catch (FileNotFoundException e) {
            throw new JFFPBException(e);
        }
    }

    @Override
    public void marshal(Object object, File fichier) throws JFFPBException {
        if (object == null) {
            throw new JFFPBException("Object null !");
        }
        valoriseObject(object);

        fileRunWrite(fichier);
    }

    private void valoriseObject(Object object) {
        this.object = object;
        setObjClass(object.getClass());

    }

    @Override
    public void marshal(Object object, OutputStream out) throws JFFPBException {
        if (object == null) {
            throw new JFFPBException("Object null !");
        }
        valoriseObject(object);
        fileRunWrite(out);

    }

    @Override
    public void marshal(Object object, File fichier, Boolean carriageReturn) throws JFFPBException {
        if (object == null) {
            throw new JFFPBException("Object null !");
        }
        this.carriageReturn = carriageReturn;
        valoriseObject(object);
        fileRunWrite(fichier);
    }

    @Override
    public void marshal(Object object, OutputStream out, Boolean carriageReturn) throws JFFPBException {
        if (object == null) {
            throw new JFFPBException("Object null !");
        }
        this.carriageReturn = carriageReturn;
        valoriseObject(object);
        fileRunWrite(out);

    }

    private void fileRunWrite(OutputStream out) throws JFFPBException {

        initVar();

        String chaineForFile = null;
        StringBuffer buffer = null;
        boolean longeurIndiquer = true;

        byte[] bufferData = new byte[1024];
        int read = 0;
        InputStream in = null;
        try {
            for (PositionalMakeRootElem itErableElement : this.positionalMakeRootElems) {

                FormatRootElem formatRoot = itErableElement.getFormatRootElem();
                Field[] fd_cp = formatRoot.getForClass().getDeclaredFields();
                itErableElement.getField().setAccessible(true);

                int longueur = formatRoot.getValuLongueurChaine() < 0 ? 0 : formatRoot.getValuLongueurChaine();
                longeurIndiquer = (longueur != 0);
                buffer = new StringBuffer(StringUtils.repeat(formatRoot.getCharRemplissage(), longueur));

                if (formatRoot.isIslist()) {

                    List<Object> list;

                    list = (List<Object>) itErableElement.getField().get(this.object);

                    for (Object objectdelaList : list) {

                        chaineForFile = makeInvokeWriteMethodes(buffer, objectdelaList, fd_cp, longeurIndiquer, this.desactivat);
                        in = new ByteArrayInputStream(chaineForFile.getBytes());

                        while ((read = in.read(bufferData)) != -1) {
                            out.write(bufferData, 0, read);
                        }
                        if (this.carriageReturn) {
                            out.write(BR.getBytes());
                        }
                        in.close();
                        buffer = new StringBuffer(StringUtils.repeat(formatRoot.getCharRemplissage(), longueur));
                    }
                } else {

                    chaineForFile = makeInvokeWriteMethodes(buffer, itErableElement.getField().get(this.object), fd_cp, longeurIndiquer,
                            this.desactivat);

                    in = new ByteArrayInputStream(chaineForFile.getBytes());

                    while ((read = in.read(bufferData)) != -1) {
                        out.write(bufferData, 0, read);
                    }
                    if (this.carriageReturn) {
                        out.write(BR.getBytes());
                    }
                    in.close();

                }

            }
            out.flush();
            out.close();
        } catch (IllegalArgumentException e) {
            throw new JFFPBException(e);
        } catch (IllegalAccessException e) {
            throw new JFFPBException(e);
        } catch (IOException e) {
            throw new JFFPBException(e);
        }
    }

}
