/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.ejb;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Some tests for IllegalArgumentWSFault class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class IllegalArgumentWSFaultTest extends TestCase {
    /**
     * Bean to test.
     */
    private IllegalArgumentWSFault target;

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(IllegalArgumentWSFaultTest.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        target = new IllegalArgumentWSFault();
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
     * Tests setter/getter for illegalArgumentMessage field.
     */
    public void testIllegalArgumentMessage() {
        assertEquals("default value", null, target.getIllegalArgumentMessage());
        target.setIllegalArgumentMessage("abc");
        assertEquals("new value", "abc", target.getIllegalArgumentMessage());
    }
}
