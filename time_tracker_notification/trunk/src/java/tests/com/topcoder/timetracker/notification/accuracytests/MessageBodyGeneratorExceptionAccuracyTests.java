/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.accuracytests;

import com.topcoder.timetracker.notification.send.MessageBodyGeneratorException;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for MessageBodyGeneratorException.
 * </p>
 *
 * <p>
 * This class is pretty simple. The test cases simply verifies the exception can be instantiated with the error message
 * and cause properly propagated, and that it comes with correct inheritance.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class MessageBodyGeneratorExceptionAccuracyTests extends TestCase {
    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * <p>
     * An exception instance used to create the MessageBodyGeneratorException.
     * </p>
     */
    private static final Exception CAUSE_EXCEPTION = new NullPointerException();

    /**
     * <p>
     * Returns the test suite of this class.
     * </p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(MessageBodyGeneratorExceptionAccuracyTests.class);
    }

    /**
     * <p>
     * Creation test.
     * </p>
     *
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    public void testMessageBodyGeneratorException1() {
        MessageBodyGeneratorException exception = new MessageBodyGeneratorException(ERROR_MESSAGE);
        assertNotNull("Unable to instantiate MessageBodyGeneratorException.", exception);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Creation test.
     * </p>
     *
     * <p>
     * Verifies the error message and the cause are properly propagated.
     * </p>
     */
    public void testMessageBodyGeneratorException2() {
        MessageBodyGeneratorException exception = new MessageBodyGeneratorException(ERROR_MESSAGE, CAUSE_EXCEPTION);

        assertNotNull("Unable to instantiate MessageBodyGeneratorException.", exception);
        assertTrue("The error message should match", exception.getMessage().indexOf(ERROR_MESSAGE) >= 0);
        assertEquals("Cause is not properly propagated to super class.", CAUSE_EXCEPTION, exception.getCause());
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies MessageBodyGeneratorException subclasses Exception.
     * </p>
     */
    public void testMessageBodyGeneratorExceptionInheritance1() {
        MessageBodyGeneratorException exception = new MessageBodyGeneratorException(ERROR_MESSAGE);
        assertTrue("MessageBodyGeneratorException does not subclass Exception.", exception instanceof Exception);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies MessageBodyGeneratorException subclasses Exception.
     * </p>
     */
    public void testMessageBodyGeneratorExceptionInheritance2() {
        MessageBodyGeneratorException exception = new MessageBodyGeneratorException(ERROR_MESSAGE, CAUSE_EXCEPTION);
        assertTrue("MessageBodyGeneratorException does not subclass Exception.", exception instanceof Exception);
    }
}