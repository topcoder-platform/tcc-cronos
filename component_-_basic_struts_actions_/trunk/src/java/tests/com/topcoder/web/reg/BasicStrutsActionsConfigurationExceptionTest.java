/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Unit tests for class <code>BasicStrutsActionsConfigurationException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BasicStrutsActionsConfigurationExceptionTest {
    /**
     * <p>
     * Represents the <code>Throwable</code> instance used to test against.
     * </p>
     */
    private Throwable throwable;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BasicStrutsActionsConfigurationExceptionTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        throwable = new NullPointerException();
    }

    /**
     * <p>
     * Tear down the test environment.
     * </p>
     */
    @After
    public void tearDown() {
        throwable = null;
    }

    /**
     * <p>
     * Inheritance test, verifies <code>BasicStrutsActionsConfigurationException</code> subclasses should be
     * correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", new BasicStrutsActionsConfigurationException(
            "test") instanceof BaseRuntimeException);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>BasicStrutsActionsConfigurationException(String)</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        BasicStrutsActionsConfigurationException exception = new BasicStrutsActionsConfigurationException(
            "test");
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for the constructor
     * <code>BasicStrutsActionsConfigurationException(String, Throwable)</code> .<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor2() {
        BasicStrutsActionsConfigurationException exception = new BasicStrutsActionsConfigurationException(
            "test", throwable);
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
        assertEquals("Cause should be set correctly", throwable, exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for the constructor
     * <code>BasicStrutsActionsConfigurationException(String, ExceptionData)</code> .<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor3() {
        ExceptionData data = new ExceptionData();
        data.setApplicationCode("applicationCode");
        BasicStrutsActionsConfigurationException exception = new BasicStrutsActionsConfigurationException(
            "test", data);
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
        assertEquals("Cause should be set correctly", "applicationCode", exception.getApplicationCode());
    }

    /**
     * <p>
     * Accuracy test for the constructor
     * <code>BasicStrutsActionsConfigurationException(String, Throwable, ExceptionData)</code> . <br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor4() {
        ExceptionData data = new ExceptionData();
        data.setApplicationCode("applicationCode");
        BasicStrutsActionsConfigurationException exception = new BasicStrutsActionsConfigurationException(
            "test", throwable, data);
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
        assertEquals("Cause should be set correctly", throwable, exception.getCause());
        assertEquals("Cause should be set correctly", "applicationCode", exception.getApplicationCode());
    }

}