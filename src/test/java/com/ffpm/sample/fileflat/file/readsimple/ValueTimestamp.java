/*
 * Creation : 27 oct. 2017
 */
/**
 * 
 */
package com.ffpm.sample.fileflat.file.readsimple;

import java.sql.Timestamp;
import java.util.Date;

import javax.flat.bind.JFFPBException;
import javax.flat.bind.annotation.adapter.PositionalDefault;

/**
 * TODO : Description
 * 
 * @author E501142
 */
public class ValueTimestamp extends PositionalDefault<Timestamp> {

    @Override
    public Timestamp getValue() throws JFFPBException {

        return new Timestamp(new Date().getTime());
    }

}
