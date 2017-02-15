/*
 * Creation : 23 mai 2014
 */
/**
 * 
 */
package com.ffpm.sample.fileflat.file.readsimple;

import javax.flat.bind.JFFPBException;
import javax.flat.bind.annotation.adapter.PositionalAdapter;
import javax.flat.bind.utils.StringUtils;

/**
 * 
 * @author Gloax29
 * 
 */
public class ConvertStringTableauString extends PositionalAdapter<String, String[]> {

	@Override
	public String[] unmarshal(String v) throws JFFPBException {
		String[] tableString = v.split(" ");
		return tableString;
	}

	@Override
	public String marshal(String[] v) throws JFFPBException {
		StringBuffer buffer = new StringBuffer();

		for (String val : v) {
			if (StringUtils.isBlank(val)) {

				buffer.append(" "+"      ");

			} else {
				buffer.append(" "+val.toString());

			}
		}

		return buffer.toString().replaceFirst(" ", "");

	}

}
