/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.ejb;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Some tests for StatusNotFoundFault class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class StatusNotFoundFaultTest extends TestCase {
    /**
     * Bean to test.
     */
    private StatusNotFoundFault target;

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(StatusNotFoundFaultTest.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        target = new StatusNotFoundFault();
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
        assertNotNull("created instance", new StatusNotFoundFault());
    }

    /**
     * Tests setter/getter for statusIdNotFound field.
     */
    public void testStatusIdNotFound() {
        assertEquals("default value", 0, target.getStatusIdNotFound());
        target.setStatusIdNotFound(35);
        assertEquals("new value", 35, target.getStatusIdNotFound());
    }
}
