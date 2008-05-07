/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.ejb;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link IllegalArgumentWSFault}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class IllegalArgumentWSFaultUnitTests extends TestCase {

    /**
     * <p>
     * Represents the <code>IllegalArgumentWSFault</code> instance used in tests.
     * </p>
     */
    private IllegalArgumentWSFault faultInfo;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(IllegalArgumentWSFaultUnitTests.class);
    }

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        faultInfo = new IllegalArgumentWSFault();
    }

    /**
     * <p>
     * Unit test for <code>IllegalArgumentWSFault#IllegalArgumentWSFault()</code> constructor.
     * </p>
     * <p>
     * Instance should be always created.
     * </p>
     */
    public void testIllegalArgumentWSFault_accuracy() {
        assertNotNull("Instance should be always created.", faultInfo);
    }

    /**
     * <p>
     * Unit test for <code>IllegalArgumentWSFault#getIllegalArgumentMessage()</code> method.
     * </p>
     * <p>
     * It should return null, if not set.
     * </p>
     */
    public void testGetIllegalArgumentMessage_default() {
        assertNull("Should return null.", faultInfo.getIllegalArgumentMessage());
    }

    /**
     * <p>
     * Unit test for <code>IllegalArgumentWSFault#setIllegalArgumentMessage(String)</code> method.
     * </p>
     * <p>
     * All value are valid to set.
     * </p>
     */
    public void testSetIllegalArgumentMessage_accuracy() {
        faultInfo.setIllegalArgumentMessage(null);
        assertNull("Should return null.", faultInfo.getIllegalArgumentMessage());

        faultInfo.setIllegalArgumentMessage("");
        assertEquals("Incorrect illegal argument message.", "", faultInfo.getIllegalArgumentMessage());

        faultInfo.setIllegalArgumentMessage("  ");
        assertEquals("Incorrect illegal argument message.", "  ", faultInfo.getIllegalArgumentMessage());

        faultInfo.setIllegalArgumentMessage("Hello");
        assertEquals("Incorrect illegal argument message.", "Hello", faultInfo.getIllegalArgumentMessage());
    }
}
