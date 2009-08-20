/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.ejb;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Some tests for StatusNotAllowedFault class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class StatusNotAllowedFaultTest extends TestCase {
    /**
     * Bean to test.
     */
    private StatusNotAllowedFault target;

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(StatusNotAllowedFaultTest.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        target = new StatusNotAllowedFault();
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void tearDown() throws Exception {
    }

    /**
     * Tests empty constructor.
     */
    public void testConstructor() {
        assertNotNull("created instance", new StatusNotAllowedFault());
    }

    /**
     * Tests setter/getter for statusIdNotAllowed field.
     */
    public void testStatusIdNotAllowed() {
        assertEquals("default value", 0, target.getStatusIdNotAllowed());
        target.setStatusIdNotAllowed(35);
        assertEquals("new value", 35, target.getStatusIdNotAllowed());
    }
}
