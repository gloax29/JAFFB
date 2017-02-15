/*
 * Creation : 23 juin 2014
 */
/**
 * 
 */
package com.ffpm.sample.fileflat.file.readsimple;

import java.text.DecimalFormat;

import javax.flat.bind.JFFPBException;
import javax.flat.bind.annotation.adapter.PositionalAdapter;
import javax.flat.bind.utils.StringUtils;

/**
 * 
 * @author E446027
 * 
 */
public class NumberConvertDix0 extends PositionalAdapter<String, Integer> {

	private static DecimalFormat formatter = new DecimalFormat("0000000000");

	@Override
	public Integer unmarshal(String v) throws JFFPBException {
		try {
			return Integer.valueOf(StringUtils.strip(v));
		} catch (Exception e) {
			throw new JFFPBException(v+" "+e.getMessage());
		}
	}

	@Override
	public String marshal(Integer v) throws JFFPBException {
		return formatter.format(v);
	}

}
