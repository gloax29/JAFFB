/*
 * Creation : 12 mars 2018
 */
package com.ffpm.sample.fileflat.file.test;

import javax.flat.bind.JAFFBContext;
import javax.flat.bind.JFFPBException;
import javax.flat.bind.Marshaller;

import com.ffpm.sample.fileflat.file.readsimple.ClassSimpleAvecValuDefault;

public class MainSimpleClassValueDfault {

    public static void main(String[] args) throws JFFPBException {

        JAFFBContext jaffb = JAFFBContext.newInstance();
        Marshaller m = jaffb.createMarshaller();

        for (int i = 0; i < 100000; i++) {
            ClassSimpleAvecValuDefault classsimple = new ClassSimpleAvecValuDefault();
            classsimple.setCrc("" + i);
            // le reste est valoriser par le defaultValue
            System.out.println(m.convertObjectInStringChaine(classsimple, 32));
        }

    }

}
