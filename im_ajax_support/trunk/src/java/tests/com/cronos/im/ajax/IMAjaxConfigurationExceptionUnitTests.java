/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax;

import junit.framework.TestCase;
import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * Unit test cases for IMAjaxConfigurationException class.
 * </p>
 *
 * <p>
 * This class is pretty simple. The test cases simply verifies the exception can
 * be instantiated with the error message and cause properly, and that it comes
 * with correct inheritance.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class IMAjaxConfigurationExceptionUnitTests extends TestCase {
    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * <p>
     * An exception instance used to create the IMAjaxConfigurationException.
     * </p>
     */
    private static final Exception CAUSE_EXCEPTION = new Exception();

    /**
     * <p>
     * Creation test.
     * </p>
     *
     * <p>
     * Verifies the error message is properly set.
     * </p>
     */
    public void testIMAjaxConfigurationException1() {
        IMAjaxConfigurationException exception = new IMAjaxConfigurationException(ERROR_MESSAGE);
        assertNotNull("Unable to instantiate IMAjaxConfigurationException.", exception);
        assertEquals("Error message is not properly set.", ERROR_MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Creation test.
     * </p>
     *
     * <p>
     * Verifies the error message and the cause are properly set.
     * </p>
     */
    public void testIMAjaxConfigurationException2() {
        IMAjaxConfigurationException exception = new IMAjaxConfigurationException(ERROR_MESSAGE,
                CAUSE_EXCEPTION);

        assertNotNull("Unable to instantiate IMAjaxConfigurationException.", exception);
        assertTrue("The error message should match", exception.getMessage().indexOf(ERROR_MESSAGE) >= 0);
        assertEquals("Cause exception is not properly set.", CAUSE_EXCEPTION, exception.getCause());
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies IMAjaxConfigurationException is subclass of BaseException.
     * </p>
     */
    public void testIMAjaxConfigurationExceptionInheritance1() {
        IMAjaxConfigurationException exception = new IMAjaxConfigurationException(ERROR_MESSAGE);
        assertTrue("IMAjaxConfigurationException is not subclass of BaseException.",
                exception instanceof BaseException);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies IMAjaxConfigurationException is subclass of BaseException.
     * </p>
     */
    public void testIMAjaxConfigurationExceptionInheritance2() {
        IMAjaxConfigurationException exception = new IMAjaxConfigurationException(ERROR_MESSAGE,
                CAUSE_EXCEPTION);
        assertTrue("IMAjaxConfigurationException is not subclass of BaseException.",
                exception instanceof BaseException);
    }
}
