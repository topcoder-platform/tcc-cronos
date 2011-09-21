/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action.accuracytests.converter;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.StrutsStatics;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockServletContext;

import com.opensymphony.xwork2.ActionContext;
import com.topcoder.accounting.action.converter.DateConverter;

/**
 * Accuracy test for DateConverter class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DateConverterAccuracyTest {
    /**
     * Represents the instance of DateConverter used in test.
     */
    private DateConverter converter;

    /**
     * Represents the the context for the conversion.
     */
    private Map<String, Object> context;

    /**
     * Set up for each test.
     */
    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        converter = new DateConverter();
        context = new HashMap<String, Object>();
        MockServletContext servletContext = new MockServletContext();
        servletContext.addInitParameter("datePattern", "yyyyMMdd");
        ActionContext actionContext = new ActionContext(new HashMap());
        actionContext.put(StrutsStatics.SERVLET_CONTEXT, servletContext);
        ActionContext.setContext(actionContext);
        context.put("com.opensymphony.xwork2.dispatcher.ServletContext", servletContext);
    }

    /**
     * Accuracy test for convertValue(Map&lt;String, Object&gt; context, Object value, Class toType). When the return
     * type is String type.
     */
    @Test
    public void testConvertValue1() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2011);
        cal.set(Calendar.MONTH, 9);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        Date date = cal.getTime();
        assertEquals("The convert value is incorrect.", "20111001", converter.convertValue(context, date, String.class));
    }

    /**
     * Accuracy test for convertValue(Map&lt;String, Object&gt; context, Object value, Class toType). When the return
     * type is String type.
     */
    @Test
    public void testConvertValue2() {
        Date date = (Date) converter.convertValue(context, new String[] { "20111001" }, Date.class);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        assertEquals("The convert value is incorrect.", 2011, cal.get(Calendar.YEAR));
        assertEquals("The convert value is incorrect.", 9, cal.get(Calendar.MONTH));
        assertEquals("The convert value is incorrect.", 1, cal.get(Calendar.DAY_OF_MONTH));
        assertEquals("The convert value is incorrect.", 0, cal.get(Calendar.HOUR));
    }
}
