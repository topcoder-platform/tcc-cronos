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
public class ConfluenceManagementServiceNotAuthorizedExceptionTests extends TestCase {
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
     * Tests accuracy of <code>ConfluenceManagementServiceNotAuthorizedException()</code> constructor.
     * </p>
     * <p>
     * Verifies the exception is properly created.
     * </p>
     */
    public void testCtor1Accuracy() {
        ConfluenceManagementServiceNotAuthorizedException exception =
            new ConfluenceManagementServiceNotAuthorizedException();
        assertNotNull("Unable to instantiate ConfluenceManagementServiceNotAuthorizedException.", exception);
    }

    /**
     * <p>
     * Tests accuracy of <code>ConfluenceManagementServiceNotAuthorizedException(String)</code> constructor.
     * </p>
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    public void testCtor2Accuracy() {
        ConfluenceManagementServiceNotAuthorizedException exception =
            new ConfluenceManagementServiceNotAuthorizedException(MESSAGE);
        assertNotNull("Unable to instantiate ConfluenceManagementServiceNotAuthorizedException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Tests accuracy of <code>ConfluenceManagementServiceNotAuthorizedException(Throwable)</code> constructor.
     * </p>
     * <p>
     * Verifies the cause are properly propagated.
     * </p>
     */
    public void testCtor3Accuracy() {
        ConfluenceManagementServiceNotAuthorizedException exception =
            new ConfluenceManagementServiceNotAuthorizedException(CAUSE);
        assertNotNull("Unable to instantiate ConfluenceManagementServiceNotAuthorizedException.", exception);
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Tests accuracy of <code>ConfluenceManagementServiceNotAuthorizedException(String, Throwable)</code>
     * constructor.
     * </p>
     * <p>
     * Verifies the error message and the cause are properly propagated.
     * </p>
     */
    public void testCtor4Accuracy() {
        ConfluenceManagementServiceNotAuthorizedException exception =
            new ConfluenceManagementServiceNotAuthorizedException(MESSAGE, CAUSE);
        assertNotNull("Unable to instantiate ConfluenceManagementServiceNotAuthorizedException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Inheritance test, verifies <code>ConfluenceManagementServiceNotAuthorizedException</code> subclasses
     * <code>ConfluenceManagementServiceException</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue(
            "ConfluenceManagementServiceNotAuthorizedException do not subclass ConfluenceManagementServiceException.",
            new ConfluenceManagementServiceNotAuthorizedException() instanceof ConfluenceManagementServiceException);
    }

    /**
     * <p>
     * Test class annotation.
     * </p>
     */
    public void test_annotation() {
        TestHelper.assertClassAnnotation(ConfluenceManagementServiceNotAuthorizedException.class,
            ApplicationException.class);
    }
}