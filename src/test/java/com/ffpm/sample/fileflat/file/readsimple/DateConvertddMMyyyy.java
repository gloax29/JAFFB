/*
 * Creation : 19 mars 2014
 */
/**
 *
 */
package com.ffpm.sample.fileflat.file.readsimple;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.flat.bind.JFFPBException;
import javax.flat.bind.annotation.adapter.PositionalAdapter;
import javax.flat.bind.utils.StringUtils;

/**
 * 
 * @author Gloax29
 * 
 */
public class DateConvertddMMyyyy extends PositionalAdapter<String, Date> {

	private static SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");

	
	@Override
	public Date unmarshal(String v) {
		Date d = null;

		try {
			if (!StringUtils.isBlank(v)) {
				d = sdf.parse(v);
			}

		} catch (Exception e) {
			d = null;
			System.err.println(e + " est null");
		}

		return d;
	}

	@Override
	public String marshal(Date v) {
		if (v == null) {

			return "        ";

		}
		return sdf.format(v);
	}

}
