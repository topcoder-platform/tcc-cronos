/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.clientcockpit;

import com.topcoder.management.phase.ConfigurationException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * Unit test case of {@link CockpitConfigurationException}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CockpitConfigurationExceptionTest extends TestCase {
    /**
     * <p>
     * Represents the error message used for testing.
     * </p>
     */
    private static final String ERROR_MESSAGE = "error message";

    /**
     * <p>
     * Represents Throwable instance used for testing.
     * </p>
     */
    private Throwable cause;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(CockpitConfigurationExceptionTest.class);

        return suite;
    }

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    protected void setUp() throws Exception {
        cause = new NullPointerException();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    protected void tearDown() throws Exception {
        cause = null;
    }

    /**
     * <p>
     * Tests <code>CockpitConfigurationException(String message)</code> constructor for accuracy.
     * </p>
     */
    public void testCtor1() {
        CockpitConfigurationException e = new CockpitConfigurationException(ERROR_MESSAGE);
        assertNotNull("Unable to instantiate CockpitConfigurationException", e);
        assertTrue("CockpitConfigurationException should subclass ConfigurationException",
            e instanceof ConfigurationException);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, e.getMessage());
    }

    /**
     * <p>
     * Tests <code>CockpitConfigurationException(String message, Throwable cause)</code> constructor for
     * accuracy.
     * </p>
     */
    public void testCtor2() {
        CockpitConfigurationException e = new CockpitConfigurationException(ERROR_MESSAGE, cause);
        assertNotNull("Unable to instantiate CockpitConfigurationException", e);
        assertTrue("CockpitConfigurationException should subclass ConfigurationException",
            e instanceof ConfigurationException);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, e.getMessage());
        assertEquals("Error cause is not properly propagated to super class", cause, e.getCause());
    }
}
