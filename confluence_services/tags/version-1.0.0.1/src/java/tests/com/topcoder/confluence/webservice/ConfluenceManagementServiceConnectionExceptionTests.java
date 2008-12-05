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
public class ConfluenceManagementServiceConnectionExceptionTests extends TestCase {
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
     * Tests accuracy of <code>ConfluenceManagementServiceConnectionException()</code> constructor.
     * </p>
     * <p>
     * Verifies the exception is properly created.
     * </p>
     */
    public void testCtor1Accuracy() {
        ConfluenceManagementServiceConnectionException exception =
            new ConfluenceManagementServiceConnectionException();
        assertNotNull("Unable to instantiate ConfluenceManagementServiceConnectionException.", exception);
    }

    /**
     * <p>
     * Tests accuracy of <code>ConfluenceManagementServiceConnectionException(String)</code> constructor.
     * </p>
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    public void testCtor2Accuracy() {
        ConfluenceManagementServiceConnectionException exception =
            new ConfluenceManagementServiceConnectionException(MESSAGE);
        assertNotNull("Unable to instantiate ConfluenceManagementServiceConnectionException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Tests accuracy of <code>ConfluenceManagementServiceConnectionException(Throwable)</code> constructor.
     * </p>
     * <p>
     * Verifies the cause are properly propagated.
     * </p>
     */
    public void testCtor3Accuracy() {
        ConfluenceManagementServiceConnectionException exception =
            new ConfluenceManagementServiceConnectionException(CAUSE);
        assertNotNull("Unable to instantiate ConfluenceManagementServiceConnectionException.", exception);
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Tests accuracy of <code>ConfluenceManagementServiceConnectionException(String, Throwable)</code>
     * constructor.
     * </p>
     * <p>
     * Verifies the error message and the cause are properly propagated.
     * </p>
     */
    public void testCtor4Accuracy() {
        ConfluenceManagementServiceConnectionException exception =
            new ConfluenceManagementServiceConnectionException(MESSAGE, CAUSE);
        assertNotNull("Unable to instantiate ConfluenceManagementServiceConnectionException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Inheritance test, verifies <code>ConfluenceManagementServiceConnectionException</code> subclasses
     * <code>ConfluenceManagementServiceException</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue(
            "ConfluenceManagementServiceConnectionException does not subclass ConfluenceManagementServiceException.",
            new ConfluenceManagementServiceConnectionException() instanceof ConfluenceManagementServiceException);
    }

    /**
     * <p>
     * Test class annotation.
     * </p>
     */
    public void test_annotation() {
        TestHelper.assertClassAnnotation(ConfluenceManagementServiceConnectionException.class,
            ApplicationException.class);
    }
}