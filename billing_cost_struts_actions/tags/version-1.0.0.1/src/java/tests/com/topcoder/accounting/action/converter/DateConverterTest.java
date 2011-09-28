/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.StrutsStatics;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockServletContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;

/**
 * <p>
 * Unit tests for the {@link DateConverter}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DateConverterTest {
    /** Represents the instance used to test again. */
    private DateConverter testInstance;
    /** Represents the servlet context used to test again. */
    private MockServletContext servletContext;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        servletContext = new MockServletContext();
        servletContext.addInitParameter("datePattern", "yyyy.MM.dd");
        ActionContext actionContext = new ActionContext(new HashMap());
        actionContext.put(StrutsStatics.SERVLET_CONTEXT, servletContext);
        ActionContext.setContext(actionContext);
        testInstance = new DateConverter();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        testInstance = null;
    }

    /**
     * <p>
     * Accuracy test for {@link DateConverter#DateConverter()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testDateConverter() throws Exception {
        assertNotNull("Instance should be correctly created.", testInstance);
    }

    /**
     * <p>
     * Inheritance test, verifies <code>DateConverter</code> subclasses
     * <code>DefaultTypeConverter</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue(
                "DateConverter does not subclass DefaultTypeConverter.",
                DateConverter.class.getSuperclass() == DefaultTypeConverter.class);
    }

    /**
     * <p>
     * Accuracy test for {@link DateConverter#convertValue()}.
     * </p>
     * <p>
     * Convert date instance to string. A string representation of date is
     * expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_convertValue1() throws Exception {
        Map<String, Object> context = new HashMap<String, Object>();
        Date date = new Date(0);
        assertEquals("must be '1970.01.01'", "1970.01.01", testInstance
                .convertValue(context, date, String.class));
    }

    /**
     * <p>
     * Accuracy test for {@link DateConverter#convertValue()}.
     * </p>
     * <p>
     * Convert string to date. The date of that string would be expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_convertValue2() throws Exception {
        String[] value = new String[1];
        value[0] = "1970.01.01";
        assertDate(value);
    }

    /**
     * <p>
     * Accuracy test for {@link DateConverter#convertValue()}.
     * </p>
     * <p>
     * Convert integer to object. This will get null as expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_convertValue3() throws Exception {
        Map<String, Object> context = new HashMap<String, Object>();
        assertNull("must be null", testInstance.convertValue(context, new Date(
                0), Integer.class));
    }

    /**
     * <p>
     * Failure test for {@link DateConverter#convertValue()}.
     * </p>
     * <p>
     * Servlet context is missing.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @SuppressWarnings("unchecked")
    @Test(expected = DateConversionException.class)
    public void test_convertValue_failure_1() throws Exception {
        ActionContext actionContext = new ActionContext(new HashMap());
        actionContext.put(StrutsStatics.SERVLET_CONTEXT, null);
        ActionContext.setContext(actionContext);
        String[] value = new String[1];
        value[0] = "1970.01.01";
        assertDate(value);
    }

    /**
     * <p>
     * Failure test for {@link DateConverter#convertValue()}.
     * </p>
     * <p>
     * Date pattern configuration is missing.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @SuppressWarnings("unchecked")
    @Test(expected = DateConversionException.class)
    public void test_convertValue_failure_2() throws Exception {
        ActionContext actionContext = new ActionContext(new HashMap());
        ActionContext.setContext(actionContext);
        String[] value = new String[1];
        value[0] = "1970.01.01";
        assertDate(value);
    }

    /**
     * <p>
     * Failure test for {@link DateConverter#convertValue()}.
     * </p>
     * <p>
     * Date pattern is invalid.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @SuppressWarnings("unchecked")
    @Test(expected = DateConversionException.class)
    public void test_convertValue_failure_3() throws Exception {
        servletContext.addInitParameter("datePattern", "xxxxxx");
        ActionContext actionContext = new ActionContext(new HashMap());
        ActionContext.setContext(actionContext);
        String[] value = new String[1];
        value[0] = "1970.01.01";
        assertDate(value);
    }

    /**
     * <p>
     * Failure test for {@link DateConverter#convertValue()}.
     * </p>
     * <p>
     * Can't cast string to string array, ClassCastException will throw.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = DateConversionException.class)
    public void test_convertValue_failure_4() throws Exception {
        String value = "1970.01.01";
        assertDate(value);
    }

    /**
     * <p>
     * Failure test for {@link DateConverter#convertValue()}.
     * </p>
     * <p>
     * Date string is invalid.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = DateConversionException.class)
    public void test_convertValue_failure_5() throws Exception {
        String[] value = new String[1];
        value[0] = "xxxxx";
        assertDate(value);
    }

    /**
     * Assert date.
     *
     * @param value the value
     */
    private void assertDate(Object value) {
        Date date = (Date) testInstance.convertValue(
                new HashMap<String, Object>(), value, Date.class);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        assertEquals("must be '1970'", 1970, calendar.get(Calendar.YEAR));
        assertEquals("must be '0'", 0, calendar.get(Calendar.MONTH));
        assertEquals("must be '1'", 1, calendar.get(Calendar.DATE));
    }
}
