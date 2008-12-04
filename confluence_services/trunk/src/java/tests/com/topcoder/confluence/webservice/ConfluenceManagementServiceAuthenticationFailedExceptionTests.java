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
public class ConfluenceManagementServiceAuthenticationFailedExceptionTests extends TestCase {

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
     * Tests accuracy of <code>ConfluenceManagementServiceAuthenticationFailedException()</code> constructor.
     * </p>
     * <p>
     * Verifies the exception is properly created.
     * </p>
     */
    public void testCtor1Accuracy() {
        ConfluenceManagementServiceAuthenticationFailedException exception =
            new ConfluenceManagementServiceAuthenticationFailedException();
        assertNotNull("Unable to instantiate ConfluenceManagementServiceAuthenticationFailedException.", exception);
    }

    /**
     * <p>
     * Tests accuracy of <code>ConfluenceManagementServiceAuthenticationFailedException(String)</code>
     * constructor.
     * </p>
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    public void testCtor2Accuracy() {
        ConfluenceManagementServiceAuthenticationFailedException exception =
            new ConfluenceManagementServiceAuthenticationFailedException(MESSAGE);
        assertNotNull("Unable to instantiate ConfluenceManagementServiceAuthenticationFailedException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Tests accuracy of <code>ConfluenceManagementServiceAuthenticationFailedException(Throwable)</code>
     * constructor.
     * </p>
     * <p>
     * Verifies the cause are properly propagated.
     * </p>
     */
    public void testCtor3Accuracy() {
        ConfluenceManagementServiceAuthenticationFailedException exception =
            new ConfluenceManagementServiceAuthenticationFailedException(CAUSE);
        assertNotNull("Unable to instantiate ConfluenceManagementServiceAuthenticationFailedException.", exception);
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Tests accuracy of <code>ConfluenceManagementServiceAuthenticationFailedException(String, Throwable)</code>
     * constructor.
     * </p>
     * <p>
     * Verifies the error message and the cause are properly propagated.
     * </p>
     */
    public void testCtor4Accuracy() {
        ConfluenceManagementServiceAuthenticationFailedException exception =
            new ConfluenceManagementServiceAuthenticationFailedException(MESSAGE, CAUSE);
        assertNotNull("Unable to instantiate ConfluenceManagementServiceAuthenticationFailedException.", exception);
        assertEquals("Error message is not properly propagated to super class.", MESSAGE, exception.getMessage());
        assertEquals("Cause is not properly propagated to super class.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Inheritance test, verifies <code>ConfluenceManagementServiceAuthenticationFailedException</code>
     * subclasses <code>ConfluenceManagementServiceException</code>.
     * </p>
     */
    public void testInheritance() {
        ConfluenceManagementServiceAuthenticationFailedException e =
            new ConfluenceManagementServiceAuthenticationFailedException();
        assertTrue("ConfluenceManagementServiceAuthenticationFailedException does not subclass"
            + " ConfluenceManagementServiceException.", e instanceof ConfluenceManagementServiceException);
    }

    /**
     * <p>
     * Test class annotation.
     * </p>
     */
    public void test_annotation() {
        TestHelper.assertClassAnnotation(ConfluenceManagementServiceAuthenticationFailedException.class,
            ApplicationException.class);
    }
}