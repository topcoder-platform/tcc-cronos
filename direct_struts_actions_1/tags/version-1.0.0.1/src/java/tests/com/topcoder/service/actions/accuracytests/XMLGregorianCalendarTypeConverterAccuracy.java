/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.accuracytests;

import com.opensymphony.xwork2.ActionContext;

import com.topcoder.service.actions.XMLGregorianCalendarTypeConverter;

import junit.framework.TestCase;

import java.sql.Date;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>
 * Accuracy test for <code>XMLGregorianCalendarTypeConverter</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class XMLGregorianCalendarTypeConverterAccuracy extends TestCase {
    /**
     * Accuracy test for <code>convertFromString()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testconvertFromStringAccuracy() throws Exception {
        XMLGregorianCalendarTypeConverter converter = new XMLGregorianCalendarTypeConverter();

        Map<String, Object> map = new HashMap<String, Object>();
        Locale l = new Locale("cn", "CN");
        map.put(ActionContext.LOCALE, l);

        String reference = "2010-04-06";
        Object convertedObject = converter.convertFromString(map,
                new String[] { reference }, Date.class);

        assertTrue("The result should match.",
            convertedObject instanceof XMLGregorianCalendar);

        XMLGregorianCalendar result = (XMLGregorianCalendar) convertedObject;
        assertEquals("The result should match.", 2010, result.getYear());
        assertEquals("The result should match.", 4, result.getMonth());
        assertEquals("The result should match.", 6, result.getDay());
    }

    /**
     * Accuracy test for <code>convertToString()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testConvertToStringAccuracy() throws Exception {
        XMLGregorianCalendarTypeConverter converter = new XMLGregorianCalendarTypeConverter();

        Map<String, Object> map = new HashMap<String, Object>();
        Locale l = new Locale("cn", "CN");
        map.put(ActionContext.LOCALE, l);

        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        XMLGregorianCalendar xmlGregorianCalendar = datatypeFactory.newXMLGregorianCalendarDate(2010,
                4, 6, 8);

        assertNotNull("The result should match.",
            converter.convertToString(map, xmlGregorianCalendar));
    }
}
