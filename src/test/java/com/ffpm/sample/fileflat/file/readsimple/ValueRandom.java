/*
 * Creation : 27 oct. 2017
 */
package com.ffpm.sample.fileflat.file.readsimple;

import javax.flat.bind.JFFPBException;
import javax.flat.bind.annotation.adapter.PositionalDefault;

public class ValueRandom extends PositionalDefault<Integer> {

    @Override
    public Integer getValue() throws JFFPBException {

        return new Integer((int) (9999 * Math.random()));
    }

}
