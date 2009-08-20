/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.ejb;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Some tests for PersistenceFault class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PersistenceFaultTest extends TestCase {
    /**
     * Bean to test.
     */
    private PersistenceFault target;

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(PersistenceFaultTest.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        target = new PersistenceFault();
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
        assertNotNull("created instance", new IllegalArgumentWSFault());
    }

    /**
     * Tests setter/getter for persistenceMessage field.
     */
    public void testPersistenceMessage() {
        assertEquals("default value", null, target.getPersistenceMessage());
        target.setPersistenceMessage("abc");
        assertEquals("new value", "abc", target.getPersistenceMessage());
    }
}

