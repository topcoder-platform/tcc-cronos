/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.stresstests;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;

import junit.framework.TestCase;

import com.opensymphony.xwork2.ActionContext;
import com.topcoder.service.actions.XMLGregorianCalendarTypeConverter;

/**
 * <p>
 * This class is the stress test for XMLGregorianCalendarTypeConverter.
 * version 1.0.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class XMLGregorianCalendarTypeConverterStressTest extends TestCase {

    /**
     * The looping count for testing
     */
    private static final int COUNT = 3000;

    /**
     * The start time for the stress test.
     */
    private long startTime = 0;

    /**
     * The end time for the stress test.
     */
    private long endTime = 0;

    /**
     * The XMLGregorianCalendarTypeConverter instance for stress test.
     */
    XMLGregorianCalendarTypeConverter instance = null;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        instance = new XMLGregorianCalendarTypeConverter();
    }

    /**
     * <p>
     * Clears the test environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        instance = null;
    }

    /**
     * <p>
     * This method tests convertToString() and convertFromString().
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testConvertFromToString() throws Exception {
        startTime = System.currentTimeMillis();

        Map<String, Object> map = new HashMap<String, Object>();
        Locale locale = new Locale("en", "US");
        map.put(ActionContext.LOCALE, locale);
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.US);

        Date date = new Date();

        for (int i = 0; i < COUNT; ++i) {

            XMLGregorianCalendar convertedObject = (XMLGregorianCalendar) instance.convertFromString(map,
                    new String[]{df.format(date)}, Date.class);

            String str = instance.convertToString(new HashMap<String, Object>(), convertedObject);
            assertEquals("The two date should be equal", str, date.toString());
        }

        endTime = System.currentTimeMillis();
        System.out.println("The stress test for convertToString()/convertFromString() costs: "
                + (endTime - startTime) + " milliseconds.");
    }
}
