/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import junit.framework.TestCase;

import com.topcoder.util.errorhandling.BaseException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Unit tests for class <code>UserDocumentationManagementActionsException</code>.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UserDocumentationManagementActionsExceptionTest extends TestCase {
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
     * Inheritance test, verifies <code>UserDocumentationManagementActionsException</code> subclasses should be correct.
     * </p>
     */
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.",
                new UserDocumentationManagementActionsException("test") instanceof BaseException);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>UserDocumentationManagementActionsException(String)</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    public void testCtor() {
        UserDocumentationManagementActionsException exception = new UserDocumentationManagementActionsException("test");
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>UserDocumentationManagementActionsException(String, Throwable)</code> .<br>
     * Instance should be created successfully.
     * </p>
     */
    public void testCtor2() {
        UserDocumentationManagementActionsException exception = new UserDocumentationManagementActionsException("test",
                throwable);
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
        assertEquals("Cause should be set correctly", throwable, exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>UserDocumentationManagementActionsException(String, ExceptionData)</code>
     * .<br>
     * Instance should be created successfully.
     * </p>
     */
    public void testCtor3() {
        ExceptionData data = new ExceptionData();
        data.setApplicationCode("applicationCode");
        UserDocumentationManagementActionsException exception = new UserDocumentationManagementActionsException("test",
                data);
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
        assertEquals("Cause should be set correctly", "applicationCode", exception.getApplicationCode());
    }

    /**
     * <p>
     * Accuracy test for the constructor
     * <code>UserDocumentationManagementActionsException(String, Throwable, ExceptionData)</code> . <br>
     * Instance should be created successfully.
     * </p>
     */
    public void testCtor4() {
        ExceptionData data = new ExceptionData();
        data.setApplicationCode("applicationCode");
        UserDocumentationManagementActionsException exception = new UserDocumentationManagementActionsException("test",
                throwable, data);
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test'", "test", exception.getMessage());
        assertEquals("Cause should be set correctly", throwable, exception.getCause());
        assertEquals("Cause should be set correctly", "applicationCode", exception.getApplicationCode());
    }

}