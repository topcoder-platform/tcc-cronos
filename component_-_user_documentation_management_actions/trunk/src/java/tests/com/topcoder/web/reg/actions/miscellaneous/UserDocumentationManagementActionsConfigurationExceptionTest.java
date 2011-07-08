/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import junit.framework.TestCase;

import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Unit tests for class <code>UserDocumentationManagementActionsConfigurationException</code>.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UserDocumentationManagementActionsConfigurationExceptionTest extends TestCase {
    /**
     * <p>
     * Represents the <code>Throwable</code> instance used to test against.
     * </p>
     */
    private Throwable throwable;

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    public void setUp() {
        throwable = new NullPointerException();
    }

    /**
     * <p>
     * Tear down the test environment.
     * </p>
     */
    public void tearDown() {
        throwable = null;
    }

    /**
     * <p>
     * Inheritance test, verifies <code>UserDocumentationManagementActionsConfigurationException</code> subclasses
     * should be correct.
     * </p>
     */
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.",
                new UserDocumentationManagementActionsConfigurationException("test") instanceof BaseRuntimeException);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>UserDocumentationManagementActionsConfigurationException(String)</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    public void testCtor() {
        UserDocumentationManagementActionsConfigurationException exception = new UserDocumentationManagementActionsConfigurationException(
                "test");
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for the constructor
     * <code>UserDocumentationManagementActionsConfigurationException(String, Throwable)</code> .<br>
     * Instance should be created successfully.
     * </p>
     */
    public void testCtor2() {
        UserDocumentationManagementActionsConfigurationException exception = new UserDocumentationManagementActionsConfigurationException(
                "test", throwable);
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
        assertEquals("Cause should be set correctly", throwable, exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for the constructor
     * <code>UserDocumentationManagementActionsConfigurationException(String, ExceptionData)</code> .<br>
     * Instance should be created successfully.
     * </p>
     */
    public void testCtor3() {
        ExceptionData data = new ExceptionData();
        data.setApplicationCode("applicationCode");
        UserDocumentationManagementActionsConfigurationException exception = new UserDocumentationManagementActionsConfigurationException(
                "test", data);
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
        assertEquals("Cause should be set correctly", "applicationCode", exception.getApplicationCode());
    }

    /**
     * <p>
     * Accuracy test for the constructor
     * <code>UserDocumentationManagementActionsConfigurationException(String, Throwable, ExceptionData)</code> . <br>
     * Instance should be created successfully.
     * </p>
     */
    public void testCtor4() {
        ExceptionData data = new ExceptionData();
        data.setApplicationCode("applicationCode");
        UserDocumentationManagementActionsConfigurationException exception = new UserDocumentationManagementActionsConfigurationException(
                "test", throwable, data);
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
        assertEquals("Cause should be set correctly", throwable, exception.getCause());
        assertEquals("Cause should be set correctly", "applicationCode", exception.getApplicationCode());
    }

}