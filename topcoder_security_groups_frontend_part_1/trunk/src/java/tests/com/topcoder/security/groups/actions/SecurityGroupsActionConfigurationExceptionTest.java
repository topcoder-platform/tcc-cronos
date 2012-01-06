/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for class <code>SecurityGroupsActionConfigurationException</code>.
 * </p>
 *
 * @author hanshuai
 * @version 1.0
 */
public class SecurityGroupsActionConfigurationExceptionTest {
    /**
     * <p>
     * Represents a detail message.
     * </p>
     */
    private static final String DETAIL_MESSAGE = "detail";

    /**
     * <p>
     * Represents an error cause.
     * </p>
     */
    private static final Throwable CAUSE = new Exception("UnitTests");

    /**
     * <p>
     * Represents the <code>SecurityGroupsActionConfigurationException</code> impl used in tests.
     * </p>
     */
    private SecurityGroupsActionConfigurationException impl;

    /**
     * <p>
     * Accuracy test for the constructor <code>SecurityGroupsActionConfigurationException()</code>. Instance should
     * be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull(impl);
    }

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(SecurityGroupsActionConfigurationExceptionTest.class);
    }

    /**
     * <p>
     * <code>SecurityGroupsActionConfigurationException</code> should be subclass of <code>superClassName</code>.
     * </p>
     */
    @Test
    public void testInheritance0() {
        assertTrue("SecurityGroupsActionConfigurationException should be subclass of RuntimeException.",
                SecurityGroupsActionConfigurationException.class.getSuperclass() == RuntimeException.class);
    }

    /**
     * <p>
     * Tests accuracy of <code>SecurityGroupsActionConfigurationException(String)</code> constructor.<br>
     * The detail error message should be properly set.
     * </p>
     */
    @Test
    public void testCtor1() {
        // Construct SecurityGroupsActionConfigurationException with a detail message
        SecurityGroupsActionConfigurationException exception = new SecurityGroupsActionConfigurationException(
                DETAIL_MESSAGE);
        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Tests accuracy of <code>SecurityGroupsActionConfigurationException(String, Throwable)</code> constructor.<br>
     * The detail error message and the original cause of error should be properly set.
     * </p>
     */
    @Test
    public void testCtor2() {
        // Construct SecurityGroupsActionConfigurationException with a detail message and a cause
        SecurityGroupsActionConfigurationException exception = new SecurityGroupsActionConfigurationException(
                DETAIL_MESSAGE, CAUSE);
        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be properly set.", DETAIL_MESSAGE,
                exception.getMessage());
        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertSame("Cause should be identical.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new SecurityGroupsActionConfigurationException("");
    }
}
