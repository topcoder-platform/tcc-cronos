package com.topcoder.shared.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {

    //FIXME SimpleDateFormat is not thread safe
    private static final DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private DateUtil() {
    }

    private static Timestamp toTimestamp(String source, DateFormat dateFormat) throws ParseException {
        Date date = dateFormat.parse(source);
        long time = date.getTime();
        return new Timestamp(time);
    }

    public static Timestamp toTimestamp(String source, String pattern) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return toTimestamp(source, dateFormat);
    }

    public static Timestamp toTimestamp(String source) throws ParseException {
        return toTimestamp(source, DEFAULT_DATE_FORMAT);
    }

    public static String toString(Timestamp timestamp) {
        return DEFAULT_DATE_FORMAT.format(timestamp);
    }

    public static String toString(Date date) {
    	return DEFAULT_DATE_FORMAT.format(date);
    }
    
    public static String toString(Date date, DateFormat format) {
    	return format.format(date);
    }
    
    public static String toString(long millis) {
    	return toString(new Date(millis));
    }
    
    public static String toString(long millis, DateFormat format) {
    	return toString(new Date(millis), format);
    }
}