/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action.failuretests;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.StrutsStatics;
import org.springframework.mock.web.MockServletContext;

import com.opensymphony.xwork2.ActionContext;
import com.topcoder.accounting.action.converter.DateConversionException;
import com.topcoder.accounting.action.converter.DateConverter;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for DateConverter.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class DateConverterFailureTests extends TestCase {

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(DateConverterFailureTests.class);
    }

    /**
     * <p>
     * Tests DateConverter#convertValue(Map,Object,Class) for failure.
     * It tests the case that when value is invalid and expects DateConversionException.
     * </p>
     */
    @SuppressWarnings("unchecked")
    public void testConvertValue_InvalidValue() {
        Map<String, Object> context = new HashMap<String, Object>();
        MockServletContext servletContext = new MockServletContext();
        servletContext.addInitParameter("datePattern", "yyyy-MM-dd");
        context.put(StrutsStatics.SERVLET_CONTEXT, servletContext);
        ActionContext actionContext = new ActionContext(new HashMap());
        actionContext.put(StrutsStatics.SERVLET_CONTEXT, servletContext);
        ActionContext.setContext(actionContext);
        context.put(StrutsStatics.SERVLET_CONTEXT, servletContext);
        DateConverter instance = new DateConverter();
        try {
            instance.convertValue(context, "invalid", String.class);
            fail("DateConversionException expected.");
        } catch (DateConversionException e) {
            //good
        }
    }

}
