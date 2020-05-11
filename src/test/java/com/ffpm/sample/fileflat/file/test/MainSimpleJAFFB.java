/**
 * 
 */
package com.ffpm.sample.fileflat.file.test;

import java.io.File;
import java.util.Properties;

import javax.flat.bind.JAFFBContext;
import javax.flat.bind.Marshaller;
import javax.flat.bind.Unmarshaller;

import com.ffpm.sample.fileflat.file.readsimple.FileRootForFFPMSimple;

/**
 * @author e446027
 */
public class MainSimpleJAFFB {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        /**
         * lecture et creation d'un object
         */
        JAFFBContext contex = JAFFBContext.newInstance(FileRootForFFPMSimple.class);
        Unmarshaller unmarshaller = contex.createUnmarshaller();

        FileRootForFFPMSimple ffpmSimple = (FileRootForFFPMSimple) unmarshaller
                .unmarshal(new File("src/test/resources/TestSimpleFileflat.txt"));

        System.out.println(ffpmSimple.toString());

        /**
         * ecriture d'un fichier a partir de l'object
         */

        File fileReecrir = new File("src/test/resources/TestecrirNormanlFileflat.txt");

        Marshaller marshaller = JAFFBContext.newInstance().createMarshaller();

        marshaller.marshal(ffpmSimple, fileReecrir);

        /**
         * ecriture d'un fichier a partir d'un object en utilisent le desactivation de certain attribut
         */

        File fileDesactivat = new File("src/test/resources/TestecrirDesactivatFileflat.txt");

        marshaller = JAFFBContext.newInstance().createMarshaller();

        Properties desactivate = new Properties();
        desactivate.put("refference", "refference");
        desactivate.put("valueRef", "valueRef");
        desactivate.put("idLigne", "idLigne");

        marshaller.setDesactivat(desactivate);
        marshaller.marshal(ffpmSimple, fileDesactivat);

        // Files.readAllLines(fileDesactivat.toPath()).forEach(s -> System.out.println(s));

    }

}
