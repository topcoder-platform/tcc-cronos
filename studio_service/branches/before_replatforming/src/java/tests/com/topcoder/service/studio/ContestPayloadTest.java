/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Some tests for ContestPayload class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestPayloadTest extends TestCase {
    /**
     * Bean to test.
     */
    private ContestPayload target;

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(ContestPayloadTest.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        target = new ContestPayload();
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void tearDown() throws Exception {
    }

    /**
     * Tests setter/getter for name field.
     */
    public void testName() {
        assertNull("default value", target.getName());
        target.setName("abc");
        assertEquals("new value", "abc", target.getName());
    }

    /**
     * Tests setter/getter for value field.
     */
    public void testValue() {
        assertNull("default value", target.getValue());
        target.setValue("abc");
        assertEquals("new value", "abc", target.getValue());
    }

    /**
     * Tests setter/getter for required field.
     */
    public void testRequired() {
        assertFalse("default value", target.isRequired());
        target.setRequired(true);
        assertTrue("new value", target.isRequired());
    }
}
