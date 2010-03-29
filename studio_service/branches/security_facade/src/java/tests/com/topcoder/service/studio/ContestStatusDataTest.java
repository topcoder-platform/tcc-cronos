/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Arrays;

/**
 * Some tests for ContestStatusData class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestStatusDataTest extends TestCase {
    /**
     * Bean to test.
     */
    private ContestStatusData target;

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(ContestStatusDataTest.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        target = new ContestStatusData();
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void tearDown() throws Exception {
    }

    /**
     * Tests setter/getter for statusId field.
     */
    public void testStatusId() {
        assertEquals("default value", -1, target.getStatusId());
        target.setStatusId(35);
        assertEquals("new value", 35, target.getStatusId());
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
     * Tests setter/getter for description field.
     */
    public void testDescription() {
        assertNull("default value", target.getDescription());
        target.setDescription("abc");
        assertEquals("new value", "abc", target.getDescription());
    }

    /**
     * Tests setter/getter for allowableNextStatus field.
     */
    public void testAllowableNextStatus() {
        assertEquals("default value", 0, target.getAllowableNextStatus().size());
        Long[] val = new Long[] {2l, 3l};
        target.setAllowableNextStatus(Arrays.asList(val));
        assertSame("new value", val[0], target.getAllowableNextStatus().get(0));
        assertSame("new value", val[1], target.getAllowableNextStatus().get(1));
    }

    /**
     * Tests setAllowableNextStatus method for null value. IllegalArgumentException expected.
     */
    public void testSetAllowableNextStatusForNull() {
        try {
            target.setAllowableNextStatus(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }
}
