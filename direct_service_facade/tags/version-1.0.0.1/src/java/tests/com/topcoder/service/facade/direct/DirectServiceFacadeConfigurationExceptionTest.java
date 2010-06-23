/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.direct;

import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for the <code>DirectServiceFacadeConfigurationException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DirectServiceFacadeConfigurationExceptionTest extends TestCase {

    /**
     * <p>
     * Represents the error message for testing.
     * </p>
     */
    private static final String MESSAGE = "error message";

    /**
     * <p>
     * Accuracy test for the constructor <code>DirectServiceFacadeConfigurationException(String)</code>, expects the
     * instance is created properly.
     * </p>
     */
    public void testCtor1Accuracy() {
        DirectServiceFacadeConfigurationException exception = new DirectServiceFacadeConfigurationException(MESSAGE);

        assertNotNull("Failed to create the DirectServiceFacadeConfigurationException instance.", exception);
        assertEquals("Expects the message is propagated properly.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>DirectServiceFacadeConfigurationException(String, Throwable)</code>,
     * expects the instance is created properly.
     * </p>
     */
    public void testCtor2Accuracy() {
        Throwable cause = new Throwable();
        DirectServiceFacadeConfigurationException exception = new DirectServiceFacadeConfigurationException(MESSAGE,
                cause);

        assertNotNull("Failed to create the DirectServiceFacadeConfigurationException instance.", exception);
        assertEquals("Expects the message is propagated properly.", MESSAGE, exception.getMessage());
        assertEquals("Expects the cause is propagated properly.", cause, exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>DirectServiceFacadeConfigurationException(String, ExceptionData)</code>
     * , expects the instance is created properly.
     * </p>
     */
    public void testCtor3Accuracy() {
        ExceptionData data = new ExceptionData();
        DirectServiceFacadeConfigurationException exception = new DirectServiceFacadeConfigurationException(MESSAGE,
                data);

        assertNotNull("Failed to create the DirectServiceFacadeConfigurationException instance.", exception);
        assertEquals("Expects the message is propagated properly.", MESSAGE, exception.getMessage());
        assertEquals("Expects the exception data is propagated properly.", data.getErrorCode(), exception
                .getErrorCode());
    }

    /**
     * <p>
     * Accuracy test for the constructor
     * <code>DirectServiceFacadeConfigurationException(String, Throwable, ExceptionData)</code>, expects the instance
     * is created properly.
     * </p>
     */
    public void testCtor4Accuracy() {
        Throwable cause = new Throwable();
        ExceptionData data = new ExceptionData();
        DirectServiceFacadeConfigurationException exception = new DirectServiceFacadeConfigurationException(MESSAGE,
                cause, data);

        assertNotNull("Failed to create the DirectServiceFacadeConfigurationException instance.", exception);
        assertEquals("Expects the message is propagated properly.", MESSAGE, exception.getMessage());
        assertEquals("Expects the cause is propagated properly.", cause, exception.getCause());
        assertEquals("Expects the exception data is propagated properly.", data.getErrorCode(), exception
                .getErrorCode());
    }

    /**
     * <p>
     * Inheritance test, expects <code>DirectServiceFacadeConfigurationException</code> subclasses
     * <code>BaseRuntimeException</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue("Expects DirectServiceFacadeConfigurationException subclass BaseRuntimeException.",
                new DirectServiceFacadeConfigurationException(MESSAGE) instanceof BaseRuntimeException);
    }
}