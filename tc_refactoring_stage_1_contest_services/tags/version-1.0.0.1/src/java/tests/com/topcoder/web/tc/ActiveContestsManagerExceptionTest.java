/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc;

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
 * Unit tests for class <code>ActiveContestsManagerException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ActiveContestsManagerExceptionTest {
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
        return new JUnit4TestAdapter(ActiveContestsManagerExceptionTest.class);
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
     * Inheritance test, verifies <code>ActiveContestsManagerException</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.",
            new ActiveContestsManagerException("test") instanceof BaseCriticalException);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ActiveContestsManagerException(String)</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        ActiveContestsManagerException exception = new ActiveContestsManagerException("test");
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ActiveContestsManagerException(String, Throwable)</code> .<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor2() {
        ActiveContestsManagerException exception = new ActiveContestsManagerException("test", throwable);
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
        assertEquals("Cause should be set correctly", throwable, exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ActiveContestsManagerException(String, ExceptionData)</code> .<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor3() {
        ExceptionData data = new ExceptionData();
        data.setApplicationCode("applicationCode");
        ActiveContestsManagerException exception = new ActiveContestsManagerException("test", data);
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
        assertEquals("Cause should be set correctly", "applicationCode", exception.getApplicationCode());
    }

    /**
     * <p>
     * Accuracy test for the constructor
     * <code>ActiveContestsManagerException(String, Throwable, ExceptionData)</code> .<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor4() {
        ExceptionData data = new ExceptionData();
        data.setApplicationCode("applicationCode");
        ActiveContestsManagerException exception = new ActiveContestsManagerException("test", throwable, data);
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
        assertEquals("Cause should be set correctly", throwable, exception.getCause());
        assertEquals("Cause should be set correctly", "applicationCode", exception.getApplicationCode());
    }

}
