/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.prerequisite.ejb.IllegalArgumentWSFault;

/**
 * <p>
 * Unit test for <code>{@link IllegalArgumentWSException}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class IllegalArgumentWSExceptionUnitTests extends TestCase {

    /**
     * Represents a string with a detail message.
     */
    private static final String DETAIL_MESSAGE = "detail";

    /**
     * <p>
     * Represents the fault info.
     * </p>
     */
    private static final IllegalArgumentWSFault FAULT_INFO = new IllegalArgumentWSFault();

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(IllegalArgumentWSExceptionUnitTests.class);
    }

    /**
     * <p>
     * Unit test <code>IllegalArgumentWSException(String, IllegalArgumentWSFault)</code> constructor.
     * </p>
     * <p>
     * The detail error message and the fault info should be correct.
     * </p>
     */
    public void testIllegalArgumentWSException_accuracy1() {
        // Construct IllegalArgumentWSException with a detail message and fault info
        IllegalArgumentWSException exception = new IllegalArgumentWSException(DETAIL_MESSAGE, FAULT_INFO);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
        assertSame("The fault info is not set.", FAULT_INFO, exception.getFaultInfo());
        assertNull("Incorrect illegal argument message.", exception.getIllegalArgumentMessage());
    }

    /**
     * <p>
     * Unit test <code>IllegalArgumentWSException(String, String)</code> constructor.
     * </p>
     * <p>
     * The detail error message and the illegal argument message should be correct.
     * </p>
     */
    public void testIllegalArgumentWSException_accuracy2() {
        // Construct IllegalArgumentWSException with a detail message and illegal argument message
        String msg = "You are illegal:)";
        IllegalArgumentWSException exception = new IllegalArgumentWSException(DETAIL_MESSAGE, msg);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
        assertNull("The fault info should be null.", exception.getFaultInfo());
        assertEquals("Incorrect illegal argument message.", msg, exception.getIllegalArgumentMessage());
    }

    /**
     * <p>
     * Unit test for <code>IllegalArgumentWSException#getFaultInfo()</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetFaultInfo_accuracy() throws Exception {
        // Construct IllegalArgumentWSException with a detail message and fault info
        IllegalArgumentWSException exception = new IllegalArgumentWSException(DETAIL_MESSAGE, FAULT_INFO);
        assertNull("Incorrect illegal argument message.", exception.getIllegalArgumentMessage());

        String msg = "You are illegal:)";
        UnitTestHelper.setFieldValue(exception, "illegalArgumentMessage", msg);
        assertEquals("Incorrect illegal argument message.", msg, exception.getIllegalArgumentMessage());
    }

    /**
     * <p>
     * Unit test for <code>IllegalArgumentWSException#getIllegalArgumentMessage()</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetIllegalArgumentMessage_accuracy() throws Exception {
        // Construct IllegalArgumentWSException with a detail message
        IllegalArgumentWSException exception = new IllegalArgumentWSException(DETAIL_MESSAGE, "Error");
        assertNull("The fault info should be null.", exception.getFaultInfo());

        UnitTestHelper.setFieldValue(exception, "faultInfo", FAULT_INFO);
        assertSame("The fault info is not set.", FAULT_INFO, exception.getFaultInfo());
    }
}
