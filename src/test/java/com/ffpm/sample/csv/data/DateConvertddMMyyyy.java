/*
 * Creation : 19 mars 2014
 */
/**
 *
 */
package com.ffpm.sample.csv.data;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.flat.bind.JFFPBException;
import javax.flat.bind.annotation.adapter.PositionalAdapter;
import javax.flat.bind.utils.StringUtils;

/**
 * 
 * @author E446027
 * 
 */
public class DateConvertddMMyyyy extends PositionalAdapter<String, Date> {

	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public Date unmarshal(String v) throws JFFPBException {
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
	public String marshal(Date v) throws JFFPBException {
		if (v == null) {

			return "        ";

		}
		return sdf.format(v);
	}

	

}
