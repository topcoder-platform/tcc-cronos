/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps;

import junit.framework.JUnit4TestAdapter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Unit tests for {@link AuthorizationNotFoundException} class.
 * </p>
 *
 * @author KennyAlive
 * @version 1.0
 */
public class AuthorizationNotFoundExceptionTest {
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
     * Represents the ID of the authorization that doesn't exist.
     */
    private static final long AUTHORIZATION_ID = 1L;

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
        return new JUnit4TestAdapter(AuthorizationNotFoundExceptionTest.class);
    }

    /**
     * <p>
     * Accuracy test for <code>AuthorizationNotFoundException(String, long)</code> constructor.
     * </p>
     *
     * <p>
     * The detail message and authorization id should be set properly. The cause and application code should be null.
     * </p>
     */
    @Test
    public void test_constructorMessage() {
        AuthorizationNotFoundException instance = new AuthorizationNotFoundException(MESSAGE, AUTHORIZATION_ID);

        // Validate initiailzed fields
        assertNotNull("The message should not be null", instance.getMessage());
        assertEquals("The message should be set properly", MESSAGE, instance.getMessage());
        assertEquals("The authorization id should be set properly", AUTHORIZATION_ID,
                instance.getAuthorizationId());

        // Check cause and application code.
        assertNull("The cause should be null", instance.getCause());
        assertNull("The application code should be null", instance.getApplicationCode());
    }

    /**
     * <p>
     * Accuracy test for <code>AuthorizationNotFoundException(String, long)</code> constructor.
     * </p>
     *
     * <p>
     * Try to set null message. The null message should be returned.
     * </p>
     */
    @Test
    public void test_constructorMessage_2() {
        AuthorizationNotFoundException instance = new AuthorizationNotFoundException(null, AUTHORIZATION_ID);
        assertNull("The message should be set to null", instance.getMessage());
    }

    /**
     * <p>
     * Accuracy test for <code>AuthorizationNotFoundException(String, Throwable, long)</code> constructor.
     * </p>
     *
     * <p>
     * The message, the cause and the authorization id should be set properly. The application code should be null.
     * </p>
     */
    @Test
    public void test_constructorMessageCause() {
        AuthorizationNotFoundException instance =
                new AuthorizationNotFoundException(MESSAGE, CAUSE, AUTHORIZATION_ID);

        // Validate initialized fields.
        assertNotNull("The message should not be null", instance.getMessage());
        assertEquals("The message should be set properly", MESSAGE, instance.getMessage());
        assertNotNull("The cause should not be null", instance.getCause());
        assertSame("The cause should be set properly", CAUSE, instance.getCause());
        assertEquals("The authorization id should be set properly", AUTHORIZATION_ID,
                instance.getAuthorizationId());

        // Check application code.
        assertNull("The application code should be null", instance.getApplicationCode());
    }

    /**
     * <p>
     * Accuracy test for <code>AuthorizationNotFoundException(String, ExceptionData, long)</code> constructor.
     * </p>
     *
     * <p>
     * The message, the application code and the authorization id should be set properly. The cause should be null.
     * </p>
     */
    @Test
    public void test_constructorMessageData() {
        AuthorizationNotFoundException instance =
                new AuthorizationNotFoundException(MESSAGE, EXCEPTION_DATA, AUTHORIZATION_ID);

        // Validate initialized fields.
        assertNotNull("The message should not be null", instance.getMessage());
        assertEquals("The message should be set properly", MESSAGE, instance.getMessage());
        assertNotNull("The application code should not be null", instance.getApplicationCode());
        assertEquals("The application code should be set properly", APPLICATION_CODE,
                instance.getApplicationCode());
        assertEquals("The authorization id should be set properly", AUTHORIZATION_ID,
                instance.getAuthorizationId());

        // Check cause
        assertNull("The cause should be null", instance.getCause());
    }

    /**
     * <p>
     * Accuracy test for <code>AuthorizationNotFoundException(String, Throwable, ExceptionData, long)</code>
     * constructor.
     * </p>
     *
     * <p>
     * The message, the cause, the application code and the authorization id should be set properly.
     * </p>
     */
    @Test
    public void test_constructorMessageCauseData() {
        AuthorizationNotFoundException instance =
                new AuthorizationNotFoundException(MESSAGE, CAUSE, EXCEPTION_DATA, AUTHORIZATION_ID);

        // Validate initialized fields.
        assertNotNull("The message should not be null", instance.getMessage());
        assertEquals("The message should be set properly", MESSAGE, instance.getMessage());
        assertNotNull("The cause should not be null", instance.getCause());
        assertSame("The cause should be set properly", CAUSE, instance.getCause());
        assertNotNull("The application code should not be null", instance.getApplicationCode());
        assertEquals("The application code should be set properly", APPLICATION_CODE,
                instance.getApplicationCode());
        assertEquals("The authorization id should be set properly", AUTHORIZATION_ID,
                instance.getAuthorizationId());
    }

    /**
     * <p>
     * Accuracy test for <code>getAuthorizationId()</code> method.
     * </p>
     *
     * <p>
     * Check that getter works as expected.
     * </p>
     */
    @Test
    public void test_getAuthorizationId() {
        AuthorizationNotFoundException instance = new AuthorizationNotFoundException(MESSAGE, AUTHORIZATION_ID);
        // at first check that constructor initialized authorization id to correct value
        assertEquals("The constructor should set authorization id to correct value",
                AUTHORIZATION_ID, TestHelper.getField(instance, "authorizationId"));

        // then check that getter retrieves the same value that was set by constructor
        assertEquals("Getter should return the value set by constructor",
                AUTHORIZATION_ID, instance.getAuthorizationId());
    }
}
