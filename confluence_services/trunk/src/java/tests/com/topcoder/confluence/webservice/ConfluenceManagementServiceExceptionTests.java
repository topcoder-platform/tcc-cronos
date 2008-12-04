/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice;

import javax.ejb.ApplicationException;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for the <code>OSFilterException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ConfluenceManagementServiceExceptionTests extends TestCase {
    /**
     * <p>
     * Represents the error message for testing.
     * </p>
     */
    private static final String MESSAGE = "error message";

    /**
     * <p>
     * Represents the <code>Exception</code> instance used for testing.
     * </p>
     */
    private static final Exception CAUSE = new Exception();

    /**
     * <p>
     * Tests accuracy of <code>ConfluenceManagementServiceException()</code> constructor.
     * </p>
     * <p>
     * Verifies the exception is properly created.
     * </p>
     */
    public void testCtor1Accuracy() {
        ConfluenceManagementServiceException exception = new ConfluenceManagementServiceException();
        assertNotNull("Unable to instantiate ConfluenceManagementServiceException.", exception);
    }

    /**
     * <p>
     * Tests accuracy of <code>ConfluenceManagementServiceException(String)</code> constructor.
     * </p>
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    public void testCtor2Accuracy() {
        ConfluenceManagementServiceException exception = new ConfluenceManagementServiceException(MESSAGE);
        assertNotNull("Unable to instantiate ConfluenceManagementServiceException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Tests accuracy of <code>ConfluenceManagementServiceException(Throwable)</code> constructor.
     * </p>
     * <p>
     * Verifies the cause are properly propagated.
     * </p>
     */
    public void testCtor3Accuracy() {
        ConfluenceManagementServiceException exception = new ConfluenceManagementServiceException(CAUSE);
        assertNotNull("Unable to instantiate ConfluenceManagementServiceException.", exception);
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Tests accuracy of <code>ConfluenceManagementServiceException(String, Throwable)</code> constructor.
     * </p>
     * <p>
     * Verifies the error message and the cause are properly propagated.
     * </p>
     */
    public void testCtor4Accuracy() {
        ConfluenceManagementServiceException exception = new ConfluenceManagementServiceException(MESSAGE, CAUSE);
        assertNotNull("Unable to instantiate ConfluenceManagementServiceException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Inheritance test, verifies <code>ConfluenceManagementServiceException</code> subclasses
     * <code>Exception</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue("ConfluenceManagementServiceException does not subclass Exception.",
            new ConfluenceManagementServiceException() instanceof Exception);
    }

    /**
     * <p>
     * Test class annotation.
     * </p>
     */
    public void test_annotation() {
        TestHelper.assertClassAnnotation(ConfluenceManagementServiceException.class, ApplicationException.class);
    }
}