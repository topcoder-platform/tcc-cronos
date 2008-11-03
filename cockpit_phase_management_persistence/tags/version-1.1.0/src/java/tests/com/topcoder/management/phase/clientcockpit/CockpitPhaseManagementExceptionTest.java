/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.clientcockpit;

import com.topcoder.management.phase.PhaseManagementException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * Unit test case of {@link CockpitPhaseManagementException}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CockpitPhaseManagementExceptionTest extends TestCase {
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
        TestSuite suite = new TestSuite(CockpitPhaseManagementExceptionTest.class);

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
     * Tests <code>CockpitPhaseManagementException(String message)</code> constructor for accuracy.
     * </p>
     */
    public void testCtor1() {
        CockpitPhaseManagementException e = new CockpitPhaseManagementException(ERROR_MESSAGE);
        assertNotNull("Unable to instantiate CockpitPhaseManagementException", e);
        assertTrue("CockpitPhaseManagementException should subclass PhaseManagementException",
            e instanceof PhaseManagementException);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, e.getMessage());
    }

    /**
     * <p>
     * Tests <code>CockpitPhaseManagementException(String message, Throwable cause)</code> constructor for
     * accuracy.
     * </p>
     */
    public void testCtor2() {
        CockpitPhaseManagementException e = new CockpitPhaseManagementException(ERROR_MESSAGE, cause);
        assertNotNull("Unable to instantiate CockpitPhaseManagementException", e);
        assertTrue("CockpitPhaseManagementException should subclass PhaseManagementException",
            e instanceof PhaseManagementException);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, e.getMessage());
        assertEquals("Error cause is not properly propagated to super class", cause, e.getCause());
    }
}
