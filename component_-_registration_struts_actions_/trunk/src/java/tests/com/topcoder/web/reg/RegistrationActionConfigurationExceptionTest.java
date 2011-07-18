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
 * Unit tests for class <code>RegistrationActionConfigurationException</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class RegistrationActionConfigurationExceptionTest {
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
        return new JUnit4TestAdapter(RegistrationActionConfigurationExceptionTest.class);
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
     * Inheritance test, verifies <code>RegistrationActionConfigurationException</code> subclasses should be
     * correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", new RegistrationActionConfigurationException(
            "test") instanceof BaseRuntimeException);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>RegistrationActionConfigurationException(String)</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        RegistrationActionConfigurationException exception = new RegistrationActionConfigurationException(
            "test");
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for the constructor
     * <code>RegistrationActionConfigurationException(String, Throwable)</code> .<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor2() {
        RegistrationActionConfigurationException exception = new RegistrationActionConfigurationException(
            "test", throwable);
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
        assertEquals("Cause should be set correctly", throwable, exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for the constructor
     * <code>RegistrationActionConfigurationException(String, ExceptionData)</code> .<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor3() {
        ExceptionData data = new ExceptionData();
        data.setApplicationCode("applicationCode");
        RegistrationActionConfigurationException exception = new RegistrationActionConfigurationException(
            "test", data);
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
        assertEquals("Cause should be set correctly", "applicationCode", exception.getApplicationCode());
    }

    /**
     * <p>
     * Accuracy test for the constructor
     * <code>RegistrationActionConfigurationException(String, Throwable, ExceptionData)</code> . <br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor4() {
        ExceptionData data = new ExceptionData();
        data.setApplicationCode("applicationCode");
        RegistrationActionConfigurationException exception = new RegistrationActionConfigurationException(
            "test", throwable, data);
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
        assertEquals("Cause should be set correctly", throwable, exception.getCause());
        assertEquals("Cause should be set correctly", "applicationCode", exception.getApplicationCode());
    }

}
