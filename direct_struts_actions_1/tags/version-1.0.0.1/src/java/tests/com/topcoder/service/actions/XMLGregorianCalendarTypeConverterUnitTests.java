/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions;

import java.sql.Date;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.conversion.TypeConversionException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link XMLGregorianCalendarTypeConverter}</code> class.
 * </p>
 *
 * @author FireIce
 * @version 1.0
 */
public class XMLGregorianCalendarTypeConverterUnitTests extends TestCase {

    /**
     * <p>
     * Represents the <code>XMLGregorianCalendarTypeConverter</code> instance.
     * </p>
     */
    private XMLGregorianCalendarTypeConverter xmlGregorianCalendarTypeConverter;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(XMLGregorianCalendarTypeConverterUnitTests.class);
    }

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        xmlGregorianCalendarTypeConverter = new XMLGregorianCalendarTypeConverter();
    }

    /**
     * <p>
     * Tear down the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        xmlGregorianCalendarTypeConverter = null;

        super.tearDown();
    }

    /**
     * <p>
     * Tests the <code>XMLGregorianCalendarTypeConverter()</code> constructor.
     * </p>
     */
    public void testCtor() {
        assertNotNull("instance should be created.", xmlGregorianCalendarTypeConverter);
    }

    /**
     * <p>
     * Tests the <code>convertFromString(Map, String[], Class)</code> method.
     * </p>
     * <p>
     * If values is null, should throw TypeConversionException.
     * </p>
     */
    public void testConvertFromString_null() {
        try {
            xmlGregorianCalendarTypeConverter.convertFromString(new HashMap<String, Object>(), null, Date.class);
            fail("If values is null, should throw TypeConversionException.");
        } catch (TypeConversionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>convertFromString(Map, String[], Class)</code> method.
     * </p>
     * <p>
     * If values is empty, should throw TypeConversionException.
     * </p>
     */
    public void testConvertFromString_empty() {
        try {
            xmlGregorianCalendarTypeConverter.convertFromString(new HashMap<String, Object>(), new String[0],
                    Date.class);
            fail("If values is empty, should throw TypeConversionException.");
        } catch (TypeConversionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>convertFromString(Map, String[], Class)</code> method.
     * </p>
     * <p>
     * If the string is invalid date format, should throw TypeConversionException.
     * </p>
     */
    public void testConvertFromString_invalidValue1() {
        try {
            xmlGregorianCalendarTypeConverter.convertFromString(new HashMap<String, Object>(), new String[] {"asdsd"},
                    Date.class);
            fail("expected TypeConversionException");
        } catch (TypeConversionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>convertFromString(Map, String[], Class)</code> method.
     * </p>
     * <p>
     * If the string is invalid date format, should throw TypeConversionException.
     * </p>
     */
    public void testConvertFromString_invalidValue2() {
        try {
            xmlGregorianCalendarTypeConverter.convertFromString(new HashMap<String, Object>(), new String[] {"2010/04/06"},
                    Date.class);
            fail("expected TypeConversionException");
        } catch (TypeConversionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>convertFromString(Map, String[], Class)</code> method.
     * </p>
     * <p>
     * If the element of values is null, should throw TypeConversionException.
     * </p>
     */
    public void testConvertFromString_containsNull() {
        try {
            xmlGregorianCalendarTypeConverter.convertFromString(new HashMap<String, Object>(), new String[] {null},
                    Date.class);
            fail("If the element of values is null, should throw TypeConversionException.");
        } catch (TypeConversionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>convertFromString(Map, String[], Class)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testConvertFromStringWithLocalePoland() throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        Locale locale = new Locale("pl", "PL");
        map.put(ActionContext.LOCALE, locale);

        String reference = "2009-01-09";
        Object convertedObject = xmlGregorianCalendarTypeConverter.convertFromString(map, new String[] {reference},
                Date.class);

        assertNotNull(convertedObject);

        compareDates(locale, convertedObject);
    }

    /**
     * <p>
     * Tests the <code>convertFromString(Map, String[], Class)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testConvertFromStringWithLocaleFrance() throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        Locale locale = new Locale("fr", "FR");
        map.put(ActionContext.LOCALE, locale);

        String reference = "09/01/2009";
        Object convertedObject = xmlGregorianCalendarTypeConverter.convertFromString(map, new String[] {reference},
                Date.class);

        assertNotNull(convertedObject);

        compareDates(locale, convertedObject);
    }

    /**
     * <p>
     * Tests the <code>convertFromString(Map, String[], Class)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testConvertFromStringWithLocaleUK() throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        Locale locale = new Locale("en", "US");
        map.put(ActionContext.LOCALE, locale);

        String reference = "01/09/2009";
        Object convertedObject = xmlGregorianCalendarTypeConverter.convertFromString(map, new String[] {reference},
                Date.class);

        assertNotNull(convertedObject);

        compareDates(locale, convertedObject);
    }

    /**
     * <p>
     * Tests the <code>convertToString(Map, Object)</code> method.
     * </p>
     * <p>
     * If the object is not XMLGregorianCalendar, should throw TypeConversionException.
     * </p>
     */
    public void testConvertToString_not_XMLGregorianCalendar() {
        try {
            xmlGregorianCalendarTypeConverter.convertToString(new HashMap<String, Object>(), "invalid");

            fail("If the object is not XMLGregorianCalendar, should throw TypeConversionException.");
        } catch (TypeConversionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>convertToString(Map, Object)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testConvertToString_XMLGregorianCalendar() throws Exception {
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();

        XMLGregorianCalendar xmlGregorianCalendar = datatypeFactory.newXMLGregorianCalendar();

        String str = xmlGregorianCalendarTypeConverter.convertToString(new HashMap<String, Object>(),
                xmlGregorianCalendar);

        assertNotNull(str);
    }

    /**
     * <p>
     * Compares the dates.
     * </p>
     *
     * @param locale
     *            the Locale object
     * @param convertedObject
     *            the converted object, should be XMLGregorianCalendar type
     */
    private static void compareDates(Locale locale, Object convertedObject) {
        Calendar cal = Calendar.getInstance(locale);
        cal.set(Calendar.YEAR, 2009);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 9);

        Calendar cal1 = Calendar.getInstance(locale);
        cal1.setTime(((XMLGregorianCalendar) convertedObject).toGregorianCalendar().getTime());

        assertEquals(cal.get(Calendar.YEAR), cal1.get(Calendar.YEAR));
        assertEquals(cal.get(Calendar.MONTH), cal1.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.DATE), cal1.get(Calendar.DATE));

        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        assertEquals(df.format(cal.getTime()), df.format(((XMLGregorianCalendar) convertedObject).toGregorianCalendar()
                .getTime()));
    }
}
