/*
 * Creation : 15 déc. 2016
 */
/**
 * 
 */
package com.ffpm.sample.fileflat.file.readsimple;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.flat.bind.JFFPBException;
import javax.flat.bind.annotation.adapter.PositionalAdapter;
import javax.flat.bind.utils.StringUtils;

/**
 * @author E501142
 */
public class DateConvertyyyyMMddHHmmsssSSS extends PositionalAdapter<String, Timestamp> {

    @Override
    public Timestamp unmarshal(String v) throws JFFPBException {
        return StringUtils.getTimtampsValu(v);
    }

    @Override
    public String marshal(Timestamp v) throws JFFPBException {
        return StringUtils.getconvertTimeFormtat(v);

    }

    public static void main(String[] args) throws SQLException {

        try {
            // java8

            // DateTimeFormatter ff = new DateTimeFormatterBuilder().appendPattern("yyyyMMddHHmmss")
            // .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true) // Nanoseconds = 0-9 digits of fractional second.
            // .toFormatter();
            // LocalDateTime localDateTime = LocalDateTime.parse("20170112103100.2933", ff);
            //
            // OffsetDateTime odt = localDateTime.atOffset(ZoneOffset.UTC);
            // Instant instant = odt.toInstant();
            //
            // java.sql.Timestamp ts = java.sql.Timestamp.from(instant);
            // System.out.println("input: " + "201701121031002933" + " | localDateTime: " + localDateTime + " | odt: " + odt + " | instant: " +
            // instant
            // + " | ts: " + ts);
            SimpleDateFormat form = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.FRANCE);

            Date d = form.parse("201701121031002933".substring(0, 17));
            System.out.println("201701121031002933");
            System.out.println(form.format(d) + "201701121031002933".substring(17));

            d = form.parse("201701180527599919".substring(0, 17));
            System.out.println("201701180527599919");
            System.out.println(form.format(d) + "201701180527599919".substring(17));

            // String input = ClazzOutilsUtils.getconvertFormtatTime("201701180527599919");
            // TIMESTAMP t = new TIMESTAMP(input);
            // Timestamp t1 = t.timestampValue();
            // String output = ClazzOutilsUtils.getconvertTimeFormtat(t1.toString());
            // System.out.println(new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.FRANCE).format(new Date(t1.getTime())) + " \n" + output);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
