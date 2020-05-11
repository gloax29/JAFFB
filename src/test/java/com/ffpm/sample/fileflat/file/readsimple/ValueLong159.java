/*
 * Creation : 27 oct. 2017
 */
package com.ffpm.sample.fileflat.file.readsimple;

import javax.flat.bind.JFFPBException;
import javax.flat.bind.annotation.adapter.PositionalDefault;

public class ValueLong159 extends PositionalDefault<Integer> {

    @Override
    public Integer getValue() throws JFFPBException {

        return new Integer(159);
    }

}
