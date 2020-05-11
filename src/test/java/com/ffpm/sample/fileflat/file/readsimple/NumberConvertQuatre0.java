/*
 * Creation : 15 déc. 2016
 */
/**
 * 
 */
package com.ffpm.sample.fileflat.file.readsimple;

import java.text.DecimalFormat;

import javax.flat.bind.JFFPBException;
import javax.flat.bind.annotation.adapter.PositionalAdapter;

/**
 * TODO : Description
 * 
 * @author E501142
 */
public class NumberConvertQuatre0 extends PositionalAdapter<String, Integer> {

    private static DecimalFormat formatter = new DecimalFormat("0000");

    @Override
    public Integer unmarshal(String v) throws JFFPBException {

        try {
            return Integer.valueOf(v);
        } catch (Exception e) {
            System.err.println(v);
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String marshal(Integer v) throws JFFPBException {
        try {
            return formatter.format(v);
        } catch (Exception e) {
            System.err.println(v);
            e.printStackTrace();
        }
        return "0000";
    }

}
