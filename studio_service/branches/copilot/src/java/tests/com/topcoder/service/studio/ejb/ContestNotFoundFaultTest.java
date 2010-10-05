/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.ejb;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Some tests for ContestNotFoundFault class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestNotFoundFaultTest extends TestCase {
    /**
     * Bean to test.
     */
    private ContestNotFoundFault target;

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(ContestNotFoundFaultTest.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        target = new ContestNotFoundFault();
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
        assertNotNull("created instance", new ContestNotFoundFault());
    }

    /**
     * Tests setter/getter for contestIdNotFound field.
     */
    public void testContestIdNotFound() {
        assertEquals("default value", 0, target.getContestIdNotFound());
        target.setContestIdNotFound(35);
        assertEquals("new value", 35, target.getContestIdNotFound());
    }
}
