/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.forum.service.ejb;

import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * Unit tests for <code>ServiceConfigurationException</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ServiceConfigurationExceptionUnitTest extends TestCase {
    /**
     * <p>The error message used for testing.</p>
     */
    private static final String MESSAGE = "the error message";

    /**
     * <p>The ExceptionData used for testing.</p>
     */
    private static final ExceptionData DATA = new ExceptionData();

    /**
     * <p>The inner exception for testing.</p>
     */
    private static final Throwable CAUSE = new Exception();

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(ServiceConfigurationExceptionUnitTest.class);
    }

    /**
     * <p>
     * Accuracy test for constructor <code>ServiceConfigurationException(String)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStr() {
        ServiceConfigurationException exception = new ServiceConfigurationException(MESSAGE);
        assertNotNull("Unable to create ServiceConfigurationException instance.",
            exception);
        assertTrue("The ServiceConfigurationException should be instance of BaseRuntimeException.",
            exception instanceof BaseRuntimeException);
        assertEquals("The error message should match.", MESSAGE,
            exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>ServiceConfigurationException(String)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null message.
     * </p>
     */
    public void testCtorStr_Null() {
        ServiceConfigurationException exception = new ServiceConfigurationException((String) null);
        assertNotNull("Unable to create ServiceConfigurationException instance.",
            exception);
        assertTrue("The ServiceConfigurationException should be instance of BaseRuntimeException.",
            exception instanceof BaseRuntimeException);
        assertNull("The error message should match.", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>ServiceConfigurationException(String, ExceptionData)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStrExp() {
        ServiceConfigurationException exception = new ServiceConfigurationException(MESSAGE,
                DATA);
        assertNotNull("Unable to create ServiceConfigurationException instance.",
            exception);
        assertTrue("The ServiceConfigurationException should be instance of BaseRuntimeException.",
            exception instanceof BaseRuntimeException);
        assertEquals("The error message should match.", MESSAGE,
            exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>ServiceConfigurationException(String, ExceptionData)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null message and exception data.
     * </p>
     */
    public void testCtorStrExp_Null() {
        ServiceConfigurationException exception = new ServiceConfigurationException(null,
                (ExceptionData) null);
        assertNotNull("Unable to create ServiceConfigurationException instance.",
            exception);
        assertTrue("The ServiceConfigurationException should be instance of BaseRuntimeException.",
            exception instanceof BaseRuntimeException);
        assertNull("The error message should match.", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>ServiceConfigurationException(String, Throwable)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStrThrowable() {
        ServiceConfigurationException exception = new ServiceConfigurationException(MESSAGE,
                CAUSE);
        assertNotNull("Unable to create ServiceConfigurationException instance.",
            exception);
        assertTrue("The ServiceConfigurationException should be instance of BaseRuntimeException.",
            exception instanceof BaseRuntimeException);
        assertEquals("The error message should match.", MESSAGE,
            exception.getMessage());
        assertEquals("The inner cause should match.", CAUSE,
            exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>ServiceConfigurationException(String, Throwable)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null message and cause.
     * </p>
     */
    public void testCtorStrThrowable_Null() {
        ServiceConfigurationException exception = new ServiceConfigurationException(null,
                (Throwable) null);
        assertNotNull("Unable to create ServiceConfigurationException instance.",
            exception);
        assertTrue("The ServiceConfigurationException should be instance of BaseRuntimeException.",
            exception instanceof BaseRuntimeException);
        assertNull("The error message should match.", exception.getMessage());
        assertNull("The inner cause should match.", exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>ServiceConfigurationException(String, Throwable, ExceptionData)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStrThrowableExp() {
        ServiceConfigurationException exception = new ServiceConfigurationException(MESSAGE,
                CAUSE, DATA);
        assertNotNull("Unable to create ServiceConfigurationException instance.",
            exception);
        assertTrue("The ServiceConfigurationException should be instance of BaseRuntimeException.",
            exception instanceof BaseRuntimeException);
        assertEquals("The error message should match.", MESSAGE,
            exception.getMessage());
        assertEquals("The inner cause should match.", CAUSE,
            exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>ServiceConfigurationException(String, Throwable, ExceptionData)</code>.
     * </p>
     *
     * <p>
     * Verify that the exception can be created successfully with null arguments.
     * </p>
     */
    public void testCtorStrThrowableExp_Null() {
        ServiceConfigurationException exception = new ServiceConfigurationException(null,
                null, null);
        assertNotNull("Unable to create ServiceConfigurationException instance.",
            exception);
        assertTrue("The ServiceConfigurationException should be instance of BaseRuntimeException.",
            exception instanceof BaseRuntimeException);
        assertNull("The error message should match.", exception.getMessage());
        assertNull("The inner cause should match.", exception.getCause());
    }
}
