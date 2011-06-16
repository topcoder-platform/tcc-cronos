/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Unit tests for class <code>ContestServicesActionException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestServicesActionExceptionTest {
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
        return new JUnit4TestAdapter(ContestServicesActionExceptionTest.class);
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
     * Inheritance test, verifies <code>ContestServicesActionException</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.",
            new ContestServicesActionException("test") instanceof BaseCriticalException);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ContestServicesActionException(String)</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        ContestServicesActionException exception = new ContestServicesActionException("test");
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ContestServicesActionException(String, Throwable)</code> .<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor2() {
        ContestServicesActionException exception = new ContestServicesActionException("test", throwable);
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
        assertEquals("Cause should be set correctly", throwable, exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ContestServicesActionException(String, ExceptionData)</code> .<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor3() {
        ExceptionData data = new ExceptionData();
        data.setApplicationCode("applicationCode");
        ContestServicesActionException exception = new ContestServicesActionException("test", data);
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
        assertEquals("Cause should be set correctly", "applicationCode", exception.getApplicationCode());
    }

    /**
     * <p>
     * Accuracy test for the constructor
     * <code>ContestServicesActionException(String, Throwable, ExceptionData)</code> .<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor4() {
        ExceptionData data = new ExceptionData();
        data.setApplicationCode("applicationCode");
        ContestServicesActionException exception = new ContestServicesActionException("test", throwable, data);
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
        assertEquals("Cause should be set correctly", throwable, exception.getCause());
        assertEquals("Cause should be set correctly", "applicationCode", exception.getApplicationCode());
    }

}
