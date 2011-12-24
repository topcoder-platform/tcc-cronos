/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.subscribers.jms;

import junit.framework.JUnit4TestAdapter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Unit tests for {@link AmazonPaymentEventReceivingException} class.
 * </p>
 *
 * @author KennyAlive
 * @version 1.0
 */
public class AmazonPaymentEventReceivingExceptionTest {
    /**
     * Represents the detail message.
     */
    private static final String MESSAGE = "The detail message";

    /**
     * The exception instance that represents the cause.
     */
    private static final Throwable CAUSE = new Exception();

    /**
     * Represents the exception data.
     */
    private static final ExceptionData EXCEPTION_DATA = new ExceptionData();

    /**
     * Represents the application code for application data initialization.
     */
    private static final String APPLICATION_CODE = "TopcoderApplication";

    /**
     * Exception data initialization.
     */
    static {
        EXCEPTION_DATA.setApplicationCode(APPLICATION_CODE);
    }

    /**
     * Creates a suite with all test methods for JUnix3.x runner.
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(AmazonPaymentEventReceivingExceptionTest.class);
    }

    /**
     * <p>
     * Accuracy test for <code>AmazonPaymentEventReceivingException(String message)</code> constructor.
     * </p>
     *
     * <p>
     * The detail message should be set properly. The cause and application code should be null.
     * </p>
     */
    @Test
    public void test_constructorMessage() {
        AmazonPaymentEventReceivingException instance = new AmazonPaymentEventReceivingException(MESSAGE);

        // Validate that message is set.
        assertNotNull("The message should not be null", instance.getMessage());
        assertEquals("The message should be set properly", MESSAGE, instance.getMessage());

        // Check cause and application code.
        assertNull("The cause should be null", instance.getCause());
        assertNull("The application code should be null", instance.getApplicationCode());
    }

    /**
     * <p>
     * Accuracy test for <code>AmazonPaymentEventReceivingException(String message)</code> constructor.
     * </p>
     *
     * <p>
     * Try to set null message. The null message should be returned.
     * </p>
     */
    @Test
    public void test_constructorMessage_2() {
        AmazonPaymentEventReceivingException instance = new AmazonPaymentEventReceivingException(null);
        assertNull("The message should be set to null", instance.getMessage());
    }

    /**
     * <p>
     * Accuracy test for <code>AmazonPaymentEventReceivingException(String message, Throwable cause)</code>
     * constructor.
     * </p>
     *
     * <p>
     * The message and the cause should be set properly. The application code should be null.
     * </p>
     */
    @Test
    public void test_constructorMessageCause() {
        AmazonPaymentEventReceivingException instance = new AmazonPaymentEventReceivingException(MESSAGE, CAUSE);

        // Validate initialized fields.
        assertNotNull("The message should not be null", instance.getMessage());
        assertEquals("The message should be set properly", MESSAGE, instance.getMessage());
        assertNotNull("The cause should not be null", instance.getCause());
        assertSame("The cause should be set properly", CAUSE, instance.getCause());

        // Check application code.
        assertNull("The application code should be null", instance.getApplicationCode());
    }

    /**
     * <p>
     * Accuracy test for <code>AmazonPaymentEventReceivingException(String message, ExceptionData data)</code>
     * constructor.
     * </p>
     *
     * <p>
     * The message and the application code should be set properly. The cause should be null.
     * </p>
     */
    @Test
    public void test_constructorMessageData() {
        AmazonPaymentEventReceivingException instance =
                new AmazonPaymentEventReceivingException(MESSAGE, EXCEPTION_DATA);

        // Validate initialized fields.
        assertNotNull("The message should not be null", instance.getMessage());
        assertEquals("The message should be set properly", MESSAGE, instance.getMessage());
        assertNotNull("The application code should not be null", instance.getApplicationCode());
        assertEquals("The application code should be set properly", APPLICATION_CODE,
                instance.getApplicationCode());

        // Check cause
        assertNull("The cause should be null", instance.getCause());
    }

    /**
     * <p>
     * Accuracy test for <code>AmazonPaymentEventReceivingException(String, Throwable, ExceptionData)</code>
     * constructor.
     * </p>
     *
     * <p>
     * The message, the cause and the application code should be set properly.
     * </p>
     */
    @Test
    public void test_constructorMessageCauseData() {
        AmazonPaymentEventReceivingException instance =
                new AmazonPaymentEventReceivingException(MESSAGE, CAUSE, EXCEPTION_DATA);

        // Validate initialized fields.
        assertNotNull("The message should not be null", instance.getMessage());
        assertEquals("The message should be set properly", MESSAGE, instance.getMessage());
        assertNotNull("The cause should not be null", instance.getCause());
        assertSame("The cause should be set properly", CAUSE, instance.getCause());
        assertNotNull("The application code should not be null", instance.getApplicationCode());
        assertEquals("The application code should be set properly", APPLICATION_CODE,
                instance.getApplicationCode());
    }
}
